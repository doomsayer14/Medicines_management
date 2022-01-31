package com.internship.medicines.controllers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<MedicineDto>> readAllMedicine(
            @RequestParam(required = false, defaultValue = "99999.0") Double lessThenPrice,
            @RequestParam(required = false, defaultValue = "0.0") Double moreThenPrice,
            @RequestParam(required = false) String name
    ) {
        List<MedicineDto> medicineDtoList = medicineService.readAllMedicine
                (lessThenPrice, moreThenPrice, name);
        if (medicineDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(medicineDtoList);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MedicineDto> createMedicine(@RequestBody Medicine medicine) {
        if (medicine != null) {
            return new ResponseEntity<>(medicineService.createMedicine(medicine), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<MedicineDto> readMedicine(@RequestBody @PathVariable Long id) {
        MedicineDto medicineDto = medicineService.readMedicine(id);
        if (medicineDto != null) {
            return ResponseEntity.ok(medicineDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<MedicineDto> updateMedicine(@RequestBody Medicine medicine, @PathVariable Long id) {
        if (medicineService.readMedicine(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MedicineDto medicineDto = medicineService.updateMedicine(medicine, id);
        if (medicineDto != null) {
            return ResponseEntity.ok(medicineDto);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteMedicine(@RequestBody @PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
