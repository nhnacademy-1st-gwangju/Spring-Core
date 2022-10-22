package com.nhnacademy.edu.springframework.project.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@PropertySource("classpath:application.properties")
public class CsvDataParser implements DataParser {

    private List<WaterBill> list;
    @Value("${csv_path}")
    private String path;


    public CsvDataParser() {
        this.list = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    @Override
    public List<WaterBill> parse(String path) throws IOException {
        try (InputStream inputStream = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                WaterBill waterBill = new WaterBill(split[1], split[2],
                        Integer.parseInt(split[3]), Integer.parseInt(split[4]),
                        Integer.parseInt(split[5]), Long.parseLong(split[6]));
                list.add(waterBill);
            }
            return this.list;
        }
    }
}
