package org.scoula.documentAnalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.documentAnalysis.domain.IllegalBuildingCheckVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IllegalBuildingCheckDTO {
    // 불법 건축물 판단 결과를 저장하기 위한 DTO
    private Long id;                // 내부 PK (AUTO_INCREMENT)
    @JsonProperty("PLAT_PLC")
    private String platPlc; // 대지위치

    @JsonProperty("SGG_CD_NM")
    private String sggCdNm; // 시군구코드명

    @JsonProperty("STDG_CD_NM")
    private String stdgCdNm; // 법정동코드명

    @JsonProperty("BDRG_SN")
    private String bdrgSn; // 건축물대장일련번호

    @JsonProperty("LDGR_SE_CD_NM")
    private String ldgrSeCdNm; // 대장구분코드명

    @JsonProperty("LDGR_KIND_CD_NM")
    private String ldgrKindCdNm; // 대장종류코드명

    @JsonProperty("MN_USG_CD_NM")
    private String mnUsgCdNm; // 주용도코드명

    @JsonProperty("ETC_USG_CN")
    private String etcUsgCn; // 기타용도내용

    @JsonProperty("BDCVRT")
    private Double bdcvrt; // 건폐율

    @JsonProperty("GFA")
    private Double gfa; // 연면적

    @JsonProperty("GRND_NOFL")
    private Integer grndNofl; // 지상층수

    @JsonProperty("UDGD_NOFL")
    private Integer udgdNofl; // 지하층수

    @JsonProperty("PRMSN_YMD")
    private String prmsnYmd; // 허가일자 (YYYYMMDD)

    @JsonProperty("USE_APRV_YMD")
    private String useAprvYmd; // 사용승인일자 (YYYYMMDD)

    @JsonProperty("RSER_DESIGN_APLCN_YN")
    private String rserDesignAplcnYn; // 내진설계적용여부(Y/N)

    @JsonProperty("ROOF_CD_NM")
    private String roofCdNm;      // 지붕코드명

    @JsonProperty("ETC_ROOF_NM")
    private String etcRoofNm;     // 기타지붕명

    private String judgeResult; // 불법여부(자동판정결과)
    private String judgeReason; // 불법 사유/근거

    private String remark; // 추가 확인/비고 (파싱/비즈니스 로직에서

    // VO 변환
    public static IllegalBuildingCheckVO toVO(IllegalBuildingCheckDTO dto) {
        return new IllegalBuildingCheckVO(
                dto.getId(),
                dto.getPlatPlc(),
                dto.getSggCdNm(),
                dto.getStdgCdNm(),
                dto.getBdrgSn(),
                dto.getLdgrSeCdNm(),
                dto.getLdgrKindCdNm(),
                dto.getMnUsgCdNm(),
                dto.getEtcUsgCn(),
                dto.getBdcvrt(),
                dto.getGfa(),
                dto.getGrndNofl(),
                dto.getUdgdNofl(),
                dto.getPrmsnYmd(),
                dto.getUseAprvYmd(),
                dto.getRserDesignAplcnYn(),
                dto.getRoofCdNm(),
                dto.getEtcRoofNm(),
                dto.getJudgeResult(),
                dto.getJudgeReason()
        );
    }
}