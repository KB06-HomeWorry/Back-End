package org.scoula.dangerResult.service;

import org.scoula.dangerResult.domain.DangerResultVO;

public interface DangerResultService {
    DangerResultVO analysisDangerResult(Long templateId, Long userId);
    DangerResultVO getMessageList(int score, Long templateId);
}
