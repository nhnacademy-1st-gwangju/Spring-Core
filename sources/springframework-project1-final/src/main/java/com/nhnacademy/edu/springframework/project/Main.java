package com.nhnacademy.edu.springframework.project;

import com.nhnacademy.edu.springframework.project.repository.Score;
import com.nhnacademy.edu.springframework.project.repository.StudentService;
import com.nhnacademy.edu.springframework.project.service.DataLoadService;
import com.nhnacademy.edu.springframework.project.service.GradeQueryService;
import com.nhnacademy.edu.springframework.project.service.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.List;

public class Main {

    // TODO 9 - 성공적으로 실행되어야 합니다.
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nhnacademy.edu.springframework.project.config");
        DataLoadService dataLoadService = context.getBean(DataLoadService.class);
        dataLoadService.loadAndMerge();

        StudentService studentService = context.getBean(StudentService.class);
        Collection<Student> passedStudents = studentService.getPassedStudents();
        System.out.println(passedStudents);

        Collection<Student> orderedStudents = studentService.getStudentsOrderByScore();
        System.out.println(orderedStudents);


        GradeQueryService queryService = context.getBean(GradeQueryService.class);
        Score byStudentSeq = queryService.getScoreByStudentSeq(5);
        System.out.println(byStudentSeq);

        List<Score> scoreList = queryService.getScoreByStudentName("A");
        System.out.println(scoreList);
    }
}
