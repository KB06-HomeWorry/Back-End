package org.scoula.quiz.service;

import lombok.RequiredArgsConstructor;
import org.scoula.quiz.domain.UserQuizStatusVO;
import org.scoula.quiz.mapper.UserQuizStatusMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuizStatusServiceImpl implements UserQuizStatusService {

    private final UserQuizStatusMapper userQuizStatusMapper;

    @Override
    public void submitQuiz(Long userId, Long quizId, String answer, boolean isCorrect) {
        UserQuizStatusVO status = UserQuizStatusVO.builder().userId(userId).quizId(quizId).isSolved(true).isCorrect(isCorrect).answer(answer).solvedAt(LocalDateTime.now()).build();
        userQuizStatusMapper.insertOrUpdateUserQuizStatus(status);
    }

    @Override
    public List<UserQuizStatusVO> getUserStatus(Long userId) {
        return userQuizStatusMapper.selectAllStatusByUserId(userId);
    }

    @Override
    public UserQuizStatusVO getQuizStatus(Long userId, Long quizId) {
        return userQuizStatusMapper.selectUserQuizStatus(userId, quizId);
    }
}