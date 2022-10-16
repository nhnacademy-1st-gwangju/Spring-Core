package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;

import java.io.IOException;
import java.util.Collection;

public interface StudentService {
    Collection<Student> getPassedStudents() throws IOException;

    Collection<Student> getStudentsOrderByScore() throws IOException;
}
