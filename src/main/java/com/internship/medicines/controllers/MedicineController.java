package com.internship.medicines.controllers;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "?lessThenPrice&moreThenPrice&name&details")
    @ResponseBody
    public ResponseEntity<List<Medicine>> readAllMedicine(
            @RequestParam int lessThenPrice,
            @RequestParam int moreThenPrice,
            @RequestParam String name
    ) {
        List<Medicine> medicineList = medicineService.readAllMedicine
                (lessThenPrice, moreThenPrice, name);
        if (medicineList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(medicineList);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create")
    @ResponseBody
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        if (medicine != null) {
            return new ResponseEntity<>(medicineService.createMedicine(medicine), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<Medicine> readMedicine(@RequestBody @PathVariable Long id) {
        Medicine medicine = medicineService.readMedicine(id);
        if (medicine != null) {
            return ResponseEntity.ok(medicine);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/update/{id}")
    @ResponseBody
    public ResponseEntity<Medicine> updateMedicine(@RequestBody Medicine medicine, @PathVariable Long id) {
        if (medicineService.readMedicine(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Medicine medicine1 = medicineService.updateMedicine(medicine, id);
        if (medicine1 != null) {
            return ResponseEntity.ok(medicine);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteMedicine(@RequestBody @PathVariable Long id) {
        if (medicineService.readMedicine(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medicineService.deleteMedicine(id);
        if (medicineService.readMedicine(id) == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //could be OK or NOT_FOUND
    }
}
