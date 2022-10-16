package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public interface StudentService {
    Collection<Student> getPassedStudents() throws IOException;

    Collection<Student> getStudentsOrderByScore() throws IOException;
}
