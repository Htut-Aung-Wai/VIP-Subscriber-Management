package com.mytel.vip_subscriber_management.common.renewal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_RENEW_LOG_BY_MANAGER")
public class RenewByManagerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SUBSCRIBER_NO")
    private String subscriberNo;

    @Column(name = "DECISION")
    private String decision;

    @Column(name = "CONFIRMED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp confirmedAt;

    @Column(name = "BRANCH_MANAGER_NAME")
    private String branchManagerName;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @PreUpdate
    public void onUpdate() {
        if (confirmedAt != null) {
            confirmedAt = new Timestamp(System.currentTimeMillis());
        }
    }

}
