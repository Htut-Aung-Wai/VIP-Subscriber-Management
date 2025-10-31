package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime confirmedAt;

    @Column(name = "UNIT_MANAGER_NAME")
    private String unitManagerName;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @PreUpdate
    public void onUpdate() {
        if (confirmedAt != null) {
            confirmedAt = LocalDateTime.now();
        }
    }

}
