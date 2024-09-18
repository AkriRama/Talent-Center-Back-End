package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentPosition;
import com.tujuhsembilan.app.model.TalentPositionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TalentPositionRepository extends JpaRepository<TalentPosition, UUID> {
    @Query("SELECT t FROM TalentPosition t WHERE t.talent.talentId = :talentId")
    List<TalentPosition> findByTalent(UUID talentId);

    @Query("SELECT t FROM TalentPosition t WHERE t.talent.talentId = :talentId")
    Optional<TalentPosition> findByTalentID(UUID talentId);

    @Query("SELECT t FROM TalentPosition t WHERE t.talent.talentId = :talentId")
    List<TalentPosition> findListByTalent(List<UUID> talentId);

//    List<TalentPosition> findByTalentIdIn(List<TalentPositionID> talentIds);
}
