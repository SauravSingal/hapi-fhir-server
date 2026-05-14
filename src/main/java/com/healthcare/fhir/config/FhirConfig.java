package com.healthcare.fhir.config;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirConfig {

    @Bean
    public FhirContext fhirContext() {
        FhirContext ctx = FhirContext.forR4();
        // Performance: reuse this singleton everywhere
        //30,000 milliseconds = 30 seconds
        ctx.getRestfulClientFactory().setSocketTimeout(30_000);
        return ctx;
    } // this will give object that will connect to fhir server and will timeout if no connection is made in 30 sec

    @Bean
    public IParser jsonParser(FhirContext ctx) {
        return ctx.newJsonParser().setPrettyPrint(true);
    } /*→ creates a parser that can:
        convert FHIR Java objects → JSON
        convert JSON → FHIR Java objects*/

    @Bean
    public IParser xmlParser(FhirContext ctx) {
        return ctx.newXmlParser();
    }/*Creates a parser that can:
    convert FHIR Java objects → XML
    convert XML → FHIR Java objects*/

}
