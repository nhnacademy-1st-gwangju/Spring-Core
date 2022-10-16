package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentServiceTest {

    DefaultStudentService defaultStudentService;
    CsvScores csvScores;
    CsvStudents csvStudents;

    @BeforeEach
    void init() {
        defaultStudentService = new DefaultStudentService();
        csvScores = (CsvScores) CsvScores.getInstance();
        csvStudents = (CsvStudents) CsvStudents.getInstance();

        // 'src/main/resource/data' 경로에 있는 데이터로 읽어와야 한다. 아닌 경우, 프로젝트 전체 Test를 한번에 실행할 시 Score에 null 값이 들어가게 된다.
        csvScores.setPath("src/main/resources/data/score.csv");
        csvStudents.setPath("src/main/resources/data/student.csv");
    }

    @Test
    @DisplayName("pass한 학생들이 정상적으로 반환된다.")
    void getPassedStudents() throws Exception {
        //given
        int expected = 6;

        //when
        Collection<Student> passedStudents = defaultStudentService.getPassedStudents();

        //then
        assertThat(passedStudents).hasSize(expected);
    }

    @Test
    @DisplayName("점수에 따라 내림차순 정렬된 결과가 반환된다.")
    void getStudentsOrderByScore() throws Exception {
        //when
        List<Student> orderByScore = (ArrayList<Student>) defaultStudentService.getStudentsOrderByScore();

        //then
        for (int i = 0; i < orderByScore.size()-1; i++) {
            assertThat(orderByScore.get(i).getScore().getScore())
                    .isGreaterThanOrEqualTo(orderByScore.get(i).getScore().getScore());
        }
    }
}