package org.scoula.documentAnalysis.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IllegalBuildingCheckVO {
    private Long id; // 내부 PK (AUTO_INCREMENT)
    private String platPlc;         // 대지위치
    private String sggCdNm;         // 시군구코드명
    private String stdgCdNm;        // 법정동코드명
    private String bdrgSn;          // 건축물대장일련번호
    private String ldgrSeCdNm;      // 대장구분코드명
    private String ldgrKindCdNm;    // 대장종류코드명
    private String mnUsgCdNm;       // 주용도코드명
    private String etcUsgCn;        // 기타용도내용
    private Double bdcvrt;          // 건폐율
    private Double gfa;             // 연면적
    private Integer grndNofl;       // 지상층수
    private Integer udgdNofl;       // 지하층수
    private String prmsnYmd;        // 허가일자 (YYYYMMDD)
    private String useAprvYmd;      // 사용승인일자 (YYYYMMDD)
    private String rserDesignAplcnYn; // 내진설계적용여부(Y/N)
    private String roofCdNm;      // 지붕코드명
    private String etcRoofNm;     // 기타지붕명
    private String judgeResult;     // 불법여부(자동판정결과)
    private String judgeReason;     // 불법 사유/근거



}