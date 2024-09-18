package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentSkillset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TalentSkillsetRepository extends JpaRepository<TalentSkillset, UUID> {
    @Query("SELECT s FROM TalentSkillset s WHERE s.talent.talentId= :talentId")
    List<TalentSkillset> findByTalent(UUID talentId);

    @Query("SELECT s FROM TalentSkillset s WHERE s.talent.talentId= :talentId")
    Optional<TalentSkillset> findByTalentID(UUID talentId);
}
