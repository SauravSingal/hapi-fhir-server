package com.healthcare.fhir.controller;

import com.healthcare.fhir.service.IngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/hl7")
@RequiredArgsConstructor
@Slf4j
public class IngestionController {

    private final IngestionService ingestionService;

    /**
     * Called by Mirth Connect after transforming HL7 v2 to FHIR.
     * Expected payload:
     * {
     *   "messageId": "MSG001",
     *   "messageType": "ADT^A01",
     *   "rawHl7": "MSH|^~\\&|...",
     *   "patientResource": "{\"resourceType\":\"Patient\",...}",
     *   "encounterResource": "{\"resourceType\":\"Encounter\",...}"
     * }
     */
    @PostMapping("/ingest")
    public ResponseEntity<Map<String, String>> ingest(
            @RequestBody Map<String, Object> payload) {
        log.info("Received HL7 ingest request for message: {}",
                payload.get("messageId"));
        ingestionService.ingest(payload);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "HL7 message processed and FHIR resources saved"
        ));
    }
}

