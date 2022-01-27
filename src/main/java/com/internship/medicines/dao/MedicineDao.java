package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MedicineDao {

    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine findById(Long id) {
        return medicineRepository.getById(id);
    }

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public List<Medicine> findMedicinesByName(String name) {
        return medicineRepository.findMedicinesByName(name);
    }

    public List<Medicine> findMedicinesByPriceIsLessThan(float price) {
        return medicineRepository.findMedicinesByPriceIsLessThan(price);
    }

    public List<Medicine> findMedicinesByPriceIsGreaterThan(float price) {
        return medicineRepository.findMedicinesByPriceIsGreaterThan(price);
    }

    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }

    public void updateById(Long id, Medicine medicine) {
        Medicine medicine1 = medicineRepository.getById(id);
        if (medicine1 != null) {
            medicineRepository.save(medicine);
        }
    }

}
