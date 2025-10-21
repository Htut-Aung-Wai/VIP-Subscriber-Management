package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT")
public class Unit {

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

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "REMARK", length = 1000)
    private String remark;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp lastUpdatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

}
