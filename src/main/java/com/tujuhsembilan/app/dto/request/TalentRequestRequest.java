package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentRequestRequest {
    private String agencyName;
    private String talentName;
    private Long startRequestDate;
    private Long endRequestDate;
    private String approvalStatus;
}
