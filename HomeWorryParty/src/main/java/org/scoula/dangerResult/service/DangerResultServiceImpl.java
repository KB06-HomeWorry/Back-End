package org.scoula.dangerResult.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @Override
    public DangerResultVO analysisDangerResult(Long templateId, Long userId) {
        List<DangerAnswerVO> answerDTOList = dangerResultMapper.getAnswerList(templateId, userId);

        int score = 100;
        if(!answerDTOList.isEmpty()){
            for (DangerAnswerVO dangerAnswerVO : answerDTOList) {
                if(dangerAnswerVO.getAnswer() == 1){
                    score -= dangerAnswerVO.getRiskWeight();
                }
                //System.out.println(dangerAnswerDTO);
            }
        }
        System.out.println("사용자의 점수 = " + score);

        return getMessageList(score, templateId);

    }

    @Override
    public DangerResultVO getMessageList(int score, Long templateId) {
        List<DangerResultVO> dangerResultVOList = dangerResultMapper.getMessageList(templateId);


        for (DangerResultVO dangerResultVO : dangerResultVOList) {
            if(score >= dangerResultVO.getMinScore() && score <= dangerResultVO.getMaxScore()){
                return dangerResultVO;
            }
            System.out.println(dangerResultVO);
        }

        return null;
    }
}
