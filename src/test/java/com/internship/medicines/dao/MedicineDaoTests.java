package com.internship.medicines.dao;

import com.internship.medicines.entities.Medicine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Mock
    Pageable pageable;

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

    private static Page<Medicine> initMedicinePage() {
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(createMedicine(1L, "Nurofen",
                11.11, "first compound", "first contr", "first terms"));

        medicineList.add(createMedicine(2L, "Mukoltin",
                22.22, "second compound", "second contr", "second terms"));

        medicineList.add(createMedicine(3L, "Evkasolin",
                33.33, "third compound", "third contr", "third terms"));

        return new PageImpl<>(medicineList);
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
        Page<Medicine> expectedPage = initMedicinePage();
        when(medicineDao.findAll(LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable)).thenReturn(expectedPage);

        Page<Medicine> resultPage = medicineRepository
                .findMedicineByPriceLessThanAndPriceGreaterThanAndAndNameContaining
                        (LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable);

        assertEquals(expectedPage, resultPage);
        verify(medicineRepository)
                .findMedicineByPriceLessThanAndPriceGreaterThanAndAndNameContaining
                        (LESS_THEN_PRICE, MORE_THEN_PRICE, NAME, pageable);

    }

    //findAll(...)
    @Test
    public void testFindAllWithParams() {
        Page<Medicine> expectedPage = initMedicinePage();
        when(medicineDao.findAll(pageable)).thenReturn(expectedPage);

        Page<Medicine> resultPage = medicineRepository.findAll(pageable);

        assertEquals(expectedPage, resultPage);
        verify(medicineRepository).findAll(pageable);
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
