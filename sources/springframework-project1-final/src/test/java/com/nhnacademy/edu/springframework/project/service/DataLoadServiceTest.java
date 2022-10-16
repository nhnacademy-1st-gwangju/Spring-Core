package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class DataLoadServiceTest {

    @Autowired
    CsvDataLoadService csvDataLoadService;
    @Autowired
    CsvStudents students;

    Collection<Student> studentList;

    @BeforeEach
    void init() throws Exception {
        studentList = students.findAll();
        studentList.clear();
    }

    @Test
    @DisplayName("loadAndMerge 호출 시 정상적으로 데이터가 적재된다.")
    void loadAndMerge() throws Exception {
        //given
        int before = studentList.size();

        //when
        csvDataLoadService.loadAndMerge();

        //then
        int after = studentList.size();
        assertThat(after).isNotEqualTo(before);
    }
}