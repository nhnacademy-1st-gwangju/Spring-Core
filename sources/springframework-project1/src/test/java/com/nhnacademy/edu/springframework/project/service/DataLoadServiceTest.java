package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class DataLoadServiceTest {

    CsvDataLoadService csvDataLoadService;
    CsvStudents students;
    Collection<Student> all;

    @BeforeEach
    void init() throws Exception {
        csvDataLoadService = new CsvDataLoadService();
        students = (CsvStudents) CsvStudents.getInstance();
        all = students.findAll();
        all.clear();
    }

    @Test
    @DisplayName("loadAndMerge 호출 시 정상적으로 데이터가 적재된다.")
    void loadAndMerge() throws Exception {
        //given
        int before = all.size();

        //when
        csvDataLoadService.loadAndMerge();

        //then
        int after = all.size();
        assertThat(after).isNotEqualTo(before);
    }
}