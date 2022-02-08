package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository {@link JpaRepository} interface for {@link Medicine} class.
 */

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}