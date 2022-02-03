package com.internship.medicines.mappers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineToMedicineDtoMapper {
    public MedicineDto mapEntity(Medicine medicine) {
        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setId(medicine.getId());
        medicineDto.setName(medicine.getName());
        medicineDto.setPrice(medicine.getPrice());
        medicineDto.setCompound(medicine.getCompound());
        medicineDto.setContraindications(medicine.getContraindications());
        medicineDto.setTermsOfUse(medicine.getTermsOfUse());
        return medicineDto;
    }

    public Medicine mapDto(MedicineDto medicineDto) {
        Medicine medicine = new Medicine();
        medicine.setId(medicineDto.getId());
        medicine.setName(medicineDto.getName());
        medicine.setPrice(medicineDto.getPrice());
        medicine.setCompound(medicineDto.getCompound());
        medicine.setContraindications(medicineDto.getContraindications());
        medicine.setTermsOfUse(medicineDto.getTermsOfUse());
        return medicine;
    }

    public List<MedicineDto> mapList(List<Medicine> medicineList) {
        List<MedicineDto> medicineDtoList = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            medicineDtoList.add(mapEntity(medicine));
        }
        return medicineDtoList;
    }
}
