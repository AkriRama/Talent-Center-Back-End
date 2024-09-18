package com.tujuhsembilan.app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentRequestListResponse {
    private int totalData;
    private List<TalentRequestListResponseDTO> mappingResponse;
}
