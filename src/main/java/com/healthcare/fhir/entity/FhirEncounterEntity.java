package com.healthcare.fhir.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "fhir_encounters")
@Data
public class FhirEncounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_id", nullable = false, unique = true)
    private String encounterId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "fhir_json", nullable = false, columnDefinition = "TEXT")
    private String fhirJson;

    @Column(name = "source_msg_id")
    private String sourceMsgId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
