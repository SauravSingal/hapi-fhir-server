package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.FhirPatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FhirPatientRepository extends JpaRepository<FhirPatientEntity, Long> {
    Optional<FhirPatientEntity> findByPatientId(String patientId);
}
