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
public class TalentStatusResponse {
    private Integer mappingParameter;
    private List<TalentStatusResponseDTO> mappingResponse;
}
