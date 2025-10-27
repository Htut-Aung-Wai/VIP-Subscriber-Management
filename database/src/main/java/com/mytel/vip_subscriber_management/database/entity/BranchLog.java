package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_BRANCH_LOG")
public class BranchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRANCH_CODE", updatable = false)
    private String branchCode;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_MANAGER_NAME")
    private String branchManagerName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Column(name = "REMARK", length = 1000)
    private String remark;

    @Column(name = "ACTION")
    private String action;

    @Column(length = 1000, name = "ORIGINAL_FIELDS")
    private String originalFields;

    @Column(length = 1000, name = "UPDATED_FIELDS")
    private String updatedFields;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Rangoon")
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

}
