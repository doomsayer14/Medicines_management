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

    public List<Medicine> findMedicinesByPriceIsLessThan(double price) {
        return medicineRepository.findMedicinesByPriceIsLessThan(price);
    }

    public List<Medicine> findMedicinesByPriceIsGreaterThan(double price) {
        return medicineRepository.findMedicinesByPriceIsGreaterThan(price);
    }

    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }
}
