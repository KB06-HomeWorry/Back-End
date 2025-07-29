package org.scoula.documentAnalysis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonthlyRentVO {
    // 월세의 평균값을 DB에서 꺼내기 위한 VO
    private Long price;
    private Long monthlyRent;
}
