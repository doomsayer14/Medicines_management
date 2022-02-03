package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link Medicine} class.
 */

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findMedicinesByPriceIsLessThan(double price);

    List<Medicine> findMedicinesByPriceIsGreaterThan(double price);

    List<Medicine> findMedicinesByNameContaining(String name);


    List<Medicine> findMedicineByPriceLessThanAndPriceGreaterThan
            (double lessThenPrice, double moreThenPrice);

    List<Medicine> findMedicineByPriceLessThanAndAndNameContaining
            (double price, String name);

    List<Medicine> findMedicineByPriceGreaterThanAndAndNameContaining
            (double price, String name);


    List<Medicine>
    findMedicineByPriceLessThanAndPriceGreaterThanAndAndNameContaining
            (double lessThenPrice, double moreThenPrice, String name);

}