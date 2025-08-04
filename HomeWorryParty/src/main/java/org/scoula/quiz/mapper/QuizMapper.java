package org.scoula.quiz.mapper;

import org.scoula.quiz.domain.QuizVO;

import java.util.List;

public interface QuizMapper {
    List<QuizVO> getQuizList(); // 퀴즈 목록 조회
}
