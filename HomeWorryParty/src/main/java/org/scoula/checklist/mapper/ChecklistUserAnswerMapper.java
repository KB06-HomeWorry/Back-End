package org.scoula.checklist.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.checklist.domain.ChecklistUserAnswerVO;

import java.util.List;

@Mapper
public interface ChecklistUserAnswerMapper {
    List<ChecklistUserAnswerVO> findByChecklistIdAndUserId(
            @Param("checklistId") long checklistId,
            @Param("userId") long userId
    );

    void insertAnswerList(@Param("checklistId") Long checklistId,
                          @Param("questionId") long questionId, @Param("userId") Long userId);

    void insertCheckList(@Param("userId") Long userId, @Param("templateId") Long templateId);

    Long getCheckListId(@Param("userId") Long userId, @Param("templateId") Long templateId);
}