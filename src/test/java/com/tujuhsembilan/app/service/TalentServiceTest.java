package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.request.*;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import com.tujuhsembilan.app.service.specification.TalentRequestSpesification;
import lib.minio.MinioSrvc;
import lib.minio.configuration.property.MinioProp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TalentServiceTest {

    @Mock
    private TalentRepository talentRepository;

    @Mock
    private TalentPositionRepository talentPositionRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private TalentSkillsetRepository talentSkillsetRepository;

    @Mock
    private SkillsetRepository skillsetRepository;

    @Mock
    private TalentStatusRepository talentStatusRepository;

    @Mock
    private EmployeeStatusRepository employeeStatusRepository;

    @Mock
    private TalentLevelRepository talentLevelRepository;

    @Mock
    private TalentRequestRepository talentRequestRepository;

    @Mock
    private MinioSrvc minioService;

    @Mock
    private MinioService minioService2;


    @Mock
    private MinioProp minioProp;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TalentService talentService;

    private TalentFilterRequest request;
    private Pageable pageable, pageableTR;
    private List<Talent> talentList;
    private PageRequest pageRequest, pageRequestTalentRequest;
    private List<TalentRequest> talentRequests;
    private List<Position> positionList;
    private List<Skillset> skillsetList;
    private List<TalentPosition> talentPositionList;
    private List<TalentSkillset> talentSkillsetList;

    private UUID talentID, talentRequestID;

    private AddTalentRequestDTO requestDTO;
    private EditTalentRequestDTO requestEditDTO;
    private MockMultipartFile imageFile;
    private MockMultipartFile docFile;

    private TalentRequestRequest requestTalentRequest;

    private TalentStatus talentStatus;
    private EmployeeStatus employeeStatus;
    private TalentLevel talentLevel;
    private UUID talentStatusId,
            talentLevelId,
            employeeStatusId,
            positionId,
            skillsetId;

    private TalentRequestRequestDTO talentRequestRequestDTO;

    @BeforeEach
    void setUp() {
//        setUp Talent List and TalentById
        talentStatus = TalentStatus.builder()
                .talentStatusId(UUID.randomUUID())
                .talentStatusName("Active")
                .isActive(true)
                .build();
        employeeStatus = EmployeeStatus.builder()
                .employeeStatusId(UUID.randomUUID())
                .employeeStatusName("Full-time")
                .build();
        talentLevel = TalentLevel.builder()
                .talentLevelId(UUID.randomUUID())
                .talentLevelName("Senior")
                .build();

        talentID = UUID.randomUUID();
        request = new TalentFilterRequest();
        pageRequest = PageRequest.builder().sortBy("talentName,asc").pageNumber(1).pageSize(8).build();
        pageable = pageRequest.getPageTalent();

        talentList = new ArrayList<>();
        talentList.add(Talent.builder()
                .talentId(talentID)
                .talentPhotoFilename("photo.jpg")
                .talentName("John Doe")
                .talentStatus(talentStatus)
                .employeeStatus(employeeStatus)
                .experience(5)
                .talentLevel(talentLevel).build()
        );

        positionList = Collections.singletonList(Position.builder()
                .positionId(UUID.randomUUID())
                .positionName("Developer")
                .build());

        talentPositionList = Collections.singletonList(
                TalentPosition.builder()
                        .talent(talentList.get(0))
                        .position(positionList.get(0))
                        .build());

        skillsetList = Collections.singletonList(Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("Java")
                .build());

        talentSkillsetList = Collections.singletonList(
                TalentSkillset.builder()
                        .talent(talentList.get(0))
                        .skillset(skillsetList.get(0))
                        .build());

        //TALENT REQUEST
        requestTalentRequest = new TalentRequestRequest();
        pageRequestTalentRequest = PageRequest.builder().sortBy("talentWishlist.client.agencyName,desc").pageNumber(1).pageSize(8).build();
        pageableTR = pageRequestTalentRequest.getPage();

        talentRequests = new ArrayList<>();
        talentRequests.add(TalentRequest.builder()
                .talentRequestId(talentRequestID)
                .requestDate(Timestamp.valueOf("2000-01-01 00:00:00"))
                .requestRejectReason("")
                .talentRequestStatus(TalentRequestStatus.builder()
                        .talentRequestStatusId(UUID.randomUUID())
                        .talentRequestStatusName("On Progress")
                        .isActive(true)
                        .build())
                .talentWishlist(TalentWishlist.builder()
                        .talentWishlistId(UUID.randomUUID())
                        .isActive(true)
                        .client(Client.builder()
                                .clientId(UUID.randomUUID())
                                .agencyName("Nemesis")
                                .build())
                        .talent(talentList.get(0))
                        .build())
                .build());

        //ADD TALENT
        talentStatusId = UUID.randomUUID();
        talentLevelId = UUID.randomUUID();
        employeeStatusId = UUID.randomUUID();
        positionId = UUID.randomUUID();
        skillsetId = UUID.randomUUID();

        // requestDTO setup
        requestDTO = new AddTalentRequestDTO();
        requestDTO.setTalentStatusId(talentStatusId);
        requestDTO.setTalentLevelId(talentLevelId);
        requestDTO.setEmployeeStatusId(employeeStatusId);
        requestDTO.setPosition(List.of(new PositionRequestDTO(positionId)));
        requestDTO.setSkillSet(List.of(new SkillsetRequestDTO(skillsetId)));
        // requestEditDTO setup
        requestEditDTO = new EditTalentRequestDTO();
        requestEditDTO.setTalentId(talentID);
        requestEditDTO.setTalentStatusId(talentStatusId);
        requestEditDTO.setTalentLevelId(talentLevelId);
        requestEditDTO.setEmployeeStatusId(employeeStatusId);
        requestEditDTO.setPosition(List.of(new PositionRequestDTO(positionId)));
        requestEditDTO.setSkillSet(List.of(new SkillsetRequestDTO(skillsetId)));

        // Initialize requestDTO with test data as needed
        imageFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "test image content".getBytes());
        docFile = new MockMultipartFile("docFile", "doc.pdf", "application/pdf", "test doc content".getBytes());


        //PUT AproveReject
        talentRequestID = UUID.randomUUID();

        talentRequestRequestDTO = new TalentRequestRequestDTO();
        talentRequestRequestDTO.setTalentRequestId(talentRequestID);
//        talentRequestRequestDTO.setAction("approve");
    }

    @Test
    void getTalents_success() {
        Page<Talent> talentPage = new PageImpl<>(talentList, pageable, talentList.size());
        when(talentRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(talentPage);
        when(talentRepository.count(any(Specification.class))).thenReturn((long) talentList.size());

        ResponseEntity<TalentListResponse> response = talentService.getTalents(request, pageable);
        log.info("gettalent: {}", response.getBody().getMappingResponse().get(0).getTalentName());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getMappingResponse().isEmpty());
        assertEquals("Status Code", 200, response.getStatusCodeValue());
        assertEquals("Total Data",1, response.getBody().getMappingParameter().intValue());
        verify(talentRepository, times( 1)).findAll(any(Specification.class), eq(pageable));
        verify(talentRepository, times(1)).count(any(Specification.class));
    }

    @Test
    void getTalentById_success() {
        when(talentRepository.findById(talentID)).thenReturn(Optional.of(talentList.get(0)));
        when(talentPositionRepository.findByTalent(any(UUID.class))).thenReturn(talentPositionList);
        when(positionRepository.findAllById(anyList())).thenReturn(positionList);
        when(talentSkillsetRepository.findByTalent(any(UUID.class))).thenReturn(talentSkillsetList);
        when(skillsetRepository.findAllById(anyList())).thenReturn(skillsetList);

        ResponseEntity<TalentResponse> response = talentService.getTalentByID(talentID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("Status Code", 200, response.getStatusCodeValue());
        assertEquals("Total Data",1, response.getBody().getMappingParameter().intValue());
    }

    @Test
    void testAddTalent_Success() throws IOException {
        when(talentStatusRepository.findById(talentStatusId)).thenReturn(Optional.of(talentStatus));
        when(talentLevelRepository.findById(talentLevelId)).thenReturn(Optional.of(talentLevel));
        when(employeeStatusRepository.findById(employeeStatusId)).thenReturn(Optional.of(employeeStatus));
        when(positionRepository.findAllById(List.of(positionId))).thenReturn(positionList);
        when(skillsetRepository.findAllById(List.of(skillsetId))).thenReturn(skillsetList);
        when(minioService2.uploadImageToMinio(requestDTO, imageFile)).thenReturn("imageFilename");
        when(minioService2.uploadImageToMinio(requestDTO, docFile)).thenReturn("docFilename");
        when(talentRepository.save(any(Talent.class))).thenReturn(talentList.get(0));

        String successMessage = "Success";
        when(messageSource.getMessage(eq("item.save.success"), any(), eq(Locale.getDefault()))).thenReturn(successMessage);

        ResponseEntity<AddTalentResponseDTO> response = talentService.addTalent(requestDTO, imageFile, docFile);

        assertEquals("Status: ", HttpStatus.OK, response.getStatusCode());
        assertEquals("Message: ", successMessage, response.getBody().getMessage());
    }

    @Test
    void testAddTalent_TalentStatusNotFound() {
        requestDTO.setTalentStatusId(null);
        requestDTO.setTalentLevelId(null);
        requestDTO.setEmployeeStatusId(null);
        requestDTO.setPosition(List.of(new PositionRequestDTO(null)));
        requestDTO.setSkillSet(List.of(new SkillsetRequestDTO(null)));

        when(messageSource.getMessage(any(), any(), any(Locale.class))).thenReturn("Internal Server Error");

        ResponseEntity<AddTalentResponseDTO> response = talentService.addTalent(requestDTO, imageFile, docFile);

        assertEquals("Status: ", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Message: ", "Internal Server Error", response.getBody().getMessage());
    }

    @Test
    void testEditTalent_Success() throws IOException {
        String successMessage = "Success";
        when(messageSource.getMessage(eq("item.update.success"), any(), eq(Locale.getDefault()))).thenReturn(successMessage);

        ResponseEntity<EditTalentResponseDTO> response = talentService.editTalent(requestEditDTO, imageFile, docFile);

        assertEquals("Status: ", HttpStatus.OK, response.getStatusCode());
        assertEquals("Message: ", successMessage, response.getBody().getMessage());
    }

    @Test
    void getTalentRequest_success() {
        // Setup data
        Specification<TalentRequest> talentRequestSpecification = TalentRequestSpesification.requestFilter(requestTalentRequest);
        Page<TalentRequest> talentRequestPage = new PageImpl<>(talentRequests, pageableTR, talentRequests.size());

        // Mock repository methods
        when(talentRequestRepository.findAll(any(Specification.class), eq(pageableTR))).thenReturn(talentRequestPage);

        // Call the method
        ResponseEntity<TalentRequestListResponse> response = talentService.getTalentRequest(requestTalentRequest, pageableTR);
        log.info("Response: {}", response);

        // Assert the response
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getMappingResponse().isEmpty());
        assertEquals("Status Code", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Total Data", talentRequests.size(), response.getBody().getTotalData());

        // Verify repository interactions
        verify(talentRequestRepository, times(1)).findAll(any(Specification.class), eq(pageableTR));
    }

    @Test
    void getTalentRequest_noData() {
        // Setup data
        Page<TalentRequest> emptyPage = new PageImpl<>(Collections.emptyList(), pageableTR, 0);

        // Mock repository methods
        when(talentRequestRepository.findAll(any(Specification.class), eq(pageableTR))).thenReturn(emptyPage);

        // Call the method
        ResponseEntity<TalentRequestListResponse> response = talentService.getTalentRequest(requestTalentRequest, pageableTR);

        // Assert the response
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMappingResponse().isEmpty());
        assertEquals("Status Code", HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Total Data", 0, response.getBody().getTotalData());

        // Verify repository interactions
        verify(talentRequestRepository, times(1)).findAll(any(Specification.class), eq(pageableTR));
    }

    @Test
    void getTalentRequest_systemError() {
        // Setup data
        Specification<TalentRequest> talentRequestSpecification = TalentRequestSpesification.requestFilter(requestTalentRequest);

        // Mock repository methods to throw an exception
        when(talentRequestRepository.findAll(eq(talentRequestSpecification), eq(pageableTR))).thenThrow(new RuntimeException("Database error"));

        // Call the method
        ResponseEntity<TalentRequestListResponse> response = talentService.getTalentRequest(requestTalentRequest, pageableTR);

        // Assert the response
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMappingResponse().isEmpty());
        assertEquals("Status Code", HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Total Data", 0, response.getBody().getTotalData());

        // Verify repository interactions
        verify(talentRequestRepository, times(1)).findAll(any(Specification.class), eq(pageableTR));
    }

    @Test
    void getTalentRequest_checkTotalDataFromAllData() {
        // Mock repository methods
        Page<TalentRequest> talentRequestPage = new PageImpl<>(talentRequests, pageableTR, talentRequests.size());

        // Mock repository methods
        when(talentRequestRepository.findAll(any(Specification.class), eq(pageableTR))).thenReturn(talentRequestPage);

        // Call the method
        ResponseEntity<TalentRequestListResponse> response = talentService.getTalentRequest(requestTalentRequest, pageableTR);
        log.info("Response: {}", response);

        // Assert the response
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getMappingResponse().isEmpty());
        assertEquals("Status Code", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("Total Data", talentRequests.size(), response.getBody().getTotalData());

        // Verify repository interactions
        verify(talentRequestRepository, times(1)).findAll(any(Specification.class), eq(pageableTR));
    }

    @Test
    void testPutApproveReject_ApproveSuccess() {

        TalentRequest talentRequest = new TalentRequest();
        when(talentRequestRepository.findById(talentRequestID)).thenReturn(Optional.of(talentRequest));
        when(messageSource.getMessage(eq("talent.approve.success"), any(), any(Locale.class))).thenReturn("Talent approved successfully");

        talentRequestRequestDTO.setAction("approve");

        ResponseEntity<TalentRequestResponse> response = talentService.approveRejectTalent(talentRequestRequestDTO);

        assertEquals("Status Code", HttpStatus.OK, response.getStatusCode());
        assertEquals("Status", HttpStatus.OK.name(), response.getBody().getStatus());
        assertEquals("Message", "Talent approved successfully", response.getBody().getMessage());
        verify(talentRequestRepository, times(1)).save(talentRequest);
    }

    @Test
    void testPutApproveReject_RejectSuccess() {

        TalentRequest talentRequest = new TalentRequest();
        when(talentRequestRepository.findById(talentRequestID)).thenReturn(Optional.of(talentRequest));
        when(messageSource.getMessage(eq("talent.reject.failed"), any(), any(Locale.class))).thenReturn("Talent rejected successfully");

        talentRequestRequestDTO.setAction("reject");
        talentRequestRequestDTO.setRejectReason("No Reason");

        ResponseEntity<TalentRequestResponse> response = talentService.approveRejectTalent(talentRequestRequestDTO);

        assertEquals("Status Code", HttpStatus.OK, response.getStatusCode());
        assertEquals("Status", HttpStatus.OK.name(), response.getBody().getStatus());
        assertEquals("Message", "Talent rejected successfully", response.getBody().getMessage());
        verify(talentRequestRepository, times(1)).save(talentRequest);
    }

    @Test
    void testPutApproveReject_Error() {

        TalentRequest talentRequest = new TalentRequest();
        when(talentRequestRepository.findById(talentRequestID)).thenReturn(Optional.of(talentRequest));
        when(messageSource.getMessage(eq("internal.error"), any(), any(Locale.class))).thenReturn("INTERNAL SERVER ERROR");

        ResponseEntity<TalentRequestResponse> response = talentService.approveRejectTalent(talentRequestRequestDTO);

        assertEquals("Status Code", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Message", "INTERNAL SERVER ERROR", response.getBody().getMessage());
        verify(talentRequestRepository, times(1)).save(talentRequests.get(0));
    }
}
