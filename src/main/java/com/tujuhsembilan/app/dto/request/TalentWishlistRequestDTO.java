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
public class TalentWishlistRequestDTO {
    private UUID talentID;
    private UUID clientID;
}
