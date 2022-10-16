package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CsvStudents implements Students {

    private static CsvStudents instance = new CsvStudents();

    private Collection<Student> list;
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    private CsvStudents() {
        this.list = new ArrayList<>();
        this.path = "src/main/resources/data/student.csv";
    }

    /** TODO 3 :
     * Java Singleton 패턴으로 getInstance() 를 구현하세요.
     **/
    public static Students getInstance() {
        return instance;
    }

    // TODO 7 : student.csv 파일에서 데이터를 읽어 클래스 멤버 변수에 추가하는 로직을 구현하세요.
    // 데이터를 적재하고 읽기 위해서, 적절한 자료구조를 사용하세요.
    @Override
    public void load() throws IOException {
        try (InputStream inputStream = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                Student student = new Student(Integer.parseInt(split[0]), split[1]);
                list.add(student);
            }
        }
    }

    @Override
    public Collection<Student> findAll() throws IOException {
        if (this.list.isEmpty()) {
            load();
            List<Score> scores = CsvScores.getInstance().findAll();
            merge(scores);
        }
        return this.list;
    }

    /**
     * TODO 8 : students 데이터에 score 정보를 추가하세요.
     * @param scores
     */
    @Override
    public void merge(Collection<Score> scores) {
        for (Student student : list) {
            for (Score score : scores) {
                if (student.getSeq() == score.getStudentSeq()) {
                    student.setScore(score);
                }
            }
        }
    }
}
