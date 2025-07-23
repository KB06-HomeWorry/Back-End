package org.scoula.dangerResult.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.dangerResult.domain.DangerAnswerVO;
import org.scoula.dangerResult.domain.DangerResultVO;

import java.util.List;

public interface DangerResultMapper {
    List<DangerAnswerVO> getAnswerList(
            @Param("templateId") Long templateId,
            @Param("userId") Long userId);

    List<DangerResultVO> getMessageList(@Param("templateId") Long templateId);
}
