package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TalentLevelRepository extends JpaRepository<TalentLevel, UUID> {
}
