package org.scoula.checklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.domain.ChecklistUserAnswerVO;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;
import org.scoula.checklist.mapper.ChecklistUserAnswerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChecklistUserAnswerServiceImpl implements ChecklistUserAnswerService {

    private final ChecklistUserAnswerMapper checklistUserAnswerMapper;
    final ChecklistService questionService;


    @Override
    public List<ChecklistUserAnswerDTO> getAnswerList(Long templateId, long userId, List<ChecklistDTO> checklist) {
        log.info("getAnswerList " + templateId + " " + userId);
        Long checkListId = checklistUserAnswerMapper.getCheckListId(userId, templateId);

        if(checkListId == null){
            checkListId = makeAnswerMemory(checkListId, userId, templateId, checklist);
        }

        List<ChecklistUserAnswerVO> voList =
                checklistUserAnswerMapper.findByChecklistIdAndUserId(checkListId, userId);

        log.info("getAnswerList " + checkListId + " " + voList.size());

        return voList.stream()
                .map(ChecklistUserAnswerDTO::of)
                .collect(Collectors.toList());
    }

    // 사용자가 해당 문제를 푼 적이 없을때 해당 사용자의 정답 리스트를 새로 생성하는 로직
    @Override
    public Long makeAnswerMemory(Long checkListId, Long userId, Long templateId, List<ChecklistDTO> checklist) {
        log.info("체크하지 않는 내용 -> 새로 생성");

        Long checklistId = makeNewCheckListId(userId, templateId);

        for(ChecklistDTO question : checklist){
            checklistUserAnswerMapper.insertAnswerList(checklistId, question.getChecklistId(), userId);
        }

        return checklistId;
    }

    // 사용자가 해당 문제를 푼 적이 없을때 사용자의 checklist 문제 id를 생성하는 로직
    @Override
    public Long makeNewCheckListId(Long userId, Long templateId) {
        checklistUserAnswerMapper.insertCheckList(userId, templateId);

        return checklistUserAnswerMapper.getCheckListId(userId, templateId);
    }

    // 사용자의 정답을 DB에 저장하는 로직
    @Override
    public void saveAnswerList(List<ChecklistUserAnswerDTO> answerDTOList) {
        for (ChecklistUserAnswerDTO dto : answerDTOList) {
            log.info(dto);
            checklistUserAnswerMapper.updateAnswer(
                    dto.getAnswer(), dto.getQuestionId(), dto.getUserId());
        }
    }
}