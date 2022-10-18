package com.nhnacademy.edu.springframework.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultChargeRepository implements ChargeRepository {

    private DataParser parser;
    private List<WaterBill> waterBills;

    @Autowired
    public DefaultChargeRepository(DataParser parser) {
        this.parser = parser;
        this.waterBills = new ArrayList<>();
    }

    public List<WaterBill> getWaterBills() {
        return waterBills;
    }

    @Override
    public void load(String path) throws IOException {
        this.waterBills = parser.parse(path);
    }

    @Override
    public List<WaterBill> find(Long usage) {
        List<WaterBill> data = new ArrayList<>();

        for (WaterBill waterBill : waterBills.stream().filter(o -> (o.getEnd() > usage) && (usage >= o.getStart())).collect(Collectors.toList())) {
            waterBill.setBillTotal(usage * waterBill.getUnitPrice());
            data.add(waterBill);
        }

        return data;
    }
}
