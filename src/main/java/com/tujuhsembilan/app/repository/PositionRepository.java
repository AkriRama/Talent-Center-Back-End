package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
}
