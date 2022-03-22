package com.internship.medicines.services;

import com.internship.medicines.auth.AuthService;
import com.internship.medicines.auth.UserRole;
import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.exceptions.MedicineNotFoundException;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for {@link Medicine}.
 * Contains business logic realization needed for {@link com.internship.medicines.controllers.MedicineController}.
 */

@Service
public class MedicineService {

    private final MedicineDao medicineDao;

    private final MedicineToMedicineDtoMapper medicineMapper;

    private final AuthService authService;

    public MedicineService(MedicineDao medicineDao, MedicineToMedicineDtoMapper medicineMapper, AuthService authService) {
        this.medicineDao = medicineDao;
        this.medicineMapper = medicineMapper;
        this.authService = authService;
    }

    /**
     * Finds medicines with specified parameters.
     *
     * @param lessThenPrice for medicines whose price is less or equal to specified
     * @param moreThenPrice for medicines whose price is greater or equal to specified
     * @param name          for medicines whose name contains specified String
     * @return List with the result of query with arguments
     * @throws MedicineNotFoundException - when there are no elements in database or
     * query didn't find any medicines with specified parameters
     */
    public Page<Medicine> readAllMedicine
    (Double lessThenPrice, Double moreThenPrice, String name, Pageable pageable) {
        //when lessThenPrice < moreThenPrice, result of their queries will not have general elements
        if (lessThenPrice < moreThenPrice) {
            return Page.empty();
        }
        Page<Medicine> resultPage;
        //if no arguments
        if (lessThenPrice == 9999.0 && moreThenPrice == 0.0 && name.equals("")) {
            resultPage = medicineDao.findAll(pageable);
            if (resultPage.isEmpty()) {
                throw new MedicineNotFoundException("No elements in database");
            }
            return resultPage;
        }
        //not default arguments
        resultPage = medicineDao.findAll(lessThenPrice, moreThenPrice, name, pageable);
        if (resultPage.isEmpty()) {
            throw new MedicineNotFoundException("No such elements with this params");
        }
        return resultPage;
    }

    /**
     * Firstly maps to {@link Medicine} to transfer in {@link MedicineDao}.
     * {@link MedicineDao}, in its turn, returns {@link Medicine},
     * which we have to map into {@link MedicineDto} for controller.
     *
     * @param medicineDto medicine, that we have to create
     * @return created medicine
     */
    public MedicineDto createMedicine(MedicineDto medicineDto) {
        if (!authService.verifyRole(UserRole.DOCTOR, UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return medicineMapper.mapEntity(medicineDao.save(medicineMapper.mapDto(medicineDto)));
    }

    /**
     * Search for medicine with specified ID in database
     *
     * @param id ID of particular medicine
     * @return medicine with specified id
     * @throws MedicineNotFoundException - when there is no medicine
     * with specified id
     */
    public MedicineDto readMedicine(Long id) {
        if (!medicineDao.existsById(id)) {
            throw new MedicineNotFoundException("Don't have any medicine with id = " + id + ".");
        }
        Medicine medicine = medicineDao.findById(id);
        return medicineMapper.mapEntity(medicine);
    }

    /**
     * Updates medicine if exists
     * @param newMedicine updated medicine, that should replace old one
     * @param id ID of particular medicine
     * @return updated medicine, or null when something went wrong
     * @throws MedicineNotFoundException - when there is no medicine with specified id
     */
    public MedicineDto updateMedicine(Medicine newMedicine, Long id) {
        if (!authService.verifyRole(UserRole.DOCTOR, UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!medicineDao.existsById(id)) {
            throw new MedicineNotFoundException("Don't have any medicine with id " + id + ".");
        }
        Medicine oldMedicine = medicineDao.findById(id);
        if (oldMedicine.equals(newMedicine)) {
            return medicineMapper.mapEntity(newMedicine);
        }

        //in case we want to update only one field, we just take old ones from oldMedicine
        newMedicine.setId(id);
        if (newMedicine.getName() == null) {
            newMedicine.setName(oldMedicine.getName());
        }
        if (newMedicine.getPrice() == 0) {
            newMedicine.setPrice(oldMedicine.getPrice());
        }
        if (newMedicine.getCompound() == null) {
            newMedicine.setCompound(oldMedicine.getCompound());
        }
        if (newMedicine.getContraindications() == null) {
            newMedicine.setContraindications(oldMedicine.getContraindications());
        }
        if (newMedicine.getTermsOfUse() == null) {
            newMedicine.setTermsOfUse(oldMedicine.getTermsOfUse());
        }

        medicineDao.save(newMedicine);
        if (medicineDao.findById(id).equals(newMedicine)) {
            return medicineMapper.mapEntity(newMedicine);
        }
        return null;
    }

    /**
     * Deletes a medicine with he specified ID if exists,
     * or do nothing if medicine already not exist
     *
     * @param id ID of particular medicine
     */
    public void deleteMedicine(Long id) {
        if (!authService.verifyRole(UserRole.DOCTOR, UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (medicineDao.existsById(id)) {
            medicineDao.delete(id);
        }
    }
}
