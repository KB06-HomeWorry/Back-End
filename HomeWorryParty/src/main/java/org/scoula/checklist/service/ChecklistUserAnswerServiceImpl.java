package org.scoula.checklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.domain.ChecklistUserAnswerVO;
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

    @Override
    public List<ChecklistUserAnswerDTO> getAnswerList(long checklistId, long userId) {
        List<ChecklistUserAnswerVO> voList = Optional.ofNullable(
                checklistUserAnswerMapper.findByChecklistIdAndUserId(checklistId, userId)
        ).orElseThrow(() -> new IllegalArgumentException("해당하는 답변이 없습니다."));

        return voList.stream()
                .map(ChecklistUserAnswerDTO::of)
                .collect(Collectors.toList());
    }
}