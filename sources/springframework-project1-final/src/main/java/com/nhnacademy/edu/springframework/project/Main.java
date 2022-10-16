package com.nhnacademy.edu.springframework.project;

import com.nhnacademy.edu.springframework.project.repository.Score;
import com.nhnacademy.edu.springframework.project.service.CsvDataLoadService;
import com.nhnacademy.edu.springframework.project.service.DefaultGradeQueryService;
import com.nhnacademy.edu.springframework.project.service.DefaultStudentService;
import com.nhnacademy.edu.springframework.project.service.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.List;

public class Main {

    // TODO 9 - 성공적으로 실행되어야 합니다.
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nhnacademy.edu.springframework.project.config");
        CsvDataLoadService csvDataLoadService = context.getBean("csvDataLoadService", CsvDataLoadService.class);
        csvDataLoadService.loadAndMerge();

        DefaultStudentService defaultStudentService = context.getBean("defaultStudentService", DefaultStudentService.class);
        Collection<Student> passedStudents = defaultStudentService.getPassedStudents();
        System.out.println(passedStudents);

        Collection<Student> orderedStudents = defaultStudentService.getStudentsOrderByScore();
        System.out.println(orderedStudents);


        DefaultGradeQueryService queryService = context.getBean("defaultGradeQueryService", DefaultGradeQueryService.class);
        Score byStudentSeq = queryService.getScoreByStudentSeq(5);
        System.out.println(byStudentSeq);

        List<Score> scoreList = queryService.getScoreByStudentName("A");
        System.out.println(scoreList);
    }
}
