package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class StudentsTest {

    CsvStudents students;
    Collection<Student> studentList;
    CsvScores scores;

    @BeforeEach
    void init() {
        students = (CsvStudents) CsvStudents.getInstance();
        scores = (CsvScores) CsvScores.getInstance();
    }


    @Test
    @DisplayName("test/resources/data 경로에 있는 test data를 읽어온다.")
    void load() throws Exception {
        //given
        students.setPath("src/test/resources/data/student.csv");

        //when
        students.load();
        studentList = students.findAll();

        //then
        assertThat(studentList).hasSize(4);
    }

    @Test
    @DisplayName("load 된 이후, 리스트는 empty가 아니다.")
    void findAll() throws Exception {
        //given
        students.setPath("src/test/resources/data/student.csv");
        students.load();

        //when
        studentList = students.findAll();

        //then
        assertThat(studentList).isNotEmpty();
    }

    @Test
    @DisplayName("merge 이후 리스트 내 각 student들의 score는 null이 아니다.")
    void merge() throws Exception {
        //given
        students.setPath("src/test/resources/data/student.csv");
        CsvScores instance = (CsvScores) CsvScores.getInstance();
        instance.setPath("src/test/resources/data/score.csv");
        List<Score> scores = instance.findAll();

        students.load();

        //when
        students.merge(scores);
        studentList = students.findAll();

        //then
        Student student = studentList.stream().filter(o -> o.getScore() != null)
                .findAny()
                .orElseThrow(NoSuchElementException::new);
        Assertions.assertThat(student.getScore()).isNotNull();
    }

    @AfterEach
    void tearDown() throws Exception {
        studentList.clear();
        scores.findAll().clear();
    }
}