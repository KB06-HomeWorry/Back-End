package org.scoula.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChecklistTemplateVO {
    // 체크리스트 문제의 유형별 이름과 단계를 가져오는 VO

    private long templateId;
    private String templateName;
    private String templateSaleType;
    private String templateStage;

}
