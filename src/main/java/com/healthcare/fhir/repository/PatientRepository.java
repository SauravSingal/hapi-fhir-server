package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {

    @Query("""
    SELECT p FROM PatientEntity p
    WHERE (:family IS NULL OR LOWER(p.familyName) LIKE LOWER(CONCAT('%', :family, '%')))
    AND (:startDate IS NULL OR p.birthDate >= :startDate)
    AND (:endDate IS NULL OR p.birthDate <= :endDate)
""")
    List<PatientEntity> searchPatients(String family,
                                       Date startDate,
                                       Date endDate);
}