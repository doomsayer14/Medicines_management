package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * DAO class for {@link Medicine}.
 */

@Repository
public class MedicineDao {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Checks whether database has a medicine with specified id
     *
     * @param id ID of particular medicine
     * @return true if medicine with specified id is exists
     */
    public boolean existsById(Long id) {
        return medicineRepository.existsById(id);
    }

    /**
     * Search for medicine with specified ID in database
     *
     * @param id ID of particular medicine
     * @return medicine with specified id
     */
    public Medicine findById(Long id) {
        return medicineRepository.getById(id);
    }

    /**
     * Finds and returns all medicines from database. If there are no medicines
     * in database, returns EmptyList
     *
     * @return Page {@link Page} of all medicines from database
     */
    public Page<Medicine> findAll(Pageable pageable) {
        return medicineRepository.findAll(pageable);
    }

    /**
     * Finds medicines with specified parameters.
     *
     * @param lessThenPrice for medicines whose price is less or equal to specified
     * @param moreThenPrice for medicines whose price is greater or equal to specified
     * @param name          for medicines whose name contains specified String
     * @return Page {@link Page} with the result of query with arguments
     */
    public Page<Medicine> findAll(double lessThenPrice, double moreThenPrice, String name, Pageable pageable) {
        return medicineRepository
                .findMedicineByPriceLessThanAndPriceGreaterThanAndAndNameContaining
                        (lessThenPrice, moreThenPrice, name, pageable);
    }

    /**
     * This method is used to create new or update existed medicine
     *
     * @param medicine medicine, that we have to save
     * @return saved medicine
     */
    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    /**
     * Deletes a medicine with he specified ID if exists,
     * or do nothing if medicine already not exist
     *
     * @param id ID of the medicine, that we have to delete
     */
    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }
}
