package com.nhnacademy.edu.springframework.project.repository;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvScores implements Scores {

    private List<Score> list;

    public CsvScores() {
        this.list = new ArrayList<>();
    }

    /** TODO 2 :
     * Java Singleton 패턴으로 getInstance() 를 구현하세요.
     *
     * -> Spring Framework로 migration 시 Singleton Bean을 등록하도록 함.
     **/

    // TODO 5 : score.csv 파일에서 데이터를 읽어 멤버 변수에 추가하는 로직을 구현하세요.
    @Override
    public void load() throws IOException {
        try (InputStream inputStream = new FileInputStream("src/main/resources/data/score.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                Score score = new Score(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                list.add(score);
            }
        }
    }

    @Override
    public List<Score> findAll() throws IOException {
        if (this.list.isEmpty()) {
            load();
        }
        return this.list;
    }
}
