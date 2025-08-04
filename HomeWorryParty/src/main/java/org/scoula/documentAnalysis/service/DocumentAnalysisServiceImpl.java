package org.scoula.documentAnalysis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.agent.mapper.AgentMapper;
import org.scoula.documentAnalysis.domain.IllegalBuildingCheckVO;
import org.scoula.documentAnalysis.domain.MonthlyRentVO;
import org.scoula.documentAnalysis.dto.*;
import org.scoula.documentAnalysis.mapper.DocumentAnalysisMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DocumentAnalysisServiceImpl implements DocumentAnalysisService {
    private final DocumentAnalysisMapper documentAnalysisMapper;
    private final AgentMapper agentMapper;

    // 불법 건축물 데이터를 추가하기 위한 클래스
    @Override
    public void insertIllegalBuildingData(IllegalBuildingCheckDTO illegalBuildingCheckDTO) {
        documentAnalysisMapper.insert(IllegalBuildingCheckDTO.toVO(illegalBuildingCheckDTO));
    }

    // front에서 받은 데이터를 통해 분석 후 결과를 반환함
    @Override
    public DocumentAnalysisResultDTO analysis(DocumentAnalysisDTO answerDTOList) {
        DocumentAnalysisResultDTO documentAnalysisResultDTO = new DocumentAnalysisResultDTO();


        checkHouseAddress(answerDTOList.getHouseAddress(), documentAnalysisResultDTO);
        checkDocumentAgent(answerDTOList.getDocumentAgentDTO(), documentAnalysisResultDTO);
        checkDocumentSthRisk(answerDTOList.getDocumentSthRiskDTO(), answerDTOList.getHouseAddress(), documentAnalysisResultDTO);
        checkCertified(answerDTOList.getRegisterCertifiedCount(), documentAnalysisResultDTO);

        documentAnalysisResultDTO.setResultData();
        return documentAnalysisResultDTO;
    }

    private void checkDocumentAgent(DocumentAgentDTO documentAgentDTO, DocumentAnalysisResultDTO documentAnalysisResultDTO) {
        if(documentAgentDTO.getAddress().isEmpty()) {
            documentAnalysisResultDTO.getDescriptionTitleList().add("확인되지 않은 중개인");
            documentAnalysisResultDTO.getDescriptionContentList()
                    .add("중개인의 정보를 확인하기 위한 정보가 없습니다. 다시 검사를 실행하거나 중개인에 대한 " +
                            "주의가 필요합니다.");
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore() - (15));
            return;
        }
        log.info("Check Document Agent : " + documentAgentDTO.getAddress());

        List<AgentDetailVO> agentDetailVOList = checkDocumentAgentByAgentDTO(documentAgentDTO);
        log.info(agentDetailVOList.toString());
        if (agentDetailVOList.isEmpty()) {
            documentAnalysisResultDTO.getDescriptionTitleList().add("확인되지 않은 중개인");
            documentAnalysisResultDTO.getDescriptionContentList()
                    .add("신뢰할 만한 거래 정보가 없는 중개인이므로, 계약 전 추가적인 확인이 필요합니다.");
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore() - (15));
        }
    }

    // 등기부 등본 체크리스트를 확인해서 알려줌
    public void checkCertified(int registerCertifiedCount, DocumentAnalysisResultDTO documentAnalysisResultDTO) {

        if (registerCertifiedCount < 7) {
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (registerCertifiedCount * 4));
            documentAnalysisResultDTO.getDescriptionTitleList().add("등기부등본 서류 확인 미흡");
            documentAnalysisResultDTO.getDescriptionContentList().add(
                    "<span style='color: black;'>1. 명의 도용 및 권리관계 불일치</span><br>" +
                            "실제 소유자가 아닌 사람이 거래에 나서 사기나 무효 계약이 발생할 수 있습니다.<br><br>" +

                            "<span style='color: black;'>2. 근저당·가압류 등 숨겨진 권리 부담</span><br>" +
                            "등기부상 권리로 인해 보증금 반환이나 소유권 이전에 문제가 생길 수 있습니다.<br><br>" +

                            "<span style='color: black;'>3. 이중매매·이중계약 가능성</span><br>" +
                            "동일 부동산에 대해 중복 계약이 이뤄질 수 있습니다.<br><br>" +

                            "<span style='color: black;'>4. 허위매물 및 무권리자와의 거래</span><br>" +
                            "실제 소유자가 아닌 사람과 계약해 보증금 사기를 당할 수 있습니다.<br><br>" +

                            "<span style='color: black;'>5. 재산상 손해 및 법적 분쟁</span><br>" +
                            "계약금 손실이나 법적 분쟁 등으로 금전적 피해를 입을 수 있습니다."
            );
        }
    }

    // 주소를 통해 해당 주소의 건물이 불법 건축물인지를 판단
    public void checkHouseAddress(String houseAddress, DocumentAnalysisResultDTO documentAnalysisResultDTO) {
        if (houseAddress.isEmpty()) return;
        houseAddress = "%" + houseAddress.substring(3);

        log.info(houseAddress);
        IllegalBuildingCheckVO illegalBuildingCheckVO = documentAnalysisMapper.findByAddress(houseAddress);
        //log.info(illegalBuildingCheckVO);

        if (illegalBuildingCheckVO != null) {
            //log.info("불법 건축물 걸렸다!");
            String[] dangerPoint = illegalBuildingCheckVO.getJudgeReason().split("<br>");

            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (dangerPoint.length * 10));
            documentAnalysisResultDTO.getDescriptionTitleList().add("불법 건축물 위험");
            documentAnalysisResultDTO.getDescriptionContentList().add(illegalBuildingCheckVO.getJudgeReason());
        }

    }

    // 중개 사무소 주소로 해당 중개 사무소 정보를 가져옴
    @Override
    public List<AgentDetailVO> checkDocumentAgentByAgentDTO(DocumentAgentDTO documentAgentDTO) {
        if (documentAgentDTO == null) return new ArrayList<>();

        String agentAddress = "%" + documentAgentDTO.getAddress().substring(5);
        List<AgentDetailVO> agentDetailDTOS = agentMapper.findAgentByAgentAddress(agentAddress);
        log.info(agentDetailDTOS);
      
        if(agentDetailDTOS.isEmpty()) return new ArrayList<>();

        return agentDetailDTOS;
    }

    // 매물 주소로 관리하는 중개 사무소 정보를 가져옴
    @Override
    public List<AgentDetailVO> checkDocumentAgentByAddress(String houseAddress) {
        if (houseAddress == null) return new ArrayList<>();

        List<AgentDetailVO> agentDetailDTOS = agentMapper.findAgentByHouseAddress(
                "%" + houseAddress.substring(3));
        log.info(agentDetailDTOS);

        return agentDetailDTOS;
    }

    // 중개사무소 정보나 매물 주소로 해당 매물의 시세를 비교해서 알려주는 로직
    public void checkDocumentSthRisk(DocumentSthRiskDTO documentSthRiskDTO, String houseAddress, DocumentAnalysisResultDTO documentAnalysisResultDTO) {
        if (houseAddress.isEmpty() || documentSthRiskDTO == null) return;

        int percent = Math.toIntExact(calPercent(documentSthRiskDTO, houseAddress));
        log.info("percent : " + percent);
        if (percent < 0) {
            documentAnalysisResultDTO.getDescriptionTitleList().add("데이터가 없는 유형의 매물");
            documentAnalysisResultDTO.getDescriptionContentList()
                    .add("해당 지역, 평수에 맞는 다른 매물이 탐색되지 않기 때문에 주의가 필요합니다.");
        }

        if (percent > 5) {
            documentAnalysisResultDTO.getDescriptionTitleList().add("시세보다 싼 가격");
            documentAnalysisResultDTO.getDescriptionContentList()
                    .add("시세보다 " + percent + "% 낮은 가격은 불합리한 계약 조건, 깡통 전세나 보증금 사기 등의 위험 가능성이 있으므로 거래 시 주의가 필요합니다.");
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore() - (percent * 2));
        }
    }

    // 전세, 월세, 매매 별로 해당 주소의 시세와 내가 사려는 매물의 가격을 비교해서 가격차이를 퍼센트로 반환
    private Long calPercent(DocumentSthRiskDTO documentSthRiskDTO, String address) {
        Long percent = 0L;
        String[] temp = address.split(" ");
        address = "%" + temp[1] + " " + temp[2] + "%";

        if (documentSthRiskDTO.getType().equals("전세")) {
            Long price = documentAnalysisMapper.getWholeRent(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(price);
            if (price == null) return -1L;

            price *= 10000;
            Long differ = price - documentSthRiskDTO.getPrice();
            if (differ > 0) {
                percent = differ * 100 / price;
            }
        } else if (documentSthRiskDTO.getType().equals("월세")) {
            MonthlyRentVO averagePrice = documentAnalysisMapper.getMonthRent(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(averagePrice);
            if (averagePrice == null) return -1L;

            Long differDeposit = averagePrice.getPrice() - documentSthRiskDTO.getPrice();
            Long differMonthly = averagePrice.getMonthlyRent() - documentSthRiskDTO.getMonthlyPrice();
            if (differDeposit > 0) percent = differDeposit * 100 / documentSthRiskDTO.getPrice();

            if (differMonthly > 0) percent =
                    Math.max(differMonthly * 100 / averagePrice.getMonthlyRent(), percent);
        } else if (documentSthRiskDTO.getType().equals("매매")) {
            Long price = documentAnalysisMapper.getBuy(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(price);
            if (price == null) return -1L;

            Long differ = price - documentSthRiskDTO.getPrice();
            if (differ > 0) {
                percent = differ * 100 / price;
            }
        }

        return percent;
    }

}