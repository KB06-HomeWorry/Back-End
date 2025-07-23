package org.scoula.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Office {

    @JsonProperty("SYS_REG_NO")
    private Long sysNo; // 시스템 등록번호

    @JsonProperty("CGG_CD")
    private String gu; // 구

    @JsonProperty("LGL_DONG_NM")
    private String dong; // 동

    @JsonProperty("ADDR")
    private String address; // 주소

    @JsonProperty("REST_BRKR_INFO")
    private String number; // 중개업등록번호

    @JsonProperty("MDT_BSNS_NM")
    private String agentName; // 중개사이름

    @JsonProperty("BZMN_CONM")
    private String officeName; // 중개업소이름

    @JsonProperty("TELNO")
    private String phone; // 전화번호

    @Override
    public String toString() {
        return "Product{" +
                "SYS_REG_NO=" + sysNo +
                ", cggCd='" + gu + '\'' +
                ", lglDongNm='" + dong + '\'' +
                ", addr='" + address + '\'' +
                ", restBrkrInfo='" + number + '\'' +
                ", mdtBsnsNm='" + agentName + '\'' +
                ", bzmnConm='" + officeName + '\'' +
                ", telno='" + phone + '\'' +
                '}';
    }
}
