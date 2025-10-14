package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.logging.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT_LOG")
public class UnitHeadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UNIT_CODE", updatable = false)
    private String unitCode;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "UNIT_FULL_NAME")
    private String unitFullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;

    @Column(columnDefinition = "TEXT", name = "ORIGINAL_FIELDS")
    private String originalFields;

    @Column(columnDefinition = "TEXT", name = "CHANGED_FIELDS")
    private String changedFields;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdatedAt;

    public enum ActionType {
        CREATED, UPDATED, DELETED
    }

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        lastUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}
