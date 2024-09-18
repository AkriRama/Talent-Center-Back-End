package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Table(name = "talent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Talent {
    @Id
    @Column(name = "talent_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentId;

    @Column(name = "talent_name")
    private String talentName;

    @Column(name = "talent_photo_filename")
    private String talentPhotoFilename;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "gender")
    private char sex;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Column(name = "talent_description", columnDefinition = "text")
    private String talentDesc;

    @Column(name = "talent_cv_filename")
    private String talentCvFilename;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "biography_video_url")
    private String biographyVideoUrl;

    @Column(name = "total_project_completed")
    private Integer totalProjectCompleted;

    @Column(name = "is_add_to_list_enable")
    private Boolean isAddToListEnable;

    @Column(name = "talent_availability")
    private Boolean talentAvailability;

    @Column(name = "is_active")
    private Boolean isActive;

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
    @JoinColumn(name = "talent_level_id", referencedColumnName = "talent_level_id")
    private TalentLevel talentLevel;

    @ManyToOne
    @JoinColumn(name = "talent_status_id", referencedColumnName = "talent_status_id")
    private TalentStatus talentStatus;

    @ManyToOne
    @JoinColumn(name = "employee_status_id", referencedColumnName = "employee_status_id")
    private EmployeeStatus employeeStatus;
}
//
//
//
//private UUID talentId;
//private String talentName;
//private String talentPhotoFilename;
//private String employeeNumber;
//private char sex;
//private Timestamp birthDate;
//private String talentDesc;
//private String talentCvFilename;
//private Integer experience;
//private String email;
//private String cellphone;
//private String biographyVideoUrl;
//private Integer totalProjectCompleted;
//private Boolean isAddToListEnable;
//private Boolean talentAvailability;
//private Boolean isActive;
//private TalentLevel talentLevel;
//private TalentStatus talentStatus;
//private EmployeeStatus
//
//{
//    "talentPhoto": "nasigoreng",
//        "talentName": "AkbarM",
//        "talentStatusId": "2bb69ed1-df80-4d01-ae1f-52de263c2953",
//        "nip": "1234567890",
//        "sex": "L",
//        "dob": "2023-07-01T10:00:00.000Z",
//        "talentDescription": "Saya adalah AkbarM79",
//        "cv": "cv",
//        "talentExperience": 1,
//        "talentLevelId": "22848659-c9e8-4df4-bd86-24c215442db3",
//        "projectCompleted": 3,
//        "position": [
//    {
//        "positionId": "86ac9ef9-90a5-412f-90fc-5ceff7be0a31"
//    }
//    ],
//    "skillSet": [
//    {
//        "skillId": "0e39324c-97b8-4868-87d6-d82584769242"
//    }
//    ],
//    "email": "akbarM",
//        "cellphone": "0987654321",
//        "employeeStatusId": "82dacb82-f0d6-4a09-9acb-ad67a2d9809e",
//        "talentAvailability": true,
//        "videoUrl": "url"
//}

