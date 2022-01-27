package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findMedicinesByPriceIsLessThan(float price);

    List<Medicine> findMedicinesByPriceIsGreaterThan(float price);

    List<Medicine> findMedicinesByName(String name);

    //update
}