package com.example.Yieldstreeet.YieldstreetTask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accreditations")
public class Accreditation {
    @Id
    @GeneratedValue
    private UUID accreditationId;

    @NotNull
    @Pattern(regexp = "BY_INCOME|BY_NET_WORTH", message = "Invalid accreditation type")
    private String accreditationType;

    @NotNull
    @Pattern(regexp = "PENDING|CONFIRMED|EXPIRED|FAILED", message = "Invalid accreditation status")
    private String status = "PENDING";
    private LocalDateTime lastUpdated;

    @Lob
    private String documentContent;
    private String documentName;
    private String documentMimeType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Accreditation() {}
    public Accreditation(String accreditationType, Document document, User user) {
        this.accreditationType = accreditationType;
        this.status = "PENDING";
        this.documentName = document.getName();
        this.documentMimeType = document.getMimeType();
        this.documentContent = document.getContent();
        this.user = user;
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastUpdated = LocalDateTime.now();
    }

    public UUID getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(UUID accreditationId) {
        this.accreditationId = accreditationId;
    }

    public String getAccreditationType() {
        return accreditationType;
    }

    public void setAccreditationType(String accreditationType) {
        this.accreditationType = accreditationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentMimeType() {
        return documentMimeType;
    }

    public void setDocumentMimeType(String documentMimeType) {
        this.documentMimeType = documentMimeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
