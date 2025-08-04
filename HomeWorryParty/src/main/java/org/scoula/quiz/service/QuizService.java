package org.scoula.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.quiz.domain.QuizVO;
import org.scoula.quiz.dto.QuizDTO;
import org.scoula.quiz.mapper.QuizMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizMapper mapper;

    public List<QuizDTO> getQuiz() {
        List<QuizVO> vo = mapper.getQuizList();
        List<QuizDTO> list = new ArrayList<>();

        if (vo != null && !vo.isEmpty()) {
            for (QuizVO q : vo) {
                list.add(new QuizDTO().of(q));
            }
        }

        return list;
    }
}
