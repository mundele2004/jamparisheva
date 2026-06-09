package com.example.paymentgateway.khatian.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "khatian_master")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhatianMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "khatian_no", length = 50)
    private String khatianNo;

    @Column(name = "plot_no", length = 50)
    private String plotNo;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "lgd_village_code", length = 20)
    private String lgdVillageCode;

    @Column(name = "lgd_district_code", length = 20)
    private String lgdDistrictCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
