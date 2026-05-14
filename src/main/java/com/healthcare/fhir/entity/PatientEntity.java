package com.healthcare.fhir.entity;

import com.ibm.icu.util.GenderInfo;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data  // Lombok: generates getters/setters
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private String gender;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "fhir_json", columnDefinition = "TEXT")
    private String fhirJson;  // Full FHIR JSON blob

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    // Version for optimistic locking
//    @Version
//    private Long version;
}