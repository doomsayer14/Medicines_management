package com.internship.medicines.services;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    private final MedicineToMedicineDtoMapper medicineMapper = new MedicineToMedicineDtoMapper();

    public List<MedicineDto> readAllMedicine(Double lessThenPrice, Double moreThenPrice, String name) {
        //when lessThenPrice < moreThenPrice, result of their queries will not have general elements
        if ((lessThenPrice != null && moreThenPrice != null) && lessThenPrice < moreThenPrice) {
            return Collections.emptyList();
        }
        if (lessThenPrice == 99999.0 && moreThenPrice == 0.0 && name == null) {
            return medicineMapper.mapList(medicineDao.findAll());
        }

        List<Medicine> lessList = new ArrayList<>();
        List<Medicine> moreList = new ArrayList<>();
        List<Medicine> nameList = new ArrayList<>();

        //checking if there are arguments from the controller
        if (lessThenPrice != null) {
            lessList.addAll(medicineDao.findMedicinesByPriceIsLessThan(lessThenPrice));
        }
        if (moreThenPrice != null) {
            moreList.addAll(medicineDao.findMedicinesByPriceIsGreaterThan(moreThenPrice));
        }
        if (name != null) {
            nameList.addAll(medicineDao.findMedicinesByName(name));
        }

        //saving general elements
        //this part is not working
        if (!lessList.isEmpty() || !moreList.isEmpty() || !nameList.isEmpty()) {
            List<Medicine> resultList = new ArrayList<>();
            //bruteforce!!!!!11

            //if we have only one filter parameter
            if (moreList.isEmpty() && nameList.isEmpty()) {
                return medicineMapper.mapList(lessList);
            }
            if (lessList.isEmpty() && nameList.isEmpty()) {
                return medicineMapper.mapList(moreList);
            }
            if (lessList.isEmpty() && moreList.isEmpty()) {
                return medicineMapper.mapList(nameList);
            }

            //cases for two filter parameter
            if (!lessList.isEmpty() && !moreList.isEmpty()) {
                resultList.addAll(lessList);
                resultList.retainAll(moreList);
                return medicineMapper.mapList(resultList);
            }
            if (!lessList.isEmpty() && !nameList.isEmpty()) {
                resultList.addAll(lessList);
                resultList.retainAll(nameList);
                return medicineMapper.mapList(resultList);
            }
            if (!moreList.isEmpty() && !nameList.isEmpty()) {
                resultList.addAll(moreList);
                resultList.retainAll(nameList);
                return medicineMapper.mapList(resultList);
            }

            //case for three filter parameter
            if (!lessList.isEmpty() && !moreList.isEmpty() && !nameList.isEmpty()) {
                resultList.addAll(lessList);
                resultList.retainAll(moreList);
                resultList.retainAll(nameList);
                return medicineMapper.mapList(resultList);
            }

        }
        //if there is no args, just return all medicines
        return Collections.emptyList();
    }

    public MedicineDto createMedicine(Medicine medicine) {
        return medicineMapper.mapEntity(medicineDao.save(medicine));
    }

    public MedicineDto readMedicine(Long id) {
        return medicineMapper.mapEntity(medicineDao.findById(id));
    }

    public MedicineDto updateMedicine(Medicine medicine, Long id) {
        medicineDao.save(medicine);
        if (medicineDao.findById(id) == medicine) {
            return medicineMapper.mapEntity(medicine);
        }
        return null;
    }

    public void deleteMedicine(Long id) {
        medicineDao.delete(id);
    }
}
