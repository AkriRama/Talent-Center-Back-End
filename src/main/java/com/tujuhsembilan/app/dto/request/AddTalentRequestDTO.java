package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTalentRequestDTO {
    private String talentPhoto;
    private String talentName;
    private UUID talentStatusId;
    private String nip;
    private char sex;
    private Timestamp dob;
    private String talentDescription;
    private String cv;
    private int talentExperience;
    private UUID talentLevelId;
    private Integer projectCompleted;
    private List<PositionRequestDTO> position;
    private List<SkillsetRequestDTO> skillSet;
    private String email;
    private String cellphone;
    private UUID employeeStatusId;
    private Boolean talentAvailability;
    private String videoUrl;
}
