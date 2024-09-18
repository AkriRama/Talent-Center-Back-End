package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentRequestListResponseDTO {
    private UUID talentRequestId;
    private String agencyName;
    private Timestamp requestDate;
    private String talentName;
    private String approvalStatus;
}
