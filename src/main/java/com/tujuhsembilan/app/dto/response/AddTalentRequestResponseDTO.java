package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTalentRequestResponseDTO {
    private String message;
    private int statusCode;
    private String status;
}