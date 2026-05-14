package com.healthcare.fhir.util;

import com.healthcare.fhir.entity.PatientEntity;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class FhirConverter {


    // Entity → FHIR Patient Resource
    public Patient toFhirPatient(PatientEntity entity) {
        Patient patient = new Patient();
        patient.setId(entity.getId());

        HumanName name = new HumanName();
        name.setFamily(entity.getFamilyName());
        name.addGiven(entity.getGivenName());
        patient.addName(name);

        patient.setBirthDate(Date.from(
                entity.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        patient.setActive(entity.isActive());
        return patient;
    }

    // FHIR Patient Resource → Entity
    public PatientEntity toEntity(Patient patient) {
        PatientEntity entity = new PatientEntity();
        if (patient.hasName()) {
            HumanName name = patient.getNameFirstRep();
            entity.setFamilyName(name.getFamily());
            entity.setGivenName(name.getGivenAsSingleString());
        }
        entity.setBirthDate(patient.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        entity.setActive(patient.getActive());
        return entity;
    }
}