package com.internship.medicines.mappers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicineToMedicineDtoMapperTests {

    @InjectMocks
    MedicineToMedicineDtoMapper mapper;

    private static final Medicine MEDICINE = createMedicine(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

    private static final MedicineDto MEDICINE_DTO = createMedicineDto(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

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

    private static List<MedicineDto> initMedicineDtoList() {
        List<MedicineDto> medicineDtoList = new ArrayList<>();
        medicineDtoList.add(createMedicineDto(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineDtoList.add(createMedicineDto(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineDtoList.add(createMedicineDto(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return medicineDtoList;
    }

    @Test
    public void testMapEntity() {
        MedicineDto resultMedicine = mapper.mapEntity(MEDICINE);
        assertEquals(MEDICINE_DTO, resultMedicine);
    }

    @Test
    public void testMapDto() {
        Medicine resultMedicine = mapper.mapDto(MEDICINE_DTO);
        assertEquals(MEDICINE, resultMedicine);
    }

    @Test
    public void testMapList() {
        List<MedicineDto> expectedList = initMedicineDtoList();
        List<MedicineDto> resultList = mapper.mapList(initMedicineList());
        assertEquals(expectedList, resultList);
    }
}
