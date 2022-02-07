package com.internship.medicines.controllers;

import com.internship.medicines.dto.Response;
import com.internship.medicines.exceptions.MedicineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

/**
 * This class will handle all the exceptions, connected with medicines
 */

@ControllerAdvice(annotations = Controller.class)
public class MedicineExceptionHandler {

    /**
     * When {@link MedicineNotFoundException} occurs, this method handles it.
     * @param e MedicineNotFoundException
     * @return {@link ResponseEntity} with {@link Response} and {@link HttpStatus} code NOT_FOUND
     */
    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<Response> handleMedicineNotFoundException(MedicineNotFoundException e) {
        Response response = new Response(e.getMessage(), LocalTime.now(),HttpStatus.NOT_FOUND );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
