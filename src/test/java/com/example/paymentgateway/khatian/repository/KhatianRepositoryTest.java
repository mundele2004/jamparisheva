package com.example.paymentgateway.khatian.repository;

import com.example.paymentgateway.khatian.entity.KhatianMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class KhatianRepositoryTest {

    @Autowired
    private KhatianRepository khatianRepository;

    @Test
    void findsByOwnerNameIgnoringCaseAndVillageCode() {
        khatianRepository.save(record("121", "45", "Ramesh Das", "922855"));
        khatianRepository.save(record("122", "46", "Ramesh Das", "111111"));

        List<KhatianMaster> records = khatianRepository
                .findByOwnerNameContainingIgnoreCaseAndLgdVillageCode("ramesh", "922855");

        assertThat(records).hasSize(1);
        assertThat(records.get(0).getKhatianNo()).isEqualTo("121");
    }

    @Test
    void findsByKhatianNoAndVillageCode() {
        khatianRepository.save(record("121", "45", "Ramesh Das", "922855"));

        Optional<KhatianMaster> record = khatianRepository.findByKhatianNoAndLgdVillageCode("121", "922855");

        assertThat(record).isPresent();
        assertThat(record.get().getOwnerName()).isEqualTo("Ramesh Das");
    }

    @Test
    void findsByPlotNoAndVillageCode() {
        khatianRepository.save(record("121", "45", "Ramesh Das", "922855"));

        Optional<KhatianMaster> record = khatianRepository.findByPlotNoAndLgdVillageCode("45", "922855");

        assertThat(record).isPresent();
        assertThat(record.get().getKhatianNo()).isEqualTo("121");
    }

    private KhatianMaster record(String khatianNo, String plotNo, String ownerName, String villageCode) {
        return KhatianMaster.builder()
                .khatianNo(khatianNo)
                .plotNo(plotNo)
                .ownerName(ownerName)
                .lgdVillageCode(villageCode)
                .build();
    }
}
