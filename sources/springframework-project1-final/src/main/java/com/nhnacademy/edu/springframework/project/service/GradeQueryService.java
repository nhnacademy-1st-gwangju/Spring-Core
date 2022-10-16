package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.Score;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface GradeQueryService {
    List<Score> getScoreByStudentName(String name) throws IOException;
    Score getScoreByStudentSeq(int seq) throws IOException;
}
