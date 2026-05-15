package com.healthcare.fhir.controller;


import com.healthcare.fhir.entity.FhirEncounterEntity;
import com.healthcare.fhir.entity.FhirPatientEntity;
import com.healthcare.fhir.repository.FhirEncounterRepository;
import com.healthcare.fhir.repository.FhirPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fhir")
@RequiredArgsConstructor
public class FhirController {

    private final FhirPatientRepository patientRepo;
    private final FhirEncounterRepository encounterRepo;

    // GET /fhir/Patient/{id}
    @GetMapping(value = "/Patient/{id}")
    public ResponseEntity<String> getPatient(@PathVariable String id) {
        return patientRepo.findByPatientId(id)
                .map(p -> ResponseEntity.ok(p.getFhirJson()))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /fhir/Patient — returns all patients
    @GetMapping(value = "/Patient",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllPatients() {
        List<String> patients = patientRepo.findAll()
                .stream().map(FhirPatientEntity::getFhirJson).toList();
        return ResponseEntity.ok(patients);
    }

    // GET /fhir/Encounter?patient={patientId}
    @GetMapping(value = "/Encounter")
    public ResponseEntity<List<String>> getEncountersByPatient(
            @RequestParam String patient) {
        List<String> encounters = encounterRepo.findByPatientId(patient)
                .stream().map(FhirEncounterEntity::getFhirJson).toList();
        return ResponseEntity.ok(encounters);
    }
}
