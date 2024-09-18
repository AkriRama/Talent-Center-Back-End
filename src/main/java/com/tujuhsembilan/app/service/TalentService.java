package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.request.*;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import com.tujuhsembilan.app.service.specification.TalentRequestSpesification;
import lib.minio.MinioSrvc;
import lib.minio.configuration.property.MinioProp;
import lib.minio.exception.MinioServiceUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TalentService {
    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private TalentPositionRepository talentPositionRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TalentSkillsetRepository talentSkillsetRepository;

    @Autowired
    private SkillsetRepository skillsetRepository;

    @Autowired
    private TalentStatusRepository talentStatusRepository;

    @Autowired
    private TalentLevelRepository talentLevelRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private TalentRequestRepository talentRequestRepository;

    @Autowired
    private TalentWishlistRepository talentWishlistRepository;

    @Lazy
    @Autowired
    private MinioService minioService;

    @Autowired

    private MinioSrvc minioSrvc;

    @Autowired
    private MinioProp minioProp;

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<TalentListResponse> getTalents(TalentFilterRequest request ,Pageable pageable) {
            try {
                Specification<Talent> talentSpecification = TalentRequestSpesification.requestFilterTalent(request);
                Page<Talent> talents = talentRepository.findAll(talentSpecification, pageable);
                List<UUID> uuidList = talents.stream().map(talent -> talent.getTalentId()).collect(Collectors.toList());
                List<TalentListResponseDTO> talentsDTO = new ArrayList<>();
                for (UUID uuid : uuidList) {
                    List<TalentPosition> talentPositions  = talentPositionRepository.findByTalent(uuid);
                    List<Position> positionList = positionRepository.findAllById(talentPositions.stream().map(talentPosition -> talentPosition.getPosition().getPositionId()).collect(Collectors.toList()));
                    List<TalentSkillset> talentSkillsets = talentSkillsetRepository.findByTalent(uuid);
                    List<Skillset> skillsetList = skillsetRepository.findAllById(talentSkillsets.stream().map(talentSkillset -> talentSkillset.getSkillset().getSkillsetId()).collect(Collectors.toList()));

                    List<PositionResponseDTO> positionResponseDTOS = positionList.stream().map(position ->
                            new PositionResponseDTO(
                                    position.getPositionId(),
                                    position.getPositionName()
                            )).collect(Collectors.toList());

                    List<SkillsetResponseDTO> skillsetResponseDTOS = skillsetList.stream().map(skillset ->
                            new SkillsetResponseDTO(
                                    skillset.getSkillsetId(),
                                    skillset.getSkillsetName()
                            )).collect(Collectors.toList());

                    talentsDTO = talents.stream().map(talent ->
                            new TalentListResponseDTO(
                                    talent.getTalentId(),
                                    talent.getTalentPhotoFilename(),
                                    talent.getTalentName(),
                                    talent.getTalentStatus().getTalentStatusName(),
                                    talent.getEmployeeStatus().getEmployeeStatusName(),
                                    talent.getTalentStatus().getIsActive(),
                                    talent.getExperience(),
                                    talent.getTalentLevel().getTalentLevelName(),
                                    positionResponseDTOS,
                                    skillsetResponseDTOS,
                                    talent.getTalentPhotoFilename() == null ? "" : minioSrvc.getLink(minioProp.getBucketName(), talent.getTalentName(), 7L) ,
                                    talent.getTalentCvFilename() == null ? "" : minioSrvc.getLink(minioProp.getBucketName(), talent.getTalentCvFilename(), 7L)
                            )
                    ).toList();
                }

                long totalData = talentRepository.count(talentSpecification);
                return ResponseEntity
                        .ok()
                        .body(new TalentListResponse(totalData,talentsDTO ));
            } catch (Exception e) {
                log.error(null, e);
    //            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
                return ResponseEntity
                        .internalServerError()
                        .body(new TalentListResponse(0L, null));
            }
        }

    public ResponseEntity<TalentResponse> getTalentByID(UUID talentId) {
        try {
            Optional<Talent> talentOptional = talentRepository.findById(talentId);
            Talent talent = talentOptional.get();
            List<TalentPosition> talentPosition = talentPositionRepository.findByTalent(talentId);
            List<Position> positionOptional = positionRepository.findAllById(talentPosition.stream().map(talentPosition1 -> talentPosition1.getPosition().getPositionId()).collect(Collectors.toList()));
            List<TalentSkillset> talentSkillset = talentSkillsetRepository.findByTalent(talentId);
            List<Skillset> skillsetOptional = skillsetRepository.findAllById(talentSkillset.stream().map(talentSkillset1 -> talentSkillset1.getSkillset().getSkillsetId()).collect(Collectors.toList()));

            List<PositionResponseDTO> positionResponseDTO = positionOptional.stream().map(position ->
                            new PositionResponseDTO(
                            position.getPositionId(),
                            position.getPositionName()
                        )).collect(Collectors.toList());

            List<SkillsetResponseDTO> skillsetResponseDTO = skillsetOptional.stream().map(skillset ->
                    new SkillsetResponseDTO(
                    skillset.getSkillsetId(),
                    skillset.getSkillsetName()
            )).collect(Collectors.toList());

            TalentResponseDTO talentDTO = new TalentResponseDTO(
                            talent.getTalentId(),
                            talent.getTalentPhotoFilename(),
                            talent.getTalentName(),
                            talent.getTalentStatus().getTalentStatusName(),
                            talent.getEmployeeNumber(),
                            talent.getSex(),
                            talent.getBirthDate(),
                            talent.getTalentDesc(),
                            talent.getTalentCvFilename(),
                            talent.getExperience(),
                            talent.getTalentLevel().getTalentLevelName(),
                            talent.getTotalProjectCompleted() == null ? 0 : talent.getTotalProjectCompleted(),
                            positionResponseDTO,
                            skillsetResponseDTO,
                            talent.getEmail(),
                            talent.getCellphone(),
                            talent.getEmployeeStatus().getEmployeeStatusName(),
                            talent.getTalentAvailability(),
                            talent.getBiographyVideoUrl(),
                            minioSrvc.getLink(minioProp.getBucketName(), talent.getTalentPhotoFilename(), 7L),
                            minioSrvc.getLink(minioProp.getBucketName(), talent.getTalentCvFilename(), 7L));

            return ResponseEntity
                    .ok()
                    .body(new TalentResponse(1, talentDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new TalentResponse(0, null));
        }
    }

    public ResponseEntity<AddTalentResponseDTO> addTalent(AddTalentRequestDTO requestDTO, MultipartFile imageFile, MultipartFile docFile) {
        try {

              log.info("image {}", imageFile.getOriginalFilename());

            Optional<TalentStatus> talentStatusID = talentStatusRepository.findById(requestDTO.getTalentStatusId());
            if (talentStatusID == null) {
                String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                String formatMessage = MessageFormat.format(message, requestDTO.getTalentStatusId());
                return ResponseEntity
                        .ok()
                        .body(new AddTalentResponseDTO(
                                formatMessage,
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase()
                        ));
            }

            Optional<TalentLevel> talentLevelID = talentLevelRepository.findById(requestDTO.getTalentLevelId());
            if (talentLevelID == null) {
                String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                String formatMessage = MessageFormat.format(message, requestDTO.getTalentLevelId());
                return ResponseEntity
                        .ok()
                        .body(new AddTalentResponseDTO(
                                formatMessage,
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase()
                        ));
            }

            Optional<EmployeeStatus> employeeStatusID = employeeStatusRepository.findById(requestDTO.getEmployeeStatusId());
            if (employeeStatusID == null) {
                String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                String formatMessage = MessageFormat.format(message, requestDTO.getEmployeeStatusId());
                return ResponseEntity
                        .ok()
                        .body(new AddTalentResponseDTO(
                                formatMessage,
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase()
                        ));
            }

            List<Position> positionList = positionRepository.findAllById(requestDTO.getPosition().stream().map(positionRequestDTO -> positionRequestDTO.getPositionId()).collect(Collectors.toList()));
            for (Position position : positionList) {
                if (position == null) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    String formatMessage = MessageFormat.format(message, position.getPositionId());
                    return ResponseEntity
                            .ok()
                            .body(new AddTalentResponseDTO(
                                    formatMessage,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }
            }

            List<Skillset> skillsetList = skillsetRepository.findAllById(requestDTO.getSkillSet().stream().map(skillsetRequestDTO -> skillsetRequestDTO.getSkillId()).collect(Collectors.toList()));
            for (Skillset skillset : skillsetList) {
                if (skillset == null) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    String formatMessage = MessageFormat.format(message, skillset.getSkillsetId());
                    return ResponseEntity
                            .ok()
                            .body(new AddTalentResponseDTO(
                                    formatMessage,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }
            }


            String imageFilename;
            try {
                imageFilename = minioService.uploadImageToMinio(requestDTO, imageFile);
            } catch (IOException e) {
                String message = messageSource.getMessage("application.error.upload.minio", null, Locale.getDefault());
                throw new MinioServiceUploadException(message, e);
            }

            String cvFilename;
            try {
                cvFilename = minioService.uploadImageToMinio(requestDTO, docFile);
            } catch (Exception e) {
                String message = messageSource.getMessage("application.error.upload.minio", null, Locale.getDefault());
                throw new MinioServiceUploadException(message, e);
            }

            Talent talent = Talent.builder()
                    .talentPhotoFilename(imageFilename)
                    .talentName(requestDTO.getTalentName())
                    .talentStatus(TalentStatus.builder().talentStatusId(requestDTO.getTalentStatusId()).build())
                    .employeeNumber(requestDTO.getNip())
                    .sex(requestDTO.getSex())
                    .birthDate(requestDTO.getDob())
                    .talentDesc(requestDTO.getTalentDescription())
                    .talentCvFilename(cvFilename)
                    .experience(requestDTO.getTalentExperience())
                    .talentLevel(TalentLevel.builder().talentLevelId(requestDTO.getTalentLevelId()).build())
                    .totalProjectCompleted(requestDTO.getProjectCompleted())
                    .email(requestDTO.getEmail())
                    .cellphone(requestDTO.getCellphone())
                    .employeeStatus(EmployeeStatus.builder().employeeStatusId(requestDTO.getEmployeeStatusId()).build())
                    .talentAvailability(requestDTO.getTalentAvailability())
                    .biographyVideoUrl(requestDTO.getVideoUrl())
                    .isActive(true)
                    .build();
            Talent talentID = talentRepository.save(talent);

            for (Position position : positionList) {
                TalentPosition talentPosition = TalentPosition.builder()
                        .talent(talentID)
                        .position(position)
                        .build();
                talentPositionRepository.save(talentPosition);
            }

            for (Skillset skillset : skillsetList) {
                TalentSkillset talentSkillset = TalentSkillset.builder()
                        .talent(talentID)
                        .skillset(skillset)
                        .build();
                talentSkillsetRepository.save(talentSkillset);
            }

            String message = messageSource.getMessage("item.save.success", null, Locale.getDefault());
            return ResponseEntity
                    .ok()
                    .body(new AddTalentResponseDTO(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {

            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity
                    .internalServerError()
                    .body(new AddTalentResponseDTO(
                            message,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                    ));
        }
    }

    public ResponseEntity<EditTalentResponseDTO> editTalent(EditTalentRequestDTO requestDTO, MultipartFile imageFile, MultipartFile docFile) {
        try {
            log.info("imageFile {} :", imageFile.getOriginalFilename());
            Optional<Talent> talent = talentRepository.findById(requestDTO.getTalentId());

            if (!talent.isEmpty()) {
                Optional<TalentStatus> talentStatus = talentStatusRepository.findById(requestDTO.getTalentStatusId());
                if (talentStatus.isEmpty()) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    String formatMessage = MessageFormat.format(message, requestDTO.getTalentStatusId());
                    return ResponseEntity
                            .ok()
                            .body(new EditTalentResponseDTO(
                                    formatMessage,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }

                Optional<TalentLevel> talentLevel = talentLevelRepository.findById(requestDTO.getTalentLevelId());
                if (talentLevel.isEmpty()) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    String formatMessage = MessageFormat.format(message, requestDTO.getTalentLevelId());
                    return ResponseEntity
                            .ok()
                            .body(new EditTalentResponseDTO(
                                    formatMessage,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }

                Optional<EmployeeStatus> employeeStatus = employeeStatusRepository.findById(requestDTO.getEmployeeStatusId());
                if (employeeStatus.isEmpty()) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    String formatMessage = MessageFormat.format(message, requestDTO.getEmployeeStatusId());
                    return ResponseEntity
                            .ok()
                            .body(new EditTalentResponseDTO(
                                    formatMessage,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }

                List<Position> positionList = positionRepository.findAllById(requestDTO.getPosition().stream().map(PositionRequestDTO::getPositionId).collect(Collectors.toList()));
                for (Position position : positionList) {
                    log.info("position : {}", position);
                    Optional<Position> positionOptional = positionRepository.findById(position.getPositionId());
                    if (positionOptional.isEmpty()) {
                        String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                        String formatMessage = MessageFormat.format(message, positionOptional.get().getPositionId());
                        return ResponseEntity
                                .ok()
                                .body(new EditTalentResponseDTO(
                                        formatMessage,
                                        HttpStatus.BAD_REQUEST.value(),
                                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                                ));
                    }
                }

                List<Skillset> skillsetList = skillsetRepository.findAllById(requestDTO.getSkillSet().stream().map(SkillsetRequestDTO::getSkillId).collect(Collectors.toList()));
                for (Skillset skillset : skillsetList) {
                    Optional<Skillset> skillsetOptional = skillsetRepository.findById(skillset.getSkillsetId());
                    if (skillsetOptional.isEmpty()){
                        String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                        String formatMessage = MessageFormat.format(message, skillsetOptional.get().getSkillsetId());
                        return ResponseEntity
                                .ok()
                                .body(new EditTalentResponseDTO(
                                        formatMessage,
                                        HttpStatus.BAD_REQUEST.value(),
                                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                                ));
                    }
                }

                String imageFilename;
                try {
                    imageFilename = minioService.updateImageToMinIo(requestDTO, imageFile);
                } catch (Exception e) {
                    String message = messageSource.getMessage("application.error.update.minio", null, Locale.getDefault());
                    throw new MinioServiceUploadException(message, e);
                }

                String cvFilename;
                try {
                    cvFilename = minioService.updateImageToMinIo(requestDTO, docFile);
                } catch (Exception e) {
                    String message = messageSource.getMessage("application.error.upload.minio", null, Locale.getDefault());
                    throw new MinioServiceUploadException(message, e);
                }

                Talent talentEdit = talent.get();
                talentEdit.setTalentPhotoFilename(imageFilename);
                talentEdit.setTalentName(requestDTO.getTalentName());
                talentEdit.setTalentStatus(TalentStatus.builder().talentStatusId(requestDTO.getTalentStatusId()).build());
                talentEdit.setEmployeeNumber(requestDTO.getNip());
                talentEdit.setBirthDate(requestDTO.getDob());
                talentEdit.setTalentDesc(requestDTO.getTalentDescription());
                talentEdit.setTalentCvFilename(cvFilename);
                talentEdit.setExperience(requestDTO.getTalentExperience());
                talentEdit.setTalentLevel(TalentLevel.builder().talentLevelId(requestDTO.getTalentLevelId()).build());
                talentEdit.setTotalProjectCompleted(requestDTO.getProjectCompleted());
                talentEdit.setEmail(requestDTO.getEmail());
                talentEdit.setCellphone(requestDTO.getCellphone());
                talentEdit.setEmployeeStatus(EmployeeStatus.builder().employeeStatusId(requestDTO.getEmployeeStatusId()).build());
                talentEdit.setTalentAvailability(requestDTO.getTalentAvailability());
                talentEdit.setBiographyVideoUrl(requestDTO.getVideoUrl());
                talentRepository.save(talentEdit);

                List<TalentPosition> talentPositionList = talentPositionRepository.findByTalent(requestDTO.getTalentId());
                talentPositionRepository.deleteAll(talentPositionList);
                for (Position position : positionList) {
                    TalentPosition talentPosition = TalentPosition.builder()
                            .talent(talentEdit)
                            .position(position)
                            .build();
                    talentPositionRepository.save(talentPosition);

                }

                List<TalentSkillset> talentSkillsetList = talentSkillsetRepository.findByTalent(requestDTO.getTalentId());
                talentSkillsetRepository.deleteAll(talentSkillsetList);
                for (Skillset skillset : skillsetList) {
                    TalentSkillset talentSkillset = TalentSkillset.builder()
                            .talent(talentEdit)
                            .skillset(skillset)
                            .build();
                    talentSkillsetRepository.save(talentSkillset);
//
                }
                log.info("imageFile {} :", imageFile.getOriginalFilename());

            }

            String message = messageSource.getMessage("item.update.success", null, Locale.getDefault());
            return ResponseEntity
                    .ok()
                    .body(new EditTalentResponseDTO(
                            message,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ));
        } catch(Exception e) {
            log.info("error");

            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity
                    .internalServerError()
                    .body(new EditTalentResponseDTO(
                            message,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    );
        }
    }

    public ResponseEntity<TalentRequestListResponse> getTalentRequest(TalentRequestRequest request, Pageable pageable) {
        try {
            Specification<TalentRequest> talentRequestSpecification = TalentRequestSpesification.requestFilter(request);
            Page<TalentRequest> talentRequests = talentRequestRepository.findAll(talentRequestSpecification, pageable);

            List<TalentRequestListResponseDTO> responseDTOS = talentRequests.stream().map(talentRequest ->
                    new TalentRequestListResponseDTO(
                            talentRequest.getTalentRequestId(),
                            talentRequest.getTalentWishlist().getClient().getAgencyName(),
                            talentRequest.getRequestDate(),
                            talentRequest.getTalentWishlist().getTalent().getTalentName(),
                            talentRequest.getTalentRequestStatus().getTalentRequestStatusName()
                    )).collect(Collectors.toList());

            int totalData = (int) talentRequests.getTotalElements();
            return ResponseEntity
                    .ok()
                    .body(new TalentRequestListResponse(totalData, responseDTOS));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new TalentRequestListResponse(
                            0,
                            Collections.emptyList()
                    ));
        }
    }

    public ResponseEntity<TalentRequestResponse> approveRejectTalent(TalentRequestRequestDTO requestDTO) {
            try {
                log.info("request : {}", requestDTO.getTalentRequestId());
                Optional<TalentRequest> talentRequest = talentRequestRepository.findById(requestDTO.getTalentRequestId());
                if (talentRequest.isEmpty()) {
                    String message = messageSource.getMessage("item.not.found", null, Locale.getDefault());
                    return ResponseEntity
                            .ok()
                            .body(new TalentRequestResponse(
                                    message,
                                    HttpStatus.BAD_REQUEST.value(),
                                    HttpStatus.BAD_REQUEST.getReasonPhrase()
                            ));
                }

                TalentRequest talent = talentRequest.get();
                if (requestDTO.getAction().equalsIgnoreCase("approve")) {
                    talent.setTalentRequestStatus(TalentRequestStatus.builder().talentRequestStatusId(UUID.fromString("f4d7f142-89a8-4e62-b972-2f2a32904f77")).build());
                    talent.setRequestRejectReason(null);
                    talentRequestRepository.save(talent);
                } else if (requestDTO.getAction().equalsIgnoreCase("reject")) {
                    talent.setTalentRequestStatus(TalentRequestStatus.builder().talentRequestStatusId(UUID.fromString("03a316c9-f28e-47e2-a3ea-41baa65a1ed1")).build());
                    talent.setRequestRejectReason(requestDTO.getRejectReason().equalsIgnoreCase("") ? "Tidak Ada Alasan" : requestDTO.getRejectReason());
                    talentRequestRepository.save(talent);
                }

                String message = messageSource.getMessage(requestDTO.getAction().equalsIgnoreCase("approve") ? "talent.approve.success" : "talent.reject.failed", null, Locale.getDefault());
                return ResponseEntity
                        .ok()
                        .body(new TalentRequestResponse(
                                message,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        ));
            } catch (Exception e) {
                String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
                return ResponseEntity
                        .internalServerError()
                        .body(new TalentRequestResponse(
                                message,
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                        ));
            }
        }

    //TAMBAHAN
    public ResponseEntity<TalentWishlistResponseDTO> addTalentWishlist(TalentWishlistRequestDTO requestDTO) {
        try {
            TalentWishlist talentWishlist = TalentWishlist.builder()
                    .talent(Talent.builder().talentId(requestDTO.getTalentID()).build())
                    .client(Client.builder().clientId(requestDTO.getClientID()).build())
                    .isActive(true)
                    .build();
            talentWishlistRepository.save(talentWishlist);

            String message = messageSource.getMessage("item.save.success", null, Locale.getDefault());
            return ResponseEntity
                    .ok()
                    .body(new TalentWishlistResponseDTO(
                            message,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ));

        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity
                    .internalServerError()
                    .body(new TalentWishlistResponseDTO(
                            message,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                    ));
        }
    }

    public ResponseEntity<AddTalentRequestResponseDTO> addTalentRequest(AddTalentRequestRequestDTO requestDTO) {
        try {
            List<TalentWishlist> talentWishlists = talentWishlistRepository.findAllById(requestDTO.getTalentWishlist().stream().map(talentWishlistListRequestDTO ->
                    talentWishlistListRequestDTO.getTalentWishlistID()).collect(Collectors.toList()));

            for (TalentWishlist wishlist : talentWishlists) {
                TalentRequest talentRequest = TalentRequest.builder()
                        .talentRequestStatus(TalentRequestStatus.builder().talentRequestStatusId(UUID.fromString("f4d7f142-89a8-4e62-b972-2f2a32904f77")).build())
                        .talentWishlist(wishlist)
                        .build();
                talentRequestRepository.save(talentRequest);
            }

            String message = messageSource.getMessage("item.save.success", null, Locale.getDefault());
            return ResponseEntity
                    .ok()
                    .body(new AddTalentRequestResponseDTO(
                            message,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity
                    .internalServerError()
                    .body(new AddTalentRequestResponseDTO(
                            message,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                    ));
        }
    }
}
