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

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DocumentAnalysisServiceImpl implements DocumentAnalysisService{
    private final DocumentAnalysisMapper documentAnalysisMapper;
    private final AgentMapper agentMapper;

    // 불법 건축물 데이터를 추가하기위한 클래스
    @Override
    public void insertIllegalBuildingData(IllegalBuildingCheckDTO illegalBuildingCheckDTO) {
        documentAnalysisMapper.insert(IllegalBuildingCheckDTO.toVO(illegalBuildingCheckDTO));
    }

    // front에서 받은 데이터를 통해 분석 후 결과를 반환함
    @Override
    public DocumentAnalysisResultDTO analysis(DocumentAnalysisDTO answerDTOList) {
        DocumentAnalysisResultDTO documentAnalysisResultDTO = new DocumentAnalysisResultDTO();

        checkCertified(answerDTOList.getRegisterCertifiedCount(), documentAnalysisResultDTO);
        checkHouseAddress(answerDTOList.getHouseAddress(), documentAnalysisResultDTO);
        checkDocumentAgent(answerDTOList.getDocumentAgentDTO(), documentAnalysisResultDTO);
        checkDocumentSthRisk(answerDTOList.getDocumentSthRiskDTO(), answerDTOList.getHouseAddress(), documentAnalysisResultDTO);

        documentAnalysisResultDTO.setResultData();
        return documentAnalysisResultDTO;
    }

    private void checkDocumentAgent(DocumentAgentDTO documentAgentDTO, DocumentAnalysisResultDTO documentAnalysisResultDTO) {
        // 여기서 뭐함???

    }

    // 등기부 등본 체크리스트를 확인해서 알려줌
    public void checkCertified(int registerCertifiedCount, DocumentAnalysisResultDTO documentAnalysisResultDTO){

        if(registerCertifiedCount < 7){
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (registerCertifiedCount * 4));
            documentAnalysisResultDTO.getDescriptionTitleList().add("등기부등본 서류 확인 미흡");
            documentAnalysisResultDTO.getDescriptionContentList().add(
                    "1. 명의 도용 및 권리관계 불일치<br></br>" +
                            "   실제 소유자가 아닌 제3자가 임대인(매도인) 행세를 하여 무효 계약이나 사기가 발생할 수 있음>" +
                            "<br></br>" +
                            "   공동소유, 상속 등 권리관계가 복잡한 경우 실제 계약 당사자가 아닌 사람이 거래에 나설 가능성" +
                            "<br></br>" +
                            "2. 근저당·가압류 등 숨겨진 권리 부담<br></br>" +
                            "   등기부에 근저당권, 가압류, 압류, 전세권 등 기타 권리가 설정되어 \n" +
                            "<br></br>" +
                            "   보증금 반환이나 소유권 이전에 심각한 차질 발생" +
                            "<br></br>" +
                            "   잔금지급 후 부동산이 경매로 넘어가는 경우, 전세금·보증금 손실" +
                            "<br></br>" +
                            "3. 이중매매·이중계약 가능성<br></br>" +
                            "   이미 매매(임대)가 진행 중이거나 타인에게 담보로 잡혀 있는데도 모른 채 거래" +
                            "<br></br>" +
                            "   같은 부동산을 여러 명에게 중복 계약할 수 있음" +
                            "<br></br>" +
                            "4. 허위매물 및 무권리자와의 거래<br></br>" +
                            "   실제 등기상 소유자와 계약 상대방의 일치 여부를 확인 못해" +
                            "<br></br>" +
                            "   존재하지 않는 매물(허위매물)로 인한 계약금·보증금 사기" +
                            "<br></br>" +
                            "5. 재산상 손해 및 법적 분쟁<br></br>" +
                            "   계약이 무효화되어도 이미 지급한 계약금, 중도금, 보증금을 회수하지 못하는 위험" +
                            "<br></br>" +
                            "   분쟁 발생 시 법적 소송에 휘말려 시간·비용 손실"
            );
        }
    }

    // 주소를 통해 해당 주소의 건물이 불법 건축물인지를 판단
    public void checkHouseAddress(String houseAddress, DocumentAnalysisResultDTO documentAnalysisResultDTO){
        if(houseAddress.isEmpty())return;
        houseAddress = "%" + houseAddress.substring(3);

        log.info(houseAddress);
        IllegalBuildingCheckVO illegalBuildingCheckVO = documentAnalysisMapper.findByAddress(houseAddress);
        //log.info(illegalBuildingCheckVO);


        if(illegalBuildingCheckVO != null){
            //log.info("불법 건축물 걸렸다!");
            String[] dangerPoint = illegalBuildingCheckVO.getJudgeReason().split("<br></br>");

            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (dangerPoint.length * 10));
            documentAnalysisResultDTO.getDescriptionTitleList().add("불법 건축물 위험");
            documentAnalysisResultDTO.getDescriptionContentList().add(illegalBuildingCheckVO.getJudgeReason());
        }

    }

    // 중개 사무소 주소로 해당 중개 사무소 정보를 가져옴
    @Override
    public List<AgentDetailVO> checkDocumentAgentByAgentDTO(DocumentAgentDTO documentAgentDTO){
        String houseAddress = "%" + documentAgentDTO.getAddress().substring(3);
        List<AgentDetailVO> agentDetailDTOS = agentMapper.findAgentByHouseAddress(houseAddress);
        log.info(agentDetailDTOS);
        return agentDetailDTOS;
    }

    // 매물 주소로 관리하는 중개 사무소 정보를 가져옴
    @Override
    public List<AgentDetailVO> checkDocumentAgentByAddress(String houseAddress){
        List<AgentDetailVO> agentDetailDTOS = agentMapper.findAgentByHouseAddress(houseAddress);
        log.info(agentDetailDTOS);

        return agentDetailDTOS;
    }

    // 중개사무소 정보나 매물 주소로 해당 매물의 시세를 비교해서 알려주는 로직
    public void checkDocumentSthRisk(DocumentSthRiskDTO documentSthRiskDTO, String houseAddress, DocumentAnalysisResultDTO documentAnalysisResultDTO){
        if(houseAddress.isEmpty() || documentSthRiskDTO == null)return;

        int percent = Math.toIntExact(calPercent(documentSthRiskDTO, houseAddress));
        log.info("percent : " + percent);
        if(percent > 5){
            documentAnalysisResultDTO.getDescriptionTitleList().add("시세보다 싼 가격");
            documentAnalysisResultDTO.getDescriptionContentList()
                    .add("시세보다 " + percent + "% 저렴하기에 거래시 불합리한 조건, 깡통 전세, 보증금 사기등의" +
                            "문제가 발생할 수 있기에 거래시 주의가 필요합니다." + "<br></br>");
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore() - (percent*2));
        }


    }

    // 전세, 월세, 매매 별로 해당 주소의 시세와 내가 사려는 매물의 가격을 비교해서 가격차이를 퍼센트로 반환
    private Long calPercent(DocumentSthRiskDTO documentSthRiskDTO, String address) {
        Long percent = 0L;
        String[] temp = address.split(" ");
        address = "%" + temp[1] + " " + temp[2] + "%";

        if(documentSthRiskDTO.getType().equals("전세")){
            Long price = documentAnalysisMapper.getWholeRent(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(price);
            if(price == null) return 0L;

            price *= 10000;
            Long differ = price - documentSthRiskDTO.getPrice();
            if(differ > 0){
                percent = differ * 100 / price;
            }
        }else if(documentSthRiskDTO.getType().equals("월세")){
            MonthlyRentVO averagePrice = documentAnalysisMapper.getMonthRent(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(averagePrice);
            if(averagePrice == null) return 0L;

            Long differDeposit = averagePrice.getPrice() - documentSthRiskDTO.getPrice();
            Long differMonthly = averagePrice.getMonthlyRent() - documentSthRiskDTO.getMonthlyPrice();
            if(differDeposit > 0)   percent = differDeposit * 100 / documentSthRiskDTO.getPrice();

            if(differMonthly > 0)  percent =
                    Math.max(differMonthly * 100 / averagePrice.getMonthlyRent(), percent);
        }else if(documentSthRiskDTO.getType().equals("매매")){
            Long price = documentAnalysisMapper.getBuy(address,
                    documentSthRiskDTO.getSize() - 6,
                    documentSthRiskDTO.getSize() + 6);
            //log.info(price);
            if(price == null) return 0L;

            Long differ = price - documentSthRiskDTO.getPrice();
            if(differ > 0){
                percent = differ * 100 / price;
            }
        }

        return percent;
    }


}
