package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.DefaultChargeRepository;
import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DefaultWaterChargeServiceTest {

    @InjectMocks
    DefaultWaterChargeService chargeService;

    @Mock
    DefaultChargeRepository chargeRepository;

    @Test
    @DisplayName("chargeService의 calculate 실행 시 chargeRepository.find()가 실행된다.")
    void calculate() throws Exception {
        //given
        Long usage = 1000L;

        //when
        List<WaterBill> waterBills = chargeService.calculate(usage);

        //then
        then(chargeRepository).should(times(1)).find(usage);
    }
}