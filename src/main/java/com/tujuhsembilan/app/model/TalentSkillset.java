package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.Times;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "talent_skillset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(TalentSkillsetID.class)
public class TalentSkillset {

//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "talent_skillset_id")
//    private UUID talentSkillsetId;

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

    @Id
    @ManyToOne
    @JoinColumn(name = "talent_id", referencedColumnName = "talent_id")
    private Talent talent;

    @Id
    @ManyToOne
    @JoinColumn(name = "skillset_id", referencedColumnName = "skillset_id")
    private Skillset skillset;
}
