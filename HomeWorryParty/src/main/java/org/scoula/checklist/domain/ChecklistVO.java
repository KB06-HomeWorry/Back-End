package org.scoula.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChecklistVO {

    private long checklistId;
    private long templateId;
    private String content;
    private String effectiveness;
    private long orderNum;
    private long riskWeight;

}
