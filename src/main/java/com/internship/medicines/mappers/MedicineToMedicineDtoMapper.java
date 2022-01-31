package com.internship.medicines.mappers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineToMedicineDtoMapper {
    public MedicineDto mapEntity(Medicine medicine) {
        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setId(medicine.getId());
        medicineDto.setPrice(medicine.getPrice());
        medicineDto.setCompound(medicine.getCompound());
        medicineDto.setContraindications(medicine.getContraindications());
        medicineDto.setTermsOfUse(medicine.getTermsOfUse());
        return medicineDto;
    }

    public List<MedicineDto> mapList(List<Medicine> medicineList) {
        List<MedicineDto> medicineDtoList = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            medicineDtoList.add(mapEntity(medicine));
        }
        return medicineDtoList;
    }
}
