package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;

import java.io.IOException;
import java.util.Collection;

public interface Students {
    void load() throws IOException;

    Collection<Student> findAll() throws IOException;

    void merge(Collection<Score> scores);
}
