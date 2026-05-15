package com.healthcare.fhir.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "hl7_messages")
@Data
public class Hl7MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "message_type", nullable = false)
    private String messageType;

    @Column(name = "raw_hl7", nullable = false, columnDefinition = "TEXT")
    private String rawHl7;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private String status = "RECEIVED";

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
