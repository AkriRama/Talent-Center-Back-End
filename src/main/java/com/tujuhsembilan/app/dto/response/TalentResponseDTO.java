package com.tujuhsembilan.app.dto.response;

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
public class TalentResponseDTO {
    private UUID talentId;
    private String talentPhoto;
    private String talentName;
//    private Boolean talentStatus;
    private String talentStatus;
    private String nip;
    private char sex;
    private Timestamp dob;
    private String talentDescription;
    private String cv;
    private Integer talentExperience;
    private String talentLevel;
    private Integer projectCompleted;
//    private String totalRequested;
    private List<PositionResponseDTO> position;
    private List<SkillsetResponseDTO> skillSet;
    private String email;
    private String cellphone;
    private String employeeStatus;
    private Boolean talentAvailability;
    private String videoUrl;
    private String imageUrl;
    private String docUrl;
}
