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

/**
 * Medicine controller.
 * The MedicineController class can create, read, update
 * or delete medicines from the database. Also provides
 * specified endpoints and HTTP methods.
 */

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<MedicineDto>> readAllMedicine(
            @RequestParam(required = false, defaultValue = "9999.0") Double lessThenPrice,
            @RequestParam(required = false, defaultValue = "0.0") Double moreThenPrice,
            @RequestParam(required = false, defaultValue = "") String name
    ) {
        List<MedicineDto> medicineDtoList =
                medicineService.readAllMedicine(lessThenPrice, moreThenPrice, name);
        return ResponseEntity.ok(medicineDtoList);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MedicineDto> createMedicine(@RequestBody MedicineDto medicineDto) {
        return new ResponseEntity<>(medicineService.createMedicine(medicineDto), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<MedicineDto> readMedicine(@PathVariable Long id) {
        MedicineDto medicineDto = medicineService.readMedicine(id);
        return ResponseEntity.ok(medicineDto);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<MedicineDto> updateMedicine
            (@RequestBody Medicine medicine, @PathVariable Long id) {
        MedicineDto medicineDto = medicineService.updateMedicine(medicine, id);
        if (medicineDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(medicineDto);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
