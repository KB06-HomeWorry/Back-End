package org.scoula.checklist.mapper;

import org.scoula.checklist.domain.ChecklistVO;

import java.util.List;

public interface ChecklistMapper {
    List<ChecklistVO> get(long template_id);

}
