package org.scoula.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.checklist.domain.ChecklistVO;
import org.scoula.security.account.domain.UserVO;
import org.scoula.user.dto.UserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistDTO {

    private long checklistId;
    private long templateId;
    private String content;
    private String effectiveness;
    private String necessity;
    private long orderNum;
    private long riskWeight;

    // VO → DTO
    public static ChecklistDTO of(ChecklistVO u) {
        return ChecklistDTO.builder()
                .checklistId(u.getChecklistId())
                .templateId(u.getTemplateId())
                .content(u.getContent())
                .effectiveness(u.getEffectiveness())
                .necessity(u.getNecessity())
                .orderNum(u.getOrderNum())
                .riskWeight(u.getRiskWeight())
                .build();
    }

    // DTO → VO
    public ChecklistVO toVO() {
        return ChecklistVO.builder()
                .checklistId(checklistId)
                .templateId(templateId)
                .content(content)
                .effectiveness(effectiveness)
                .necessity(necessity)
                .orderNum(orderNum)
                .riskWeight(riskWeight)
                .build();
    }

}
