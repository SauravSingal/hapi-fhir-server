package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.FhirPatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FhirPatientRepository extends JpaRepository<FhirPatientEntity, Long> {
}
