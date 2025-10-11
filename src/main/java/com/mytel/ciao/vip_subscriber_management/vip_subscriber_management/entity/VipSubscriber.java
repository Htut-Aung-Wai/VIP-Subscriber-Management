package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "CAIO_VIP_VIP_SUBSCRIBER")
public class VipSubscriber {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "VIP_PACKAGE_ID")
    private String vipPackageId;

    @Column(name = "SUBSCRIBER_NO")
    private String subscriberNo;

    @Column(name = "BRANCH")
    private String branchName;

    @Column(name = "PROPOSAL_DOCUMENT_NO")
    private String proposalDocumentNo;

    @Column(name = "REGISTRATION_DATE")
    private Timestamp registrationDate;

    @Column(name = "EXPIRY_DATE")
    private Timestamp expiryDate;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;




}
