package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Skillset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillsetRepository extends JpaRepository<Skillset, UUID> {
}
