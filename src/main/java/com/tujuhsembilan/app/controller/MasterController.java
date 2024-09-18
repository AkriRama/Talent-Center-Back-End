package com.tujuhsembilan.app.controller;


import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-management")
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping("/talent-level-option-lists")
    public ResponseEntity<TalentLevelResponse> getTalentLevel() {
        return masterService.getTalentLevel();
    }

    @GetMapping("/employee-status-option-lists")
    public ResponseEntity<EmployeeStatusResponse> getEmployeeStatus() {
        return masterService.getEmployeeStatus();
    }

    @GetMapping("/skill-set-option-lists")
    public ResponseEntity<SkillsetResponse> getSkillset() {
        return masterService.getSkillset();
    }

    @GetMapping("/talent-position-option-lists")
    public ResponseEntity<PositionResponse> getPosition() {
        return masterService.getPosition();
    }

    @GetMapping("talent-status-option-lists")
    public ResponseEntity<TalentStatusResponse> getTalentStatus() {
        return masterService.getTalentStatus();
    }


}
