package com.internship.medicines.controllers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.services.MedicineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicineControllerTests {

    @InjectMocks
    MedicineController medicineController;

    @Mock
    MedicineService medicineService;

    private static final MedicineDto MEDICINE_DTO = createMedicineDto(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

    private static final Medicine MEDICINE = createMedicine(1L, "Nurofen",
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

    private static List<MedicineDto> initMedicineList() {
        List<MedicineDto> medicineList = new ArrayList<>();
        medicineList.add(createMedicineDto(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineList.add(createMedicineDto(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineList.add(createMedicineDto(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return medicineList;
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

    @Test
    public void testReadAllMedicine() {
        List<MedicineDto> expectedList = initMedicineList();
        when(medicineService.readAllMedicine(LESS_THEN_PRICE, MORE_THEN_PRICE, NAME))
                .thenReturn(expectedList);

        ResponseEntity<List<MedicineDto>> responseEntity = medicineController.readAllMedicine
                (LESS_THEN_PRICE, MORE_THEN_PRICE, NAME);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedList, responseEntity.getBody());
    }

    @Test
    public void testCreateMedicine() {
        when(medicineService.createMedicine(MEDICINE_DTO)).thenReturn(MEDICINE_DTO);

        ResponseEntity<MedicineDto> responseEntity = medicineController.createMedicine(MEDICINE_DTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MEDICINE_DTO, responseEntity.getBody());
    }

    @Test
    public void testReadMedicine() {
        when(medicineService.readMedicine(id)).thenReturn(MEDICINE_DTO);

        ResponseEntity<MedicineDto> responseEntity = medicineController.readMedicine(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MEDICINE_DTO, responseEntity.getBody());
    }

    @Test
    public void testUpdateMedicine() {
        when(medicineService.updateMedicine(MEDICINE, id)).thenReturn(MEDICINE_DTO);

        ResponseEntity<MedicineDto> responseEntity = medicineController.updateMedicine(MEDICINE, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MEDICINE_DTO, responseEntity.getBody());
    }

    @Test
    public void testUpdateNullMedicine() {
        when(medicineService.updateMedicine(MEDICINE, id)).thenReturn(null);

        ResponseEntity<MedicineDto> responseEntity = medicineController.updateMedicine(MEDICINE, id);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testDeleteMedicine() {
        doNothing().when(medicineService).deleteMedicine(id);

        ResponseEntity<HttpStatus> responseEntity = medicineController.deleteMedicine(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
