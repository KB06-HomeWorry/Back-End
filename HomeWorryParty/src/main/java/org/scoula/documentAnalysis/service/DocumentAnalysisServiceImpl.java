package org.scoula.documentAnalysis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.documentAnalysis.domain.IllegalBuildingCheckVO;
import org.scoula.documentAnalysis.dto.*;
import org.scoula.documentAnalysis.mapper.DocumentAnalysisMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DocumentAnalysisServiceImpl implements DocumentAnalysisService{
    private final DocumentAnalysisMapper documentAnalysisMapper;

    @Override
    public void insertIllegalBuildingData(IllegalBuildingCheckDTO illegalBuildingCheckDTO) {
        documentAnalysisMapper.insert(IllegalBuildingCheckDTO.toVO(illegalBuildingCheckDTO));
    }

    @Override
    public DocumentAnalysisResultDTO analysis(DocumentAnalysisDTO answerDTOList) {
        DocumentAnalysisResultDTO documentAnalysisResultDTO = new DocumentAnalysisResultDTO();

        checkCertified(answerDTOList.getRegisterCertifiedCount(), documentAnalysisResultDTO);
        checkHouseAddress(answerDTOList.getHouseAddress(), documentAnalysisResultDTO);
        checkDocumentAgent(answerDTOList.getDocumentAgentDTO(), documentAnalysisResultDTO);
        checkDocumentSthRisk(answerDTOList.getDocumentSthRiskDTO(), documentAnalysisResultDTO);

        documentAnalysisResultDTO.setResultData();
        return documentAnalysisResultDTO;
    }

    public void checkCertified(int registerCertifiedCount, DocumentAnalysisResultDTO documentAnalysisResultDTO){

        if(registerCertifiedCount < 7){
            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (registerCertifiedCount * 4));
            documentAnalysisResultDTO.getDescriptionTitleList().add("등기부등본 서류 확인 미흡");
            documentAnalysisResultDTO.getDescriptionContentList().add(
                    "1. 명의 도용 및 권리관계 불일치<br></br>" +
                            "실제 소유자가 아닌 제3자가 임대인(매도인) 행세를 하여 무효 계약이나 사기가 발생할 수 있음>" +
                            "<br></br>" +
                            "공동소유, 상속 등 권리관계가 복잡한 경우 실제 계약 당사자가 아닌 사람이 거래에 나설 가능성" +
                            "<br></br>" +
                            "2. 근저당·가압류 등 숨겨진 권리 부담<br></br>" +
                            "등기부에 근저당권, 가압류, 압류, 전세권 등 기타 권리가 설정되어 \n" +
                            "<br></br>" +
                            "보증금 반환이나 소유권 이전에 심각한 차질 발생" +
                            "<br></br>" +
                            "잔금지급 후 부동산이 경매로 넘어가는 경우, 전세금·보증금 손실" +
                            "<br></br>" +
                            "3. 이중매매·이중계약 가능성<br></br>" +
                            "이미 매매(임대)가 진행 중이거나 타인에게 담보로 잡혀 있는데도 모른 채 거래" +
                            "<br></br>" +
                            "같은 부동산을 여러 명에게 중복 계약할 수 있음" +
                            "<br></br>" +
                            "4. 허위매물 및 무권리자와의 거래<br></br>" +
                            "실제 등기상 소유자와 계약 상대방의 일치 여부를 확인 못해" +
                            "<br></br>" +
                            "존재하지 않는 매물(허위매물)로 인한 계약금·보증금 사기" +
                            "<br></br>" +
                            "5. 재산상 손해 및 법적 분쟁<br></br>" +
                            "계약이 무효화되어도 이미 지급한 계약금, 중도금, 보증금을 회수하지 못하는 위험" +
                            "<br></br>" +
                            "분쟁 발생 시 법적 소송에 휘말려 시간·비용 손실"
            );
        }
    }
    public void checkHouseAddress(String houseAddress, DocumentAnalysisResultDTO documentAnalysisResultDTO){
        IllegalBuildingCheckVO illegalBuildingCheckVO = documentAnalysisMapper.findByAddress(houseAddress);

        if(illegalBuildingCheckVO != null){
            String[] dangerPoint = illegalBuildingCheckVO.getJudgeReason().split("<br></br>");

            documentAnalysisResultDTO.setScore(documentAnalysisResultDTO.getScore()
                    - (dangerPoint.length * 10));
            documentAnalysisResultDTO.getDescriptionTitleList().add("불법 건축물 위험");
            documentAnalysisResultDTO.getDescriptionContentList().add(illegalBuildingCheckVO.getJudgeReason());
        }

    }
    public void checkDocumentAgent(DocumentAgentDTO documentAgentDTO, DocumentAnalysisResultDTO documentAnalysisResultDTO){

    }
    public void checkDocumentSthRisk(DocumentSthRiskDTO documentSthRiskDTO, DocumentAnalysisResultDTO documentAnalysisResultDTO){

    }


}
