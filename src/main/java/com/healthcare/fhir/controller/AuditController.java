package com.healthcare.fhir.controller;

import com.healthcare.fhir.entity.Hl7MessageEntity;
import com.healthcare.fhir.repository.Hl7MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuditController {

    private final Hl7MessageRepository hl7Repo;

    // GET /api/audit — last 20 HL7 messages with status
    @GetMapping("/audit")
    public ResponseEntity<List<Hl7MessageEntity>> getAuditLog() {
        return ResponseEntity.ok(
                hl7Repo.findTop20ByOrderByReceivedAtDesc()
        );
    }
}
