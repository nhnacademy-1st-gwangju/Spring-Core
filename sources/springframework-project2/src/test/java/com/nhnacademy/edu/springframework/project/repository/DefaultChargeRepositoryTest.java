package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class DefaultChargeRepositoryTest {

    @Autowired
    DefaultChargeRepository chargeRepository;

    @Test
    @DisplayName("load시 데이터를 불러온다.")
    void load() throws Exception {
        //given
        String path = "src/main/resources/data/Tariff_20220331.csv";

        //when
        chargeRepository.load(path);

        //then
        assertThat(chargeRepository.getWaterBills()).isNotEmpty();
    }

    @Test
    @DisplayName("입력 받은 사용량 대로 계산하고 결과 리스트를 반환한다.")
    void find() throws Exception {
        //given
        String path = "src/main/resources/data/Tariff_20220331.csv";
        chargeRepository.load(path);
        Long usage = 1000L;

        //when
        List<WaterBill> waterBills = chargeRepository.find(usage);

        //then
        assertThat(waterBills).isNotEmpty();
        assertThat(waterBills.get(0).getBillTotal()).isEqualTo(usage * waterBills.get(0).getUnitPrice());
    }
}