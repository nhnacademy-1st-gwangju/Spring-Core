package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.Score;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class DefaultGradeQueryService implements GradeQueryService {

    private Students students;

    @Autowired
    public DefaultGradeQueryService(Students students) {
        this.students = students;
    }

    @Override
    public List<Score> getScoreByStudentName(String name) throws IOException, NoSuchElementException {
        // TODO 5: 학생 이름으로 점수를 반환합니다. 동명이인 고려합니다.
        // resources/data/student.csv 를 보면 학번, 이름으로 구성되어있습니다. 학번은 숫자, 이름은 알파벳입니다.
        // resources/data/score.csv 를 보면 학번, 점수로 구성되어있습니다. 학번은 숫자, 점수는 숫자입니다.
        //
        // 만약 getScoreByStudentName() 인자 name에 동명이인인 학생이름을 넣으면, 동명이인의 Score 들을 리턴합니다.
        // 인자 - 학생이름
        // 반환 - 점수리스트
        //
        // Hint. CsvStudents 클래스의 findAll() 이 있네요? 적절히 필터링하고 찾아오면 되겠죠?
        //
        List<Student> collect = students.findAll()
                .stream()
                .filter(o -> o.getName().equals(name))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new NoSuchElementException();
        }

        List<Score> scores = new ArrayList<>();
        for (Student student : collect) {
            scores.add(student.getScore());
        }

        return scores;
    }

    @Override
    public Score getScoreByStudentSeq(int seq) throws IOException, NoSuchElementException {
        // TODO 6 : 학번으로 점수를 반환합니다. seq 인자가 학번입니다.

        return students.findAll()
                .stream()
                .filter(o-> o.getSeq() == seq)
                .findAny()
                .orElseThrow(NoSuchElementException::new)
                .getScore();
    }
}
