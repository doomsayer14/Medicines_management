package com.internship.medicines.services;

import com.google.gson.Gson;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.dao.MedicineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    public List<Medicine> readAllMedicine
            (int lessThenPrice, int moreThenPrice, String name) {
        if (lessThenPrice > 0) {
            return medicineDao.
                    findMedicinesByPriceIsLessThan(lessThenPrice);
        }
        if (moreThenPrice > 0) {
            return medicineDao.
                    findMedicinesByPriceIsGreaterThan(moreThenPrice);
        }
        if (name != null) {
            return medicineDao.findMedicinesByName(name);
        }
        return medicineDao.findAll();
    }

    public Medicine createMedicine(Medicine medicine) {
        return medicineDao.saveMedicine(medicine);
    }

    public Medicine readMedicine(Long id) {
        return medicineDao.findById(id);
    }

    public Medicine updateMedicine(Medicine medicine, Long id) {
        medicineDao.updateById(id, medicine);
        if (medicineDao.findById(id) != null) {
            return medicine;
        }
        return null;
    }

    public void deleteMedicine(Long id) {
        medicineDao.deleteById(id);
    }
}
