package com.healthcare.fhir.controller;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.healthcare.fhir.entity.PatientEntity;
import com.healthcare.fhir.service.PatientService;
import com.healthcare.fhir.util.FhirConverter;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PatientController implements IResourceProvider {

    private final PatientService patientService;
    private final FhirConverter converter;

    @Override
    public Class<Patient> getResourceType() { return Patient.class; }

    // CREATE: POST /fhir/Patient
    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {
        PatientEntity saved = patientService.create(converter.toEntity(patient));
        MethodOutcome outcome = new MethodOutcome();
        outcome.setId(new IdType("Patient", saved.getId(), "1"));
        outcome.setCreated(true);
        return outcome;
    }

    // READ: GET /fhir/Patient/{id}
    @Read
    public Patient readPatient(@IdParam IdType id) {
        PatientEntity entity = patientService.findById(id.getIdPart())
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return converter.toFhirPatient(entity);
    }

    // UPDATE: PUT /fhir/Patient/{id}
    @Update
    public MethodOutcome updatePatient(@IdParam IdType id,
                                       @ResourceParam Patient patient) {
        patient.setId(id.getIdPart());
        PatientEntity updated = patientService.update(converter.toEntity(patient));
        return new MethodOutcome(new IdType("Patient", updated.getId()));
    }

    // DELETE: DELETE /fhir/Patient/{id}
    @Delete
    public void deletePatient(@IdParam IdType id) {
        patientService.delete(id.getIdPart());
    }

    // SEARCH: GET /fhir/Patient?family=Smith&birthdate=gt1980
    @Search
    public List<Patient> search(
            @RequiredParam(name = Patient.SP_FAMILY) StringParam family,
            @OptionalParam(name = Patient.SP_BIRTHDATE) DateRangeParam birthDate
    ) {
        return patientService.search(family, birthDate);
    }
}
