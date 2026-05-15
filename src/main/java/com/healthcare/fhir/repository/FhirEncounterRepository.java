package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.FhirEncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FhirEncounterRepository extends JpaRepository<FhirEncounterEntity, Long> {
    List<FhirEncounterEntity> findByPatientId(String patientId);
}
