package com.healthcare.fhir.config;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirConfig {

    @Bean
    public FhirContext getFhirContext() {
        return FhirContext.forR4();
    }

    public IParser fhirJsonParser() {
        return FhirContext.forR4().newJsonParser().setPrettyPrint(true);
    }
}
