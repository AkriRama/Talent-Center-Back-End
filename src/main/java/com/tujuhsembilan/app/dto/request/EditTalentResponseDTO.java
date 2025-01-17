package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditTalentResponseDTO {
    private String message;
    private int statusCode;
    private String status;
}
