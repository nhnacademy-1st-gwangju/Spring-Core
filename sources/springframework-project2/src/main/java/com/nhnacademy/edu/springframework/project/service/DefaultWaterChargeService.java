package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.ChargeRepository;
import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultWaterChargeService implements WaterChargeService {

    private ChargeRepository chargeRepository;

    @Autowired
    public DefaultWaterChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @Override
    public List<WaterBill> calculate(Long usage) {
        return chargeRepository.find(usage);
    }
}
