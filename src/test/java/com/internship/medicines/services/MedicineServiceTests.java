package com.internship.medicines.services;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MedicineServiceTests {


    @InjectMocks
    MedicineService medicineService;

    @Mock
    MedicineDao medicineDao;

    private final MedicineToMedicineDtoMapper mapper = new MedicineToMedicineDtoMapper();
    private final Medicine medicine = createMedicine(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");
    private final Long id = 1L;

//    private static Medicine createMedicine(Long id) {
//        Medicine medicine = new Medicine();
//        medicine.setId(id);
//        medicine.setName("SomeName");
//        medicine.setPrice(322.8);
//        medicine.setCompound("some compound");
//        medicine.setContraindications("some contraindications");
//        medicine.setTermsOfUse("some terms of use");
//        return medicine;
//    }

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
    public void shouldCreateAppointment() {
        when(medicineDao.save(any(Medicine.class))).thenReturn(medicine);

        medicineService.createMedicine(mapper.mapEntity(medicine));
        verify(medicineDao).save(any(Medicine.class));
    }

    @Test
    public void shouldFindMedicineById() {
        Medicine expectedMedicine = medicine;
        when(medicineDao.findById(id)).thenReturn(expectedMedicine);


        MedicineDto result = medicineService.readMedicine(id);
        assertEquals(mapper.mapEntity(expectedMedicine), result);
        verify(medicineDao, times(1)).findById(id);
    }

    @Test
    public void shouldFindAllMedicines() {
        List<Medicine> expectedList = initMedicineList();
        when(medicineDao.findAll()).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(9999.0, 0.0, "");
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findAll();
    }

    @Test
    public void shouldFindAllMedicinesWithParams() {
        double lessThenPrice = 32.0;
        double moreThenPrice = 12.0;
        String name = "in";
        List<Medicine> expectedList = initMedicineList();
        when(medicineDao.findAll(lessThenPrice, moreThenPrice, name)).thenReturn(expectedList);

        List<MedicineDto> result = medicineService.readAllMedicine(lessThenPrice, moreThenPrice, name);
        assertEquals(mapper.mapList(expectedList), result);
        verify(medicineDao, times(1)).findAll(lessThenPrice, moreThenPrice, name);
    }


    //update

    @Test
    public void shouldDeleteAppointment() {
        when(medicineDao.findById(id)).thenReturn(medicine);
        doNothing().when(medicineDao).delete(id);

        medicineService.deleteMedicine(id);
        verify(medicineDao).delete(id);
    }
}
