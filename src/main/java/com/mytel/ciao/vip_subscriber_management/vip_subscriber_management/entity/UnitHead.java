package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import lombok.*;

@Entity
@Data
@Table(name = "unit_head")
public class UnitHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_code")
    private String unitCode;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_full_name")
    private String unitFullName;

    private String email;

    @Column(name = "phone")
    private String phoneNumber;

    public UnitHead(String unitCode, String unitName, String unitFullName, String email, String phoneNumber) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.unitFullName = unitFullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
