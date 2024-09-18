package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTalentRequestRequestDTO {
//    private UUID talentRequestStatusID;
    private List<TalentWishlistListRequestDTO> talentWishlist;
}
