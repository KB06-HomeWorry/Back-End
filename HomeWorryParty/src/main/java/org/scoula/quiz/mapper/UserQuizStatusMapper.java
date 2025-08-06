package org.scoula.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scoula.quiz.domain.UserQuizStatusVO;

import java.util.List;

@Mapper
public interface UserQuizStatusMapper {
    void insertOrUpdateUserQuizStatus(UserQuizStatusVO status);

    List<UserQuizStatusVO> selectAllStatusByUserId(Long userId);

    List<UserQuizStatusVO> selectCompletedQuizzesByUserId(Long userId);
}