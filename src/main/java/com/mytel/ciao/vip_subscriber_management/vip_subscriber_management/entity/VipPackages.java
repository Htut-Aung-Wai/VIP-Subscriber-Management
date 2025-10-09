package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CAIO_VIP_PACKAGES")
public class VipPackages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "KIND_OF_VIP")
    private String kindOfVip;

    @Column(name = "MAIN_BALANCE")
    private String mainBalance;

    @Column(name = "DATA")
    private String data;

    @Column(name = "ON_NET")
    private Integer onNet;

    @Column(name = "OFF_NET")
    private Integer offNet;

    @Column(name = "MONTH")
    private Integer month;

}
