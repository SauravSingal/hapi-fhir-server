package com.healthcare.fhir.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "testDB")
public class testDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
}
