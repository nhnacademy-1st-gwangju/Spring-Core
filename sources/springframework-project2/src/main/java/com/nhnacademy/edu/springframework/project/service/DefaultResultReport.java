package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultResultReport implements ResultReport {

    @Override
    public void report(List<WaterBill> data) {

        List<WaterBill> result = data.stream()
                .sorted(Comparator.comparingLong(WaterBill::getBillTotal))
                .limit(5)
                .collect(Collectors.toList());

        for (WaterBill waterBill : result) {
            System.out.println(waterBill);
        }
    }
}
