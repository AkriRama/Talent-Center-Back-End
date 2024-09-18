package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.security.PrivateKey;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "talent_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TalentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "talent_request_id")
    private UUID talentRequestId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    private Timestamp requestDate;

    @Column(name = "request_reject_reason")
    private String requestRejectReason;

    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_time")
    private Timestamp lastModifiedTime;

    @ManyToOne
    @JoinColumn(name = "talent_request_status_id", referencedColumnName = "talent_request_status_id")
    private TalentRequestStatus talentRequestStatus;

    @ManyToOne
    @JoinColumn(name = "talent_wishlist_id", referencedColumnName = "talent_wishlist_id")
    private TalentWishlist talentWishlist;
}
