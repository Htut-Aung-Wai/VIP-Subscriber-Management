package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT_HEAD_LOG")
public class UnitHeadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UNIT_CODE")
    private String unitCode;

    @Column(name = "BRANCH_NAME")
    private String unitName;

    @Column(name = "UNIT_HEAD_FULL_NAME")
    private String unitHeadFullName;

    @Column(name = "VMY_CODE")
    private String vmyCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "REMARK", length = 1000)
    private String remark;

//    @Column(length = 1000, name = "ORIGINAL_FIELDS")
//    private String originalFields;
//
//    @Column(length = 1000, name = "UPDATED_FIELDS")
//    private String updatedFields;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime lastUpdatedAt;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        if (lastUpdatedAt == null) {
            lastUpdatedAt = createdAt;
        }
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}
