package com.nhnacademy.edu.springframework.project.repository;

import java.io.IOException;
import java.util.List;

public interface ChargeRepository {
    void load(String path) throws IOException;
    List<WaterBill> find(Long usage);
}
