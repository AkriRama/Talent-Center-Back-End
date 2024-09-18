package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {
    @Query("SELECT t FROM Talent t")
    List<Talent> findByNothing();
}
