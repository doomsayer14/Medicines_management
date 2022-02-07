package com.internship.medicines.services;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.exceptions.MedicineNotFoundException;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class for {@link Medicine}.
 * Contains business logic realization needed for controllers.
 */

@Service
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    @Autowired
    private MedicineToMedicineDtoMapper medicineMapper;

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
    public List<MedicineDto> readAllMedicine(Double lessThenPrice, Double moreThenPrice, String name) {
        //when lessThenPrice < moreThenPrice, result of their queries will not have general elements
        if (lessThenPrice < moreThenPrice) {
            throw new MedicineNotFoundException("Wrong parameters: lessThenPrice < moreThenPrice");
        }
        List<Medicine> resultList;
        //if no arguments
        if (lessThenPrice == 9999.0 && moreThenPrice == 0.0 && name.equals("")) {
            resultList = medicineDao.findAll();
            if (resultList.isEmpty()) {
                throw new MedicineNotFoundException("No elements in database");
            }
            return medicineMapper.mapList(resultList);
        }

        resultList = medicineDao.findAll(lessThenPrice, moreThenPrice, name);
        if (resultList.isEmpty()) {
            throw new MedicineNotFoundException("No such elements with this params");
        }
        return medicineMapper.mapList(resultList);
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
     * @param medicine updated medicine, that should replace old one
     * @param id ID of particular medicine
     * @return updated medicine, or null when something went wrong
     * @throws MedicineNotFoundException - when there is no medicine with specified id
     */
    public MedicineDto updateMedicine(Medicine medicine, Long id) {
        if (!medicineDao.existsById(id)) {
            throw new MedicineNotFoundException("Don't have any medicine with id " + id + ".");
        }
        medicineDao.save(medicine);
        if (medicineDao.findById(id) == medicine) {
            return medicineMapper.mapEntity(medicine);
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
        if (medicineDao.existsById(id)) {
            medicineDao.delete(id);
        }
    }
}
