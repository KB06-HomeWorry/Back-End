package org.scoula.checklist.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.junit.Test; // JUnit4

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
@Transactional
@Slf4j
public class ChecklistServiceImplTest {

    @Autowired
    ChecklistService  checklistService;

    @Autowired
    ChecklistUserAnswerService answerService;

    @Test
    public void getChecklist() {
        List<ChecklistDTO> checklistDTOList = checklistService.getChecklist(
                checklistService.getChecklistTemplate("매매", "입주 후"));
        //assertNotNull(checklistDTOList);
        for(ChecklistDTO checklistDTO : checklistDTOList){
            log.info("checklistDTO: {}", checklistDTO);
        }
    }

//    @Test
//    public void getChecklistAnswerList() {
//        List<ChecklistUserAnswerDTO> checklistAnswerDTOList = answerService.getAnswerList(1L, 1);
//
//        for(ChecklistUserAnswerDTO checklistDTO : checklistAnswerDTOList){
//            log.info("checklistDTO: {}", checklistDTO);
//        }
//    }


    @Test
    public void getChecklistTemplate() {
        ChecklistTemplateDTO  checklistTemplateDTO = new ChecklistTemplateDTO();
        checklistTemplateDTO = checklistService.getChecklistTemplate("매매", "입주 후");
        //assertNotNull(checklistTemplateDTO);
        log.info(checklistTemplateDTO.toString());
    }
}