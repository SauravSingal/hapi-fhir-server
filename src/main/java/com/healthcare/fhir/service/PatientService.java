package com.healthcare.fhir.service;

import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.healthcare.fhir.entity.PatientEntity;
import com.healthcare.fhir.repository.PatientRepository;
import com.healthcare.fhir.util.FhirConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository repository;
    private final FhirConverter fhirConverter;


    public PatientEntity create(PatientEntity patient) {
        patient.setId(UUID.randomUUID().toString());
        return repository.save(patient);
    }

    public Optional<PatientEntity> findById(String id) {
        return repository.findById(id);
    }

    public PatientEntity update(PatientEntity patient) {
        if (!repository.existsById(patient.getId())) {
            throw new ResourceNotFoundException("Patient not found: " + patient.getId());
        }
        return repository.save(patient);
    }

    public void delete(String id) {
        PatientEntity patient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient: " + id));
        patient.setActive(false);  // Logical (soft) delete
        repository.save(patient);
    }

    public List<Patient> search(StringParam family,
                                DateRangeParam birthDate) {

        String familyValue = family != null ? family.getValue() : null;

        Date startDate = null;
        Date endDate = null;

        if (birthDate != null) {
            if (birthDate.getLowerBoundAsInstant() != null) {
                startDate = Date.from(birthDate.getLowerBoundAsInstant().toInstant());
            }
            if (birthDate.getUpperBoundAsInstant() != null) {
                endDate = Date.from(birthDate.getUpperBoundAsInstant().toInstant());
            }
        }
        List<PatientEntity> entities =
                repository.searchPatients(familyValue, startDate, endDate);

        return entities.stream()
                .map(fhirConverter::toFhirPatient)
                .toList();
    }
}
