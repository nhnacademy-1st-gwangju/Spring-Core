package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import com.nhnacademy.edu.springframework.project.repository.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class GradeQueryServiceTest {

    @Autowired
    DefaultGradeQueryService defaultGradeQueryService;

    @Test
    @DisplayName("해당 이름의 학생이 있는 경우 정상적으로 동작한다.")
    void getScoreByStudentName() throws Exception {
        //given
        String name = "A";

        //when
        List<Score> byStudentName = defaultGradeQueryService.getScoreByStudentName(name);

        //then
        assertThat(byStudentName).isNotEmpty();
    }

    @Test
    @DisplayName("해당 이름의 학생이 없는 경우 실패한다.")
    void getScoreByStudentNameFail() throws Exception {
        //given
        String name = "없는 사람";

        //when, then
        assertThatThrownBy(() -> defaultGradeQueryService.getScoreByStudentName(name))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("해당 학번의 학생이 있는 경우 정상적으로 동작한다.")
    void getScoreByStudentSeq() throws Exception {
        //given
        int number = 3;

        //when
        Score byStudentSeq = defaultGradeQueryService.getScoreByStudentSeq(number);

        //then
        assertThat(byStudentSeq).isNotNull();
    }

    @Test
    @DisplayName("해당 학번의 학생이 없는 경우 실패한다.")
    void getScoreByStudentSeqFail() throws Exception {
        //given
        int notNumber = 1000000;

        //when, then
        assertThatThrownBy(() -> defaultGradeQueryService.getScoreByStudentSeq(notNumber))
                .isInstanceOf(NoSuchElementException.class);
    }
}