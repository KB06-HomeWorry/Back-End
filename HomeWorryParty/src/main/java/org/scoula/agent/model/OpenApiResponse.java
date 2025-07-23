package org.scoula.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenApiResponse {

    @JsonProperty("landBizInfo")
    private LandBizInfo landBizInfo;

    @Override
    public String toString() {
        return "OpenApiResponse{" +
                "landBizInfo=" + landBizInfo +
                '}';
    }

    @Data
    public static class LandBizInfo {
        @JsonProperty("list_total_count")
        private Integer listTotalCount;

        @JsonProperty("RESULT")
        private Result result;

        @JsonProperty("row")
        private List<Office> row;

        @Override
        public String toString() {
            return "LandBizInfo{" +
                    "listTotalCount=" + listTotalCount +
                    ", result=" + result +
                    ", row=" + row +
                    '}';
        }
    }

    @Data
    public static class Result {
        @JsonProperty("CODE")
        private String code;

        @JsonProperty("MESSAGE")
        private String message;

        @Override
        public String toString() {
            return "Result{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
