package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class ScoresTest {

    @Autowired
    CsvScores scores;
    List<Score> scoreList;

    @BeforeEach
    void init() {
        scoreList = new ArrayList<>();
    }

    @Test
    @DisplayName("파일을 읽어오면 내부 collection에 데이터가 정상적으로 쌓인다.")
    void load() throws Exception {
        //given
        int before = scoreList.size();

        //when
        scores.load();

        //then
        scoreList = scores.findAll();
        int after = scoreList.size();
        assertThat(after).isNotEqualTo(before);
    }

    @Test
    @DisplayName("읽어 온 데이터 전부를 정상적으로 반환한다.")
    void findAll() throws Exception {
        //when
        scoreList = scores.findAll();

        //then
        assertThat(scoreList).isNotEmpty();
    }

    @AfterEach
    void tearDown() {
        scoreList.clear();
    }
}