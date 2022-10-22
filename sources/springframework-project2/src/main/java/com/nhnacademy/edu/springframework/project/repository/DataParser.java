package com.nhnacademy.edu.springframework.project.repository;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface DataParser {
    List<WaterBill> parse(String path) throws IOException;
    String getPath();
}
