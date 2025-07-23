package org.scoula.dangerResult.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangerAnswerVO {

    private Long dangerAnswerId;         // answer_id 컬럼과 매핑
    private int answer;     // answer 컬럼과 매핑
    private int riskWeight; // risk_weight 컬럼과 매핑
    private String effectiveness;

}
