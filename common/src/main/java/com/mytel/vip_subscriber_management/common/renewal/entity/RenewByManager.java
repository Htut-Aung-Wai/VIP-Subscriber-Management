package com.mytel.vip_subscriber_management.common.renewal.entity;

import com.example.vip_management.entity.VipSubscriber;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_RENEW_BY_MANAGER")
public class RenewByManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SUBSCRIBER_NO")
    private String subscriberNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DECISION")
    private Decision decision;

    @Column(name = "BRANCH")
    private String branchName;

    @Column(name = "CONFIRMED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp confirmedAt;

    public enum Decision {
        WILL_RENEW, NOT_RENEW
    }

    @PrePersist
    public void onCreate() {
        confirmedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        confirmedAt = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "SUBSCRIBER_ID", nullable = false)
    @JsonIgnore
    private VipSubscriber subscriber;

    @JsonProperty("subscriberNo")
    public String getSubscriberNo() {
        return subscriberNo != null ? subscriber.getSubscriberNo() : null;
    }
}
