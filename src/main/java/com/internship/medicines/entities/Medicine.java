package com.internship.medicines.entities;

import lombok.Data;

import javax.persistence.*;

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
    private float price;

    @Column(name = "compound")
    private String compound;

    @Column(name = "contraindications")
    private String contraindications;

    @Column(name = "terms_of_use")
    private String termsOfUse;
}
