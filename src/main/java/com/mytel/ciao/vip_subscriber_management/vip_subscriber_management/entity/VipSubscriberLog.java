package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "CAIO_VIP_VIP_SUBSCRIBER_LOG")
public class VipSubscriberLog{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "SUBSCRIBER_ID")
    private String subscriberId;

    @Column(name = "SUBSCRIBER_NO")
    private String subscriberNo;

    @Column(name = "ACTION_TYPE")
    private String actionType;

    @Column(name = "ACTION_USERNAME")
    private String actionUsername;

    @Column(name = "OLD_DATA",length = 1000)
    private String oldData;

    @Column(name = "NEW_DATA",length = 1000)
    private String newData;

    @Column(name ="IS_SUCCESS")
    private Boolean isSuccess;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Column(name ="DESCRIPTION")
    private String description;



}
