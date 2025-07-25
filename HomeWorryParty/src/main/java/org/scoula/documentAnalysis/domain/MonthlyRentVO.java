package org.scoula.documentAnalysis.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonthlyRentVO {
    private Long monthlyFee = 0L;
    private Long deposit = 0L;

}
