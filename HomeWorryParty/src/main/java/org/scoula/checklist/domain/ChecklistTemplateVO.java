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

    private long templateId;
    private String templateName;
    private String templateSaleType;
    private String templateStage;

}
