-- Raw HL7 messages — audit trail of everything received
CREATE TABLE IF NOT EXISTS hl7_messages (
                                            id              BIGSERIAL PRIMARY KEY,
                                            message_id      VARCHAR(100) NOT NULL,
    message_type    VARCHAR(20) NOT NULL,
    raw_hl7         TEXT NOT NULL,
    received_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    status          VARCHAR(20) NOT NULL DEFAULT 'RECEIVED',
    error_message   TEXT
    );

-- FHIR Patient resources converted from HL7 PID segment
CREATE TABLE IF NOT EXISTS fhir_patients (
                                             id              BIGSERIAL PRIMARY KEY,
                                             patient_id      VARCHAR(100) NOT NULL UNIQUE,
    fhir_json       TEXT NOT NULL,
    source_msg_id   VARCHAR(100),
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
    );

-- FHIR Encounter resources converted from HL7 PV1 segment
CREATE TABLE IF NOT EXISTS fhir_encounters (
                                               id              BIGSERIAL PRIMARY KEY,
                                               encounter_id    VARCHAR(100) NOT NULL UNIQUE,
    patient_id      VARCHAR(100) NOT NULL,
    fhir_json       TEXT NOT NULL,
    source_msg_id   VARCHAR(100),
    created_at      TIMESTAMP NOT NULL DEFAULT NOW()
    );
