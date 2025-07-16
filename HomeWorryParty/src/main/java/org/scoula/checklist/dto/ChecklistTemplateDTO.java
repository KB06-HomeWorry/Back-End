package org.scoula.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.checklist.domain.ChecklistTemplateVO;
import org.scoula.checklist.domain.ChecklistVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistTemplateDTO {


    private long templateId;
    private String templateName;
    private String templateSaleType;
    private String templateStage;


    // VO → DTO
    public static ChecklistTemplateDTO of(ChecklistTemplateVO u) {
        return ChecklistTemplateDTO.builder()
                .templateId(u.getTemplateId())
                .templateName(u.getTemplateName())
                .templateSaleType(u.getTemplateSaleType())
                .templateStage(u.getTemplateStage())
                .build();
    }

    // DTO → VO
    public ChecklistTemplateVO toVO() {
        return ChecklistTemplateVO.builder()
                .templateId(templateId)
                .templateName(templateName)
                .templateSaleType(templateSaleType)
                .templateStage(templateStage)
                .build();
    }


}
