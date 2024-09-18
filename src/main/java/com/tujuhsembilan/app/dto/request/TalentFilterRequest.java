package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentFilterRequest {
    private String talentName;
    private UUID talentStatus;
    private UUID employeeStatus;
    private Integer minTalentExperience;
    private Integer maxTalentExperience;
    private UUID talentLevel;
}
