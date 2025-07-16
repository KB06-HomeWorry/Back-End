package org.scoula.checklist.service;

import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;
import org.scoula.user.dto.ChangePasswordDTO;
import org.scoula.user.dto.UserDTO;
import org.scoula.user.dto.UserJoinDTO;
import org.scoula.user.dto.UserUpdateDTO;

import java.util.List;

public interface ChecklistService {


    List<ChecklistDTO> getChecklist(String stage, String type);
    ChecklistTemplateDTO getChecklistTemplate(String stage, String type);


}
