package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID>, JpaSpecificationExecutor<TalentRequest> {
}
