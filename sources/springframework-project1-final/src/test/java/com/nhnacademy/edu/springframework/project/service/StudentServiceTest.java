package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class StudentServiceTest {

    @Autowired
    DefaultStudentService defaultStudentService;

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