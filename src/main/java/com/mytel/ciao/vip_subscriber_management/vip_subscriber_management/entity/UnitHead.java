package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT_HEAD")
public class UnitHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UNIT_CODE")
    private String unitCode;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "UNIT_FULL_NAME")
    private String unitFullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phoneNumber;

    public UnitHead(String unitCode, String unitName, String unitFullName, String email, String phoneNumber) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.unitFullName = unitFullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
