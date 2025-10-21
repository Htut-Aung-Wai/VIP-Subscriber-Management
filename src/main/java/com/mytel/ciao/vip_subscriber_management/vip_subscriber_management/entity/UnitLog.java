package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT_LOG")
public class UnitLog {

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

    @Column(name = "REMARK", length = 1000)
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private ActionType action;

    @Column(length = 1000, name = "ORIGINAL_FIELDS")
    private String originalFields;

    @Column(length = 1000, name = "UPDATED_FIELDS")
    private String updatedFields;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp lastUpdatedAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
        lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public enum ActionType {
        CREATED, UPDATED, DELETED
    }

}
