package org.scoula.checklist.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.checklist.domain.ChecklistTemplateVO;

public interface ChecklistTemplateMapper {

    ChecklistTemplateVO findByTypeAndStage(@Param("type") String type, @Param("stage") String stage); // id 중복 체크시 사용


}
