package org.scoula.documentAnalysis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class IllegalBuildingCheckVO {
    private final Long id;                // 내부 PK (AUTO_INCREMENT)
    private final String platPlc;         // 대지위치
    private final String sggCdNm;         // 시군구코드명
    private final String stdgCdNm;        // 법정동코드명
    private final String bdrgSn;          // 건축물대장일련번호
    private final String ldgrSeCdNm;      // 대장구분코드명
    private final String ldgrKindCdNm;    // 대장종류코드명
    private final String mnUsgCdNm;       // 주용도코드명
    private final String etcUsgCn;        // 기타용도내용
    private final Double bdcvrt;          // 건폐율
    private final Double gfa;             // 연면적
    private final Integer grndNofl;       // 지상층수
    private final Integer udgdNofl;       // 지하층수
    private final String prmsnYmd;        // 허가일자 (YYYYMMDD)
    private final String useAprvYmd;      // 사용승인일자 (YYYYMMDD)
    private final String rserDesignAplcnYn; // 내진설계적용여부(Y/N)
    private final String judgeResult;     // 불법여부(자동판정결과)
    private final String judgeReason;     // 불법 사유/근거
}