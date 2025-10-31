package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CAIO_VIP_UNIT_HEAD")
@ToString(exclude = "unit")
public class UnitHead {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "UNIT_HEAD_FULL_NAME", nullable = false)
    @NotBlank(message = "Unit Head's Name Required!")
    private String unitHeadFullName;

    @Column(name = "VMY_CODE", nullable = false, unique = true)
    @NotBlank(message = "VMY Code Required!")
    private String vmyCode;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Email(message = "Email format is invalid.")
    @NotBlank(message = "Email Required!")
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    @NotBlank(message = "Phone Number Required!")
    private String phoneNumber;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "REMARK", length = 1000)
    private String remark;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime lastUpdatedAt;


    @OneToOne
    @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID", unique = true)
    @JsonManagedReference
    private Unit unit;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

    public void assignUnit(Unit unit) {
        this.unit = unit;
        if (unit != null && unit.getUnitHead() == null) {
            unit.setUnitHead(this);
        }
    }

}