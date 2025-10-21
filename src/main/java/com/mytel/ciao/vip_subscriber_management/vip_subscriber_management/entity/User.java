package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_BRANCH_MANAGER")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "VMY_CODE", nullable = false, updatable = false)
    private String vmyCode;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

}
