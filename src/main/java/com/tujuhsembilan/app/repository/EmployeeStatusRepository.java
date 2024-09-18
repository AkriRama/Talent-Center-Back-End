package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, UUID> {
}
