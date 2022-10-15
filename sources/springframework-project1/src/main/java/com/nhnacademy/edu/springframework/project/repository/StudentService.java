package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;

import java.util.Collection;

public interface StudentService {
    Collection<Student> getPassedStudents() throws Exception;

    Collection<Student> getStudentsOrderByScore() throws Exception;
}
