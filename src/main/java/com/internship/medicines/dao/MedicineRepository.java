package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository {@link JpaRepository} interface for {@link Medicine} class.
 */

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    @Override
    Page<Medicine> findAll(Pageable pageable);

    Page<Medicine> findMedicineByPriceLessThanAndPriceGreaterThanAndAndNameContaining
            (double ltp, double mtp, String name, Pageable pageable);
}