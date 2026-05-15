package com.healthcare.fhir.service;

import com.healthcare.fhir.entity.*;
import com.healthcare.fhir.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngestionService {

    private final Hl7MessageRepository hl7Repo;
    private final FhirPatientRepository patientRepo;
    private final FhirEncounterRepository encounterRepo;
    private final FhirValidationService validationService;

    @Transactional
    public void ingest(Map<String, Object> payload) {
        String rawHl7     = (String) payload.get("rawHl7");
        String messageId  = (String) payload.get("messageId");
        String msgType    = (String) payload.get("messageType");
        String patientJson   = (String) payload.get("patientResource");
        String encounterJson = (String) payload.get("encounterResource");

        // 1. Save raw HL7 audit record
        Hl7MessageEntity hl7 = new Hl7MessageEntity();
        hl7.setMessageId(messageId);
        hl7.setMessageType(msgType);
        hl7.setRawHl7(rawHl7);
        hl7.setStatus("RECEIVED");
        hl7Repo.save(hl7);
        log.info("Saved HL7 audit record for message: {}", messageId);

        // 2. Validate and save FHIR Patient
        try {
            Patient patient = validationService.parseAndValidatePatient(patientJson);
            String patientId = patient.getIdElement().getIdPart();

            FhirPatientEntity fp = patientRepo.findByPatientId(patientId)
                    .orElse(new FhirPatientEntity());
            fp.setPatientId(patientId);
            fp.setFhirJson(patientJson);
            fp.setSourceMsgId(messageId);
            patientRepo.save(fp);
            log.info("Saved FHIR Patient: {}", patientId);

            // 3. Validate and save FHIR Encounter
            Encounter encounter = validationService.parseAndValidateEncounter(encounterJson);
            String encounterId = encounter.getIdElement().getIdPart();

            FhirEncounterEntity fe = encounterRepo.findById(Long.parseLong("0"))
                    .orElse(new FhirEncounterEntity());
            fe.setEncounterId(encounterId);
            fe.setPatientId(patientId);
            fe.setFhirJson(encounterJson);
            fe.setSourceMsgId(messageId);
            encounterRepo.save(fe);
            log.info("Saved FHIR Encounter: {}", encounterId);

            // Mark HL7 as processed
            hl7.setStatus("PROCESSED");
            hl7Repo.save(hl7);

        } catch (Exception e) {
            hl7.setStatus("FAILED");
            hl7.setErrorMessage(e.getMessage());
            hl7Repo.save(hl7);
            log.error("Failed to process message {}: {}", messageId, e.getMessage());
            throw e;
        }
    }
}

