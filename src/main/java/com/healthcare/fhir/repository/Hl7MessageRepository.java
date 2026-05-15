package com.healthcare.fhir.repository;

import com.healthcare.fhir.entity.Hl7MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Hl7MessageRepository extends JpaRepository<Hl7MessageEntity, Long> {
    List<Hl7MessageEntity> findTop20ByOrderByReceivedAtDesc();
}
