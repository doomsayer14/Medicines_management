package com.internship.medicines.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
        private Long id;

        private String name;

        private double price;

        private String compound;

        private String contraindications;

        private String termsOfUse;
}
