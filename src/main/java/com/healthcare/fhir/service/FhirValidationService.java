package com.healthcare.fhir.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import com.healthcare.fhir.config.FhirConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FhirValidationService {

    private final FhirConfig fhirConfig;

    public Patient parseAndValidatePatient(String fhirJson) {
        FhirValidator validator = fhirConfig.fhirContext().newValidator();
        Patient patient = fhirConfig.fhirJsonParser(fhirConfig.fhirContext()).parseResource(Patient.class, fhirJson);
        ValidationResult result = validator.validateWithResult(patient);
        if (!result.isSuccessful()) {
            String errors = result.getMessages().toString();
            log.warn("FHIR Patient validation warnings: {}", errors);
        }
        log.info("Parsed FHIR Patient: {}", patient.getIdElement().getValue());
        return patient;
    }

    public Encounter parseAndValidateEncounter(String fhirJson) {
        FhirValidator validator = fhirConfig.fhirContext().newValidator();
        Encounter encounter = fhirConfig.fhirJsonParser(fhirConfig.fhirContext()).parseResource(Encounter.class, fhirJson);
        ValidationResult result = validator.validateWithResult(encounter);
        if (!result.isSuccessful()) {
            log.warn("FHIR Encounter validation warnings: {}", result.getMessages());
        }
        log.info("Parsed FHIR Encounter: {}", encounter.getIdElement().getValue());
        return encounter;
    }
}
