package com.healthcare.fhir.config;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(urlPatterns = {"/fhir/*"}, loadOnStartup = 1)
public class HapiServerConfig extends RestfulServer {

    @Autowired
    private PatientResourceProvider patientProvider;
    @Autowired
    private ObservationResourceProvider obsProvider;

    @Override
    protected void initialize() {
        // Register FHIR version
        setFhirContext(FhirContext.forR4());

        // Register resource providers
        setResourceProviders(patientProvider, obsProvider);

        // Register interceptors
        registerInterceptor(new AuditInterceptor());
        registerInterceptor(new ValidationInterceptor());

        // JSON default
        setDefaultResponseEncoding(EncodingEnum.JSON);

//        // Enable CORS for browser clients
//        CorsInterceptor corsInterceptor = new CorsInterceptor();
//        registerInterceptor(corsInterceptor);
    }
}