package com.internship.medicines.controllers;

import com.internship.medicines.dto.MedicineDto;
import com.internship.medicines.entities.Medicine;
import com.internship.medicines.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Medicine controller.
 * The MedicineController class can create, read, update
 * or delete medicines from the database. Also provides
 * specified endpoints and HTTP methods.
 */

//@Api(value = "MedicinesController",
//        produces = "application/json",
//        consumes = "application/json")
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

//    @ApiOperation(value = "Get all medicines from database",
//            notes = "Returns Page of medicines",
//            response = Medicine.class,
//            responseContainer = "Page")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Medicine>> readAllMedicine(
            @RequestParam(required = false, defaultValue = "9999.0") Double lessThenPrice,
            @RequestParam(required = false, defaultValue = "0.0") Double moreThenPrice,
            @RequestParam(required = false, defaultValue = "") String name,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Medicine> medicinePage =
                medicineService.readAllMedicine(lessThenPrice, moreThenPrice, name, pageable);
        return ResponseEntity.ok(medicinePage);
    }

//    @ApiOperation(value = "Create a medicines",
//            notes = "Returns Page of medicines",
//            response = MedicineDto.class)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicineDto> createMedicine(@RequestBody @Validated(MedicineDto.class) MedicineDto medicineDto) {
        return new ResponseEntity<>(medicineService.createMedicine(medicineDto), HttpStatus.CREATED);
    }

//    @ApiOperation(value = "Read a particular medicine from database",
//            notes = "Returns a medicines",
//            response = MedicineDto.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public ResponseEntity<MedicineDto> readMedicine(@PathVariable Long id) {
        MedicineDto medicineDto = medicineService.readMedicine(id);
        return ResponseEntity.ok(medicineDto);
    }

//    @ApiOperation(value = "Update a medicine",
//            notes = "Returns an updated medicine",
//            response = MedicineDto.class)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public ResponseEntity<MedicineDto> updateMedicine
            (@RequestBody Medicine medicine, @PathVariable Long id) {
        MedicineDto medicineDto = medicineService.updateMedicine(medicine, id);
        if (medicineDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(medicineDto);
    }

//    @ApiOperation(value = "Delete a medicine")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}