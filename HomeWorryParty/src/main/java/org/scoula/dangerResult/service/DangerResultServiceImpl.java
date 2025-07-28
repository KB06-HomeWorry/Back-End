package org.scoula.dangerResult.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.dangerResult.domain.DangerAnswerVO;
import org.scoula.dangerResult.domain.DangerResultVO;
import org.scoula.dangerResult.mapper.DangerResultMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DangerResultServiceImpl implements DangerResultService {

    private final DangerResultMapper dangerResultMapper;
    private final ChecklistService  checklistService;

    // 사용자의 정답을 불러오고 체크리스트의 정답과 비교해서 점수를 매기는 로직
    @Override
    public DangerResultVO analysisDangerResult(Long templateId, Long userId) {
        List<DangerAnswerVO> answerDTOList = dangerResultMapper.getAnswerList(templateId, userId);
        List<ChecklistDTO> checklistDTOList = checklistService.getChecklist(templateId);

        log.info("답안 list 개수 = " + answerDTOList.size());
        List<String> descriptionTitleList = new ArrayList<>();
        List<String> descriptionContentList = new ArrayList<>();

        int score = 100;
        if(!answerDTOList.isEmpty()){
            for (int i = 0; i < answerDTOList.size(); i++) {
                if(answerDTOList.get(i).getAnswer() == 1){
                    score -= answerDTOList.get(i).getRiskWeight();
                }else{
                    descriptionTitleList.add(checklistDTOList.get(i).getNecessity_title());
                    descriptionContentList.add(checklistDTOList.get(i).getNecessity_content());
                }
            }
        }
        log.info("사용자의 점수 = " + score);

        DangerResultVO dangerResultVO = getMessageList(score, templateId);
        dangerResultVO.setDescriptionContentList(descriptionContentList);
        dangerResultVO.setDescriptionTitleList(descriptionTitleList);

        return dangerResultVO;
    }

    // 사용자의 점수에 따른 결과를 보여주기 위해 결과 페이지 정보를 불러오는 로직
    @Override
    public DangerResultVO getMessageList(int score, Long templateId) {
        List<DangerResultVO> dangerResultVOList = dangerResultMapper.getMessageList(templateId);
        DangerResultVO findDangerResultVO = new DangerResultVO();


        for (DangerResultVO dangerResultVO : dangerResultVOList) {
            if(score >= dangerResultVO.getMinScore() && score <= dangerResultVO.getMaxScore()){
                findDangerResultVO.copy(dangerResultVO);
            }
            System.out.println(dangerResultVO);
        }

        return findDangerResultVO;
    }
}
