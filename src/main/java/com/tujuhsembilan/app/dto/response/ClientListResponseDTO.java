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
public class ClientListResponseDTO {
    private UUID clientID;
    private String clientName;
    private String gender;
    private Timestamp birthDate;
    private String email;
    private String agencyName;
    private String agencyAddress;
    private Boolean isActive;
}
