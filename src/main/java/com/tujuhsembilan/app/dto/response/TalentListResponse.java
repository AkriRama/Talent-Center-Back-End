package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentListResponse {
    private Long mappingParameter;

    private List<TalentListResponseDTO> mappingResponse;
}
