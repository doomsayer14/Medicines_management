package com.internship.medicines;

import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;
import com.internship.medicines.services.MedicineService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MedicinesServiceTests {


    @InjectMocks
    MedicineService medicineService;

    @Mock
    MedicineDao medicineDao;

    MedicineToMedicineDtoMapper mapper = new MedicineToMedicineDtoMapper();

    private static Medicine createMedicine(Long id) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setName("SomeName");
        medicine.setPrice(322.8);
        medicine.setCompound("some compound");
        medicine.setContraindications("some contraindications");
        medicine.setTermsOfUse("some terms of use");
        return medicine;
    }

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
    public void shouldReturnEmptyListWhenMorePriceIsGreaterThenLessPrice() {
        when((findCommon(medicineDao.findMedicinesByPriceIsLessThan(10.0),
                medicineDao.findMedicinesByPriceIsGreaterThan(100.0))))
                .thenReturn(Collections.emptyList());

        List<MedicineDto> result = medicineService
                .readAllMedicine(10.0, 100.0, null);
        assertEquals(Collections.emptyList(), result);
    }

    private static List<Medicine> findCommon(List<Medicine> list1, List<Medicine> list2) {
        List<Medicine> resultList = new ArrayList<>();
        for (Medicine medicine1 : list1) {
            for (Medicine medicine2 : list2) {
                if (medicine1.equals(medicine2)) {
                    resultList.add(medicine1);
                }
            }
        }
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }
        return resultList;
    }

    @Test
    public void shouldFindMedicineById() {
        Long id = 1L;
        Medicine expectedMedicine = createMedicine(id);
        when(medicineDao.findById(id)).thenReturn(expectedMedicine);

        MedicineDto result = medicineService.readMedicine(id);
        assertEquals(mapper.mapEntity(expectedMedicine), result);
        verify(medicineDao, times(1)).findById(id);
    }

    @Test
    public void shouldFindAllMedicines() {
        List<Medicine> expectedList = initMedicineList();
        when(medicineDao.findAll()).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(99999.0, 0.0, null);
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findAll();
    }

    @Test
    public void shouldFindMedicinesByPriceIsLessThan() {
        List<Medicine> expectedList = new ArrayList<>();
        expectedList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));
        expectedList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));
        when(medicineDao.findMedicinesByPriceIsLessThan(23.0)).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(23.0, 0.0, null);
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findMedicinesByPriceIsLessThan(23.0);
    }

    @Test
    public void shouldFindMedicinesByPriceIsGreaterThan() {
        List<Medicine> expectedList = new ArrayList<>();
        expectedList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));
        expectedList.add(createMedicine(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));
        when(medicineDao.findMedicinesByPriceIsGreaterThan(21.0)).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(99999.0, 21.0, null);
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findMedicinesByPriceIsGreaterThan(21.0);
    }

    @Test
    public void shouldFindMedicinesByName() {
        List<Medicine> expectedList = new ArrayList<>();
        expectedList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));
        when(medicineDao.findMedicinesByName("Nurofen")).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(99999.0, 0.0, "Nurofen");
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findMedicinesByName("Nurofen");
    }


}
