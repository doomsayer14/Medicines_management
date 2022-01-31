package com.internship.medicines.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Simple java class that represents Medicine.
 */

@Data
@Entity
@Table(name = "Medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "compound")
    private String compound;

    @Column(name = "contraindications")
    private String contraindications;

    @Column(name = "terms_of_use")
    private String termsOfUse;
}
