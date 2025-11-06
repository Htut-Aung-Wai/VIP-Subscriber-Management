package com.mytel.vip_subscriber_management.database.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CAIO_VIP_VIP_SUBSCRIBER_IMPORT_TEMP")
@Getter
@Setter
public class VipSubscriberImportTemp extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "TOKEN")
    private String token;
    @Column(name = "VIP_PACKAGE_ID")
    private String vipPackageId;

    @Column(name = "SUBSCRIBER_NO", nullable = false, unique = true)
    private String subscriberNo;

    @Column(name = "BRANCH")
    private String branchName;

    @Column(name = "PROPOSAL_DOCUMENT_NO")
    private String proposalDocumentNo;

    @Column(name = "REGISTRATION_DATE")
    private LocalDateTime registrationDate;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @Column(name = "UNIT_ID")
    private Long unitId;

    public VipSubscriberImportTemp() {}

    public VipSubscriberImportTemp(String token, String vipPackageId, String subscriberNo,
                                   String branchName, String proposalDocumentNo,LocalDateTime registrationDate,LocalDateTime expiryDate,Long unitId) {
        this.token = token;
        this.vipPackageId = vipPackageId;
        this.subscriberNo = subscriberNo;
        this.branchName = branchName;
        this.proposalDocumentNo = proposalDocumentNo;
        this.registrationDate=registrationDate;
        this.expiryDate=expiryDate;
        this.unitId=unitId;
    }
}
