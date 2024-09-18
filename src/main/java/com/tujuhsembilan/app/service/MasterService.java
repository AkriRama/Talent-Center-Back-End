package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MasterService {

    @Autowired
    private TalentLevelRepository talentLevelRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private SkillsetRepository skillsetRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TalentStatusRepository talentStatusRepository;

    public ResponseEntity<TalentLevelResponse> getTalentLevel() {
        try {
            List<TalentLevel> talentLevels = talentLevelRepository.findAll();

            List<TalentLevelResponseDTO> talentLevelResponseDTO = talentLevels.stream().map(talentLevel ->
                    new TalentLevelResponseDTO(
                            talentLevel.getTalentLevelId(),
                            talentLevel.getTalentLevelName()
                    )).collect(Collectors.toList());

            int totalData = talentLevels.size();

            return ResponseEntity
                    .ok()
                    .body(new TalentLevelResponse(totalData, talentLevelResponseDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new TalentLevelResponse(1, null));
        }
    }

    public ResponseEntity<EmployeeStatusResponse> getEmployeeStatus(){
        try {
            List<EmployeeStatus> employeeStatuses = employeeStatusRepository.findAll();

            List<EmployeeStatusResponseDTO> responseDTOS = employeeStatuses.stream().map(employeeStatus ->
                    new EmployeeStatusResponseDTO(
                            employeeStatus.getEmployeeStatusId(),
                            employeeStatus.getEmployeeStatusName()
                    )).collect(Collectors.toList());
            int totalData = employeeStatuses.size();

            return ResponseEntity
                    .ok()
                    .body(new EmployeeStatusResponse(totalData, responseDTOS));
        } catch(Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new EmployeeStatusResponse(0, null));
        }
    }

    public ResponseEntity<SkillsetResponse> getSkillset() {
        try {
            List<Skillset> skillsets = skillsetRepository.findAll();

            List<SkillsetResponseDTO> skillsetResponseDTOS = skillsets.stream().map(skillset ->
                    new SkillsetResponseDTO(
                            skillset.getSkillsetId(),
                            skillset.getSkillsetName()
                    )).collect(Collectors.toList());
            int totalData = skillsets.size();

            return ResponseEntity
                    .ok()
                    .body(new SkillsetResponse(totalData, skillsetResponseDTOS));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new SkillsetResponse(0, null));
        }
    }

    public ResponseEntity<PositionResponse> getPosition(){
        try {
            List<Position> positionList = positionRepository.findAll();

            List<PositionResponseDTO> positionResponseDTOS = positionList.stream().map(position ->
                    new PositionResponseDTO(
                            position.getPositionId(),
                            position.getPositionName()
                    )).collect(Collectors.toList());
            int totalData = positionList.size();

            return ResponseEntity
                    .ok()
                    .body(new PositionResponse(totalData, positionResponseDTOS));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new PositionResponse(0, null));
        }
    }

    public ResponseEntity<TalentStatusResponse> getTalentStatus() {
        try {
            List<TalentStatus> talentStatusList = talentStatusRepository.findAll();

            List<TalentStatusResponseDTO> talentStatusResponseDTOS = talentStatusList.stream().map(talentStatus ->
                    new TalentStatusResponseDTO(
                            talentStatus.getTalentStatusId(),
                            talentStatus.getTalentStatusName()
                    )).collect(Collectors.toList());

            int totalData = talentStatusResponseDTOS.size();

            return ResponseEntity
                    .ok()
                    .body(new TalentStatusResponse(totalData, talentStatusResponseDTOS));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new TalentStatusResponse(0, null));
        }
    }
}
