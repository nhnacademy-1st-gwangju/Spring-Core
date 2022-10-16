package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import com.nhnacademy.edu.springframework.project.service.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class StudentsTest {

    @Autowired
    CsvStudents students;
    @Autowired
    CsvScores scores;
    Collection<Student> studentList;

    @BeforeEach
    void init() {
        studentList = new ArrayList<>();
    }

    @Test
    @DisplayName("파일을 읽어오면 내부 collection에 데이터가 정상적으로 쌓인다.")
    void load() throws Exception {
        //given
        int before = studentList.size();

        //when
        students.load();

        //then
        studentList = students.findAll();
        int after = studentList.size();
        assertThat(after).isNotEqualTo(before);
    }

    @Test
    @DisplayName("읽어 온 데이터 전부를 정상적으로 반환한다.")
    void findAll() throws Exception {
        //when
        studentList = students.findAll();

        //then
        assertThat(studentList).isNotEmpty();
    }

    @Test
    @DisplayName("merge 이후 리스트 내 각 student들의 score는 null이 아니다.")
    void merge() throws Exception {
        //given
        List<Score> scoreList = scores.findAll();

        //when
        students.merge(scoreList);
        studentList = students.findAll();

        //then
        Student student = studentList.stream().filter(o -> o.getScore() != null)
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        assertThat(student.getScore()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        studentList.clear();
    }
}