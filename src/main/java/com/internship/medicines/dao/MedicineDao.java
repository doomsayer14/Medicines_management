package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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
     * @return List of all medicines from database
     */
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    /**
     * Finds medicines with specified parameters.
     *
     * @param lessThenPrice for medicines whose price is less or equal to specified
     * @param moreThenPrice for medicines whose price is greater or equal to specified
     * @param name          for medicines whose name contains specified String
     * @return List with the result of query with arguments
     */
    public List<Medicine> findAll(double lessThenPrice, double moreThenPrice, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicine> cq = cb.createQuery(Medicine.class);

        Root<Medicine> medicine = cq.from(Medicine.class);
        Predicate lessThenPricePredicate = cb.lessThanOrEqualTo(medicine.get("price"), lessThenPrice);
        Predicate moreThenPricePredicate = cb.greaterThanOrEqualTo(medicine.get("price"), moreThenPrice);
        Predicate namePredicate = cb.like(medicine.get("name"), "%" + name + "%");
        cq.where(lessThenPricePredicate, moreThenPricePredicate, namePredicate);

        TypedQuery<Medicine> query = entityManager.createQuery(cq);
        return query.getResultList();
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
