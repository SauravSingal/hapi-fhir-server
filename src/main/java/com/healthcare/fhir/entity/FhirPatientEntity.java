package com.healthcare.fhir.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "fhir_patients")
@Data
public class FhirPatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false, unique = true)
    private String patientId;

    @Column(name = "fhir_json", nullable = false, columnDefinition = "TEXT")
    private String fhirJson;

    @Column(name = "source_msg_id")
    private String sourceMsgId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}

