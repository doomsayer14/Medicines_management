package com.internship.medicines.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The MedicineNotFoundException can be thrown, when some methods cannot find such
 * {@link com.internship.medicines.entities.Medicine} in database.
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicineNotFoundException extends RuntimeException {

    public MedicineNotFoundException(String message) {
        super(message);
    }

    public MedicineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
