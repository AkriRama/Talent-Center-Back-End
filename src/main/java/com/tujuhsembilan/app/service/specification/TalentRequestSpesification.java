package com.tujuhsembilan.app.service.specification;

import com.tujuhsembilan.app.dto.request.TalentFilterRequest;
import com.tujuhsembilan.app.dto.request.TalentRequestRequest;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalentRequestSpesification {
    public static Specification<TalentRequest> requestFilter(TalentRequestRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();

            if (request.getAgencyName() != null) {
                String agencyNameValue = "%" + request.getAgencyName().toLowerCase() + "%";
                Predicate agencyNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("talentWishlist").get("client").get("agencyName")), agencyNameValue);
                predicateList.add(agencyNamePredicate);
            }

            if (request.getTalentName() != null) {
                String talentNameValue = "%" + request.getTalentName().toLowerCase() + "%";
                Predicate agencyNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("talentWishlist").get("talent").get("talentName")), talentNameValue);
                predicateList.add(agencyNamePredicate);
            }


            if (request.getApprovalStatus() != null) {
                String approvalStatusValue = "%" + request.getApprovalStatus().toLowerCase() + "%";
                Predicate approvalPredicate = criteriaBuilder.like(criteriaBuilder.lower(
                        root.get("talentRequestStatus").get("talentRequestStatusName")), approvalStatusValue
                );
                predicateList.add(approvalPredicate);
            }
            if (request.getStartRequestDate() != null && request.getEndRequestDate() != null) {
                Predicate datePredicate = criteriaBuilder.between(
                        root.get("requestDate"), request.getStartRequestDate(), request.getEndRequestDate()
                );
                predicateList.add(datePredicate);

            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }

    public static Specification<Talent> requestFilterTalent(TalentFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();

            if (request.getTalentName() != null) {
                String talentNameValue = "%" + request.getTalentName().toLowerCase() + "%";
                Predicate talentNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("talentName")), talentNameValue);
                predicateList.add(talentNamePredicate);
            }

            if (request.getTalentStatus() != null) {
                Predicate talentStatusPredicate = criteriaBuilder.equal(root.get("talentStatus").get("talentStatusId"), request.getTalentStatus());
                predicateList.add(talentStatusPredicate);
            }

            if (request.getEmployeeStatus() != null) {
                Predicate employeeStatusPredicate = criteriaBuilder.equal(root.get("employeeStatus").get("employeeStatusId"), request.getEmployeeStatus());
                predicateList.add(employeeStatusPredicate);
            }

            if (request.getMinTalentExperience() != null && request.getMaxTalentExperience() != null) {
                Predicate talentExperiencePredicate = criteriaBuilder.between(root.get("experience"), request.getMinTalentExperience(), request.getMaxTalentExperience());
                predicateList.add(talentExperiencePredicate);
            } else if (request.getMinTalentExperience() != null) {
                Predicate talentExperiencePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), request.getMinTalentExperience());
                predicateList.add(talentExperiencePredicate);
            } else if(request.getMaxTalentExperience() != null) {
                Predicate talenExperiencePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("experience"), request.getMaxTalentExperience());
                predicateList.add(talenExperiencePredicate);
            }

            if (request.getTalentLevel() != null) {
                Predicate talentLevelPredicate = criteriaBuilder.equal(root.get("talentLevel").get("talentLevelId"), request.getTalentLevel());
                predicateList.add(talentLevelPredicate);
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }
}
