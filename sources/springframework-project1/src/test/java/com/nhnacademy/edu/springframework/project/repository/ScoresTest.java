package com.nhnacademy.edu.springframework.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScoresTest {

    CsvScores scores;
    List<Score> scoreList;

    @BeforeEach
    void init() {
        scores = (CsvScores) CsvScores.getInstance();
        scoreList = new ArrayList<>();
    }

    @Test
    @DisplayName("test/resources/data 경로에 있는 test data 읽어오기 성공")
    void loadSuccess() throws Exception {
        //given
        scores.setPath("src/test/resources/data/score.csv");

        //when
        scores.load();

        //then
        scoreList = scores.findAll();
        assertThat(scoreList).hasSize(3);
    }

    @Test
    @DisplayName("파일을 찾을 수 없는 경우")
    void loadFailed() throws Exception {
        //given
        scores.setPath("src/test/resources/data/fail.csv");

        //when, then
        assertThatThrownBy(scores::load).isInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("읽어 온 데이터 전부를 반환한다.")
    void findAllSuccess() throws Exception {
        //given
        scores.setPath("src/test/resources/data/score.csv");

        //when
        scoreList = scores.findAll();

        //then
        assertThat(scoreList).isNotEmpty();
    }

    @Test
    @DisplayName("읽어 온 데이터가 없는 경우")
    void findAllFailed() throws Exception {
        //given
        scores.setPath("src/test/resources/data/nulltest.csv");

        //when
        scoreList = scores.findAll();

        //then
        assertThat(scoreList).isEmpty();
    }

    @AfterEach
    void tearDown() {
        scoreList.clear();
    }
}