package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.request.AddTalentRequestDTO;
import com.tujuhsembilan.app.dto.request.EditTalentRequestDTO;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lib.minio.configuration.property.MinioProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@Slf4j
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProp minioProp;

    private String sanitizeForFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9]","-");
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }

    @Autowired
    private TalentLevelRepository talentLevelRepository;

    public String uploadImageToMinio(AddTalentRequestDTO requestDTO, MultipartFile imageFile) throws IOException {
        Optional<TalentLevel> talentLevel = talentLevelRepository.findById(requestDTO.getTalentLevelId());

        String talentName = sanitizeForFilename(requestDTO.getTalentName());
        String experience = sanitizeForFilename(String.valueOf(requestDTO.getTalentExperience()));
        String levelName = sanitizeForFilename(talentLevel.get().getTalentLevelName());

        if (talentName.isEmpty() || experience.isEmpty() || levelName.isEmpty()) {
            log.warn("One or more components for filename are empty. Talent: {}, Experience: {}, Level: {}",
                requestDTO.getTalentName(),
                requestDTO.getTalentExperience(),
                talentLevel.get().getTalentLevelName());
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = getFileExtension(imageFile.getOriginalFilename());

        String generatedFilename = String.format(
                "%s_%s_%s_%s%s",
                talentName,
                experience,
                levelName,
                timestamp,
                fileExtension
        );

//        try (InputStream inputStream = imageFile.getInputStream()) {
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(minioProp.getBucketName())
//                            .object(generatedFilename)
//                            .stream(inputStream, imageFile.getSize(), -1)
//                            .contentType(imageFile.getContentType())
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new IOException("Failed to upload image to MinIo", e);
//        }

        try (InputStream inputStream = imageFile.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProp.getBucketName())
                            .object(generatedFilename)
                            .stream(inputStream, imageFile.getSize(), -1)
                            .contentType(imageFile.getContentType())
                            .build()
            );
            log.info("Uploaded image filename: {}", generatedFilename);
        } catch (Exception e) {
            log.error("Failed to upload image to MinIO. Filename: {}", generatedFilename, e);
            throw new IOException("Failed to upload image to MinIO", e);
        }


        log.info(generatedFilename);
        return generatedFilename;
    }

    public String updateImageToMinIo(EditTalentRequestDTO requestDTO, MultipartFile imageFile) throws IOException {
        Optional<TalentLevel> talentLevel = talentLevelRepository.findById(requestDTO.getTalentLevelId());

        String talentName = sanitizeForFilename(requestDTO.getTalentName());
        String experience = sanitizeForFilename(String.valueOf(requestDTO.getTalentExperience()));
        String levelName = sanitizeForFilename(talentLevel.get().getTalentLevelName());

        if (talentName.isEmpty() || experience.isEmpty() || levelName.isEmpty()) {
            log.warn("One or more components for filename are empty. Talent: {}, Experience: {}, Level: {}",
                requestDTO.getTalentLevelId(),
                requestDTO.getTalentExperience(),
                talentLevel.get().getTalentLevelName()
            );
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileExtension = getFileExtension(imageFile.getOriginalFilename());

        String generatedFilename = String.format(
                "%s_%s_%s_%s%s",
                talentName,
                experience,
                levelName,
                timestamp,
                fileExtension
        );

        try (InputStream inputStream = imageFile.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProp.getBucketName())
                            .object(generatedFilename)
                            .stream(inputStream, imageFile.getSize(), -1)
                            .contentType(imageFile.getContentType())
                            .build()
            );
            log.info("Update image filename {} : ", generatedFilename);
        } catch (Exception e) {
            log.error("Failed to Update image to MinIo. Filename {} :",generatedFilename, e);
            throw new IOException("Failed to Updated image to MinIo", e);
        }
        log.info(generatedFilename);
        return generatedFilename;
    }
}
