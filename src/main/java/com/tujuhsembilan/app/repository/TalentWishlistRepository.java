package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TalentWishlistRepository extends JpaRepository<TalentWishlist, UUID> {
}
