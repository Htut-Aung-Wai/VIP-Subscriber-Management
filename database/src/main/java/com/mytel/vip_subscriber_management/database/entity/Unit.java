package com.mytel.vip_subscriber_management.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "unitHead")
@Table(name = "CAIO_VIP_UNIT")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UNIT_CODE", updatable = false, nullable = false)
    @NotBlank(message = "Unit Code Required!")
    private String unitCode;

    @Column(name = "BRANCH_NAME", nullable = false)
    @NotBlank(message = "Unit Name Required!")
    private String unitName;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Yangon")
    private LocalDateTime lastUpdatedAt;


    @OneToOne(mappedBy = "unit", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonBackReference
    private UnitHead unitHead;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

    public void setUnitHead(UnitHead unitHead) {
        this.unitHead = unitHead;
        if (unitHead != null && unitHead.getUnit() == null) {
            unitHead.setUnit(this);
        }
    }
}
