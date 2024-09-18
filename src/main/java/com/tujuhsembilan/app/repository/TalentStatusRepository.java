package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TalentStatusRepository extends JpaRepository<TalentStatus, UUID> {
}
