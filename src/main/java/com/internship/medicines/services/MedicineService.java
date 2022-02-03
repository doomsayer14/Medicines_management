package com.internship.medicines.services;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.dao.MedicineDao;
import com.internship.medicines.exceptions.MedicineNotFoundException;
import com.internship.medicines.mappers.MedicineToMedicineDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Medicine controller.
 * The MedicineController class can create, read, update
 * or delete medicines from the database. Also provides
 * specified endpoints and HTTP methods.
 */

@Service
public class MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    @Autowired
    private EntityManager em;

    private final MedicineToMedicineDtoMapper medicineMapper = new MedicineToMedicineDtoMapper();

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

    public MedicineDto createMedicine(MedicineDto medicineDto) {
        return medicineMapper.mapEntity(medicineDao.save(medicineMapper.mapDto(medicineDto)));
    }

    public MedicineDto readMedicine(Long id) throws MedicineNotFoundException {
        if (!medicineDao.existsById(id)) {
            throw new MedicineNotFoundException("Don't have any medicine with id = " + id + ".");
        }
        Medicine medicine = medicineDao.findById(id);
        return medicineMapper.mapEntity(medicine);
    }

    public MedicineDto updateMedicine(Medicine medicine, Long id) throws MedicineNotFoundException {
        if (!medicineDao.existsById(id)) {
            throw new MedicineNotFoundException("Don't have any medicine with id " + id + ".");
        }
        medicineDao.save(medicine);
        if (medicineDao.findById(id) == medicine) {
            return medicineMapper.mapEntity(medicine);
        }
        return null;
    }

    public void deleteMedicine(Long id) {
        if (medicineDao.existsById(id)) {
            medicineDao.delete(id);
        }
    }
}
