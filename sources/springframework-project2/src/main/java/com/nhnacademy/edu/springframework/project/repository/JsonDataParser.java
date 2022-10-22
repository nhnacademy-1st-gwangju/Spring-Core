package com.nhnacademy.edu.springframework.project.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class JsonDataParser implements DataParser {

    private List<WaterBill> list;
    @Value("${json_path}")
    private String path;

    public JsonDataParser() {
        this.list = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    @Override
    public List<WaterBill> parse(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = new String(Files.readAllBytes(Paths.get(path)));

        List<Map<String, Object>> maps = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });

        for (Map<String, Object> map : maps) {
            String city = (String) map.get("지자체명");
            String sector = (String) map.get("업종");
            String level = map.get("단계").toString();
            String start = map.get("구간시작(세제곱미터)").toString();
            String end = map.get("구간끝(세제곱미터)").toString();
            String unitPrice = map.get("구간금액(원)").toString();

            list.add(new WaterBill(city, sector,
                    Integer.parseInt(level), Integer.parseInt(start),
                    Integer.parseInt(end), Long.parseLong(unitPrice)));
        }

        return this.list;
    }
}
