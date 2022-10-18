package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WaterChargeService {
    List<WaterBill> calculate(Long usage);
}
