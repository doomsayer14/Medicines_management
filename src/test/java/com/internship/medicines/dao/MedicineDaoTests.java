package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicineDaoTests {

    @InjectMocks
    MedicineDao medicineDao;

    @Mock
    MedicineRepository medicineRepository;

    @Mock EntityManager entityManager;
    @Mock CriteriaBuilder criteriaBuilder;
    @Mock CriteriaQuery<Medicine> medicineCriteriaQuery;
    @Mock Root<Medicine> medicine;

    @Mock Predicate lessThenPricePredicate;
    @Mock Predicate moreThenPricePredicate;
    @Mock Predicate namePredicate;

    @Mock TypedQuery<Medicine> query;

    private static final Medicine MEDICINE = createMedicine(1L, "Nurofen",
            11.11, "first compound", "first contr", "first terms");

    private static final Long id = 1L;

    private static final Double LESS_THEN_PRICE = 9999.0;
    private static final Double MORE_THEN_PRICE = 0.0;
    private static final String NAME = "";

    private static Medicine createMedicine
            (Long id, String name, Double price, String comp, String contr, String terms) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setName(name);
        medicine.setPrice(price);
        medicine.setCompound(comp);
        medicine.setContraindications(contr);
        medicine.setTermsOfUse(terms);
        return medicine;
    }

    private static List<Medicine> initMedicineList() {
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineList.add(createMedicine(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return medicineList;
    }

    @Test
    public void testExistById() {
        when(medicineDao.existsById(id)).thenReturn(true);

        boolean result = medicineRepository.existsById(id);

        assertTrue(result);
        verify(medicineRepository).existsById(id);
    }

    @Test
    public void testFindById() {
        when(medicineDao.findById(id)).thenReturn(MEDICINE);

        Medicine resultMedicine = medicineRepository.getById(id);

        assertEquals(MEDICINE, resultMedicine);
        verify(medicineRepository).getById(id);
    }

    @Test
    public void testFindAll() {
        List<Medicine> expectedList = initMedicineList();
        when(medicineDao.findAll()).thenReturn(expectedList);

        List<Medicine> resultList = medicineRepository.findAll();

        assertEquals(expectedList, resultList);
        verify(medicineRepository).findAll();
    }

    //findAll(...)
    @Test
    public void testFindAllWithParams() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Medicine.class)).thenReturn(medicineCriteriaQuery);

        when(medicineCriteriaQuery.from(Medicine.class)).thenReturn(medicine);
        when(criteriaBuilder.lessThanOrEqualTo(medicine.get("price"), LESS_THEN_PRICE))
                .thenReturn(lessThenPricePredicate);
        when(criteriaBuilder.greaterThanOrEqualTo(medicine.get("price"), MORE_THEN_PRICE))
                .thenReturn(moreThenPricePredicate);
        when(criteriaBuilder.like(medicine.get("name"), "%" + NAME + "%")).thenReturn(namePredicate);
        medicineCriteriaQuery.where(lessThenPricePredicate, moreThenPricePredicate, namePredicate);

        when(entityManager.createQuery(medicineCriteriaQuery)).thenReturn(query);
        List<Medicine> expectedList = query.getResultList();

        List<Medicine> resultList = medicineDao.findAll(LESS_THEN_PRICE, MORE_THEN_PRICE, NAME);

        assertEquals(expectedList, resultList);
    }

    @Test
    public void testSave() {
        when(medicineDao.save(MEDICINE)).thenReturn(MEDICINE);

        Medicine resultList = medicineRepository.save(MEDICINE);

        assertEquals(MEDICINE, resultList);
        verify(medicineRepository).save(MEDICINE);
    }

    @Test
    public void testDelete() {
        doNothing().when(medicineRepository).deleteById(id);

        medicineDao.delete(id);

        verify(medicineRepository).deleteById(id);
    }
}
