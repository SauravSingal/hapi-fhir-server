package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.FhirEncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FhirEncounterRepository extends JpaRepository<FhirEncounterEntity, Long> {
}
