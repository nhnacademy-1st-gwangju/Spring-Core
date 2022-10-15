package com.nhnacademy.edu.springframework.project.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvScores implements Scores {

    private static CsvScores instance;
    private List<Score> list;
    private String path;

    private CsvScores() {
        this.list = new ArrayList<>();
        this.path = "src/main/resources/data/score.csv";
    }

    public void setPath(String path) {
        this.path = path;
    }

    /** TODO 2 :
     * Java Singleton 패턴으로 getInstance() 를 구현하세요.
     **/
    public static Scores getInstance() {
        if (instance == null) {
            instance = new CsvScores();
        }
        return instance;
    }

    // TODO 5 : score.csv 파일에서 데이터를 읽어 멤버 변수에 추가하는 로직을 구현하세요.
    @Override
    public void load() throws IOException {
        try (InputStream inputStream = new FileInputStream(path);
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
