package com.tujuhsembilan.app.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentListResponseDTO {
    private UUID talendId;
    private String talentPhotoUrl;
    private String talentName;
    private String talentStatus;
    private String employeeStatus;
    private Boolean talentAvailability;
    private Integer talentExperience;
    private String talentLevel;
    private List<PositionResponseDTO> position;
    private List<SkillsetResponseDTO> skillset;
    private String imageUrl;
    private String docUrl;
}
