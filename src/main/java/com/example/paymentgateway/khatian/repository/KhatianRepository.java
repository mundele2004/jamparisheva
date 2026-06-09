package com.example.paymentgateway.khatian.repository;

import com.example.paymentgateway.khatian.entity.KhatianMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhatianRepository extends JpaRepository<KhatianMaster, Long> {
    List<KhatianMaster> findByOwnerNameContainingIgnoreCaseAndLgdVillageCode(String ownerName, String lgdVillageCode);

    Optional<KhatianMaster> findByKhatianNoAndLgdVillageCode(String khatianNo, String lgdVillageCode);

    Optional<KhatianMaster> findByPlotNoAndLgdVillageCode(String plotNo, String lgdVillageCode);
}
