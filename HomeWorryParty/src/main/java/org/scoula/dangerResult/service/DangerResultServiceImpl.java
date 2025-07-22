package org.scoula.dangerResult.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.dangerResult.domain.DangerAnswerVO;
import org.scoula.dangerResult.domain.DangerResultVO;
import org.scoula.dangerResult.mapper.DangerResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DangerResultServiceImpl implements DangerResultService {

    private final DangerResultMapper dangerResultMapper;
    private final ChecklistService  checklistService;

    @Override
    public DangerResultVO analysisDangerResult(Long templateId, Long userId) {
        List<DangerAnswerVO> answerDTOList = dangerResultMapper.getAnswerList(templateId, userId);
        List<ChecklistDTO> checklistDTOList = checklistService.getChecklist(templateId);

        System.out.println("답안 list 개수 = " + answerDTOList.size());

        StringBuilder sb = new StringBuilder();
        int score = 100;
        if(!answerDTOList.isEmpty()){
            for (int i = 0; i < answerDTOList.size(); i++) {
                if(answerDTOList.get(i).getAnswer() == 1){
                    score -= answerDTOList.get(i).getRiskWeight();
                }else{
                    sb.append(checklistDTOList.get(i).getNecessity()).append('\n').append('\n');
                }

            }
        }
        System.out.println("사용자의 점수 = " + score);

        DangerResultVO dangerResultVO = getMessageList(score, templateId);
        dangerResultVO.setDescription(sb.toString());

        return dangerResultVO;
    }

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
