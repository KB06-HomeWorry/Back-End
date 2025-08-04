package org.scoula.dangerResult.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangerResultVO {
    // 위험도 카드를 위한 내용을 가져오는 VO
    private String grade;
    private long templateId;
    private float minScore;
    private float maxScore;
    private String message;
    private List<String> descriptionTitleList;
    private List<String> descriptionContentList;
    private String imageUrl;

    public void copy(DangerResultVO dangerResultVO) {
        this.grade = dangerResultVO.getGrade();
        this.templateId = dangerResultVO.getTemplateId();
        this.minScore = dangerResultVO.getMinScore();
        this.maxScore = dangerResultVO.getMaxScore();
        this.descriptionTitleList = dangerResultVO.getDescriptionTitleList();
        this.descriptionContentList = dangerResultVO.getDescriptionContentList();
        this.message = dangerResultVO.getMessage();
        this.imageUrl = dangerResultVO.getImageUrl();
    }
}
