package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "CAIO_VIP_VIP_SUBSCRIBER")
public class VipSubscriber extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

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

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    @JsonIgnore
    private Unit unit;

    @JsonProperty("unitId")
    public Long getUnitId() {
        return unit != null ? unit.getId() : null;
    }

}
