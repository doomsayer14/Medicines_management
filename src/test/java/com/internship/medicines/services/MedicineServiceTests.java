package com.internship.medicines.services;

import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.exceptions.MedicineNotFoundException;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicineServiceTests {

    @InjectMocks
    MedicineService medicineService;

    @Mock
    MedicineDao medicineDao;

    @Mock
    MedicineToMedicineDtoMapper mapper;

    @Mock
    Pageable pageable;

    private static final Medicine MEDICINE = createMedicine(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

    private static final MedicineDto MEDICINE_DTO = createMedicineDto(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

    private static final Long id = 1L;

    private static final Double LESS_THEN_PRICE = 9999.0;
    private static final Double MORE_THEN_PRICE = 0.0;
    private static final String NAME = "";


    private static Medicine createMedicine
            (Long id, String name, Double price, String comp, String contr, String terms) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setName(name);
        medicine.setPrice(price);
        medicine.setCompound(comp);
        medicine.setContraindications(contr);
        medicine.setTermsOfUse(terms);
        return medicine;
    }

    private static MedicineDto createMedicineDto
            (Long id, String name, Double price, String comp, String contr, String terms) {
        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setId(id);
        medicineDto.setName(name);
        medicineDto.setPrice(price);
        medicineDto.setCompound(comp);
        medicineDto.setContraindications(contr);
        medicineDto.setTermsOfUse(terms);
        return medicineDto;
    }

    private static Page<Medicine> initMedicinePage() {
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineList.add(createMedicine(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return new PageImpl<>(medicineList);
    }

    private static List<Medicine> initMedicineList() {
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineList.add(createMedicine(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return medicineList;
    }

    @Test
    public void testFindMedicineById() {
        when(medicineDao.existsById(id)).thenReturn(true);
        when(medicineDao.findById(id)).thenReturn(MEDICINE);

        MedicineDto result = medicineService.readMedicine(id);
        assertEquals(mapper.mapEntity(MEDICINE), result);
        verify(medicineDao).findById(id);
    }

    @Test
    public void testThrowExceptionWhenFindMedicineById() {
        assertThrows(MedicineNotFoundException.class, () -> medicineService.readMedicine(999L));
        verify(medicineDao, never()).findById(id);
    }

    @Test
    public void testFindAllMedicines() {
        Page<Medicine> expectedPage = initMedicinePage();
        when(medicineDao.findAll(pageable)).thenReturn(expectedPage);

        Page<Medicine> result = medicineService.readAllMedicine(LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable);
        assertEquals(expectedPage, result);
        verify(medicineDao).findAll(pageable);
    }

    @Test
    public void testThrowExceptionsWhenDatabaseIsEmpty() {
        Page<Medicine> expectedPage = Page.empty();
        when(medicineDao.findAll(pageable)).thenReturn(expectedPage);

        assertThrows(MedicineNotFoundException.class, () -> medicineService.readAllMedicine
                (LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable));
        verify(medicineDao, never()).findAll(LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable);
    }

    @Test
    public void testFindAllMedicinesWithParams() {
        double lessThenPrice = 32.0;
        double moreThenPrice = 12.0;
        String name = "in";
        Page<Medicine> expectedPage = initMedicinePage();
        when(medicineDao.findAll(lessThenPrice, moreThenPrice, name, pageable)).thenReturn(expectedPage);

        Page<Medicine> result = medicineService.readAllMedicine(lessThenPrice, moreThenPrice, name, pageable);
        assertEquals(expectedPage, result);
        verify(medicineDao).findAll(lessThenPrice, moreThenPrice, name, pageable);
    }

    @Test
    public void testReturnEmptyPageWhenMoreThenPriceGreaterThanLessThanPrice() {
        double lessThenPrice = 12.0;
        double moreThenPrice = 32.0;
        String name = "in";

        Page<Medicine> expectedPage = Page.empty();
        //when(medicineDao.findAll(lessThenPrice, moreThenPrice, name, pageable)).thenReturn(expectedPage);

        Page<Medicine> result = medicineService.readAllMedicine
                (lessThenPrice, moreThenPrice, name, pageable);

        assertEquals(expectedPage, result);
        verify(medicineDao, never()).findAll(lessThenPrice, moreThenPrice, name, pageable);
    }

    @Test
    public void testThrowExceptionsWhenNoMedicineWithThisParams() {
        double lessThenPrice = 32.0;
        double moreThenPrice = 12.0;
        String name = "in";
        Page<Medicine> expectedPage = Page.empty();
        when(medicineDao.findAll(lessThenPrice, moreThenPrice, name, pageable)).thenReturn(expectedPage);

        assertThrows(MedicineNotFoundException.class, () -> medicineService.readAllMedicine
                (lessThenPrice, moreThenPrice, name, pageable));
        verify(medicineDao).findAll(lessThenPrice, moreThenPrice, name, pageable);
    }

    @Test
    public void testCreateMedicine() {
        when(mapper.mapDto(MEDICINE_DTO)).thenReturn(MEDICINE);
        when(mapper.mapEntity(MEDICINE)).thenReturn(MEDICINE_DTO);

        when((medicineDao).save(MEDICINE)).thenReturn(MEDICINE);

        medicineService.createMedicine(mapper.mapEntity(MEDICINE));

        verify(medicineDao).save(MEDICINE);
    }

    @Test
    public void testUpdateMedicineThrowMedicineNotFoundExceptions() {
        when(medicineDao.existsById(id)).thenReturn(false);

        assertThrows(MedicineNotFoundException.class, () -> medicineService.updateMedicine(MEDICINE, id));

        verify(medicineDao).existsById(id);
        verify(medicineDao, never()).save(MEDICINE);
    }

    @Test
    public void testDeleteMedicine() {
        when(medicineDao.existsById(id)).thenReturn(true);
        doNothing().when(medicineDao).delete(id);

        medicineService.deleteMedicine(id);
        verify(medicineDao).existsById(id);
        verify(medicineDao).delete(id);
    }
}
