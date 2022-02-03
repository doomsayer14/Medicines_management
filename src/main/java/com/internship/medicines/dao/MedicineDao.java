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

@Repository
public class MedicineDao {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private EntityManager em;

    public boolean existsById(Long id) {
        return medicineRepository.existsById(id);
    }

    public Medicine findById(Long id) {
        return medicineRepository.getById(id);
    }

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public List<Medicine> findAll(double lessThenPrice, double moreThenPrice, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Medicine> cq = cb.createQuery(Medicine.class);

        Root<Medicine> medicine = cq.from(Medicine.class);
        Predicate lessThenPricePredicate = cb.lessThanOrEqualTo(medicine.get("price"), lessThenPrice);
        Predicate moreThenPricePredicate = cb.greaterThanOrEqualTo(medicine.get("price"), moreThenPrice);
        Predicate namePredicate = cb.like(medicine.get("name"), "%" + name + "%");
        cq.where(lessThenPricePredicate, moreThenPricePredicate, namePredicate);

        TypedQuery<Medicine> query = em.createQuery(cq);
        return query.getResultList();
    }


    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }
}
