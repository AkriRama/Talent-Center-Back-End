package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TalentRequestRepositoryTest {

    @Mock
    private TalentRequestRepository talentRequestRepository;

    private TalentRequest talentRequest, talentRequest2;

    @BeforeEach
    void setUp() {
        talentRequest = TalentRequest.builder()
                .talentRequestId(UUID.randomUUID())
                .talentRequestStatus(TalentRequestStatus.builder()
                        .talentRequestStatusId(UUID.randomUUID())
                        .talentRequestStatusName("Approved")
                        .build())
                .talentWishlist(TalentWishlist.builder()
                        .talentWishlistId(UUID.randomUUID())
                        .talent(Talent.builder()
                                .talentId(UUID.randomUUID())
                                .talentName("John Doe")
                                .build())
                        .client(Client.builder()
                                .clientId(UUID.randomUUID())
                                .agencyName("Nemesis")
                                .build())
                        .build())
                .build();
        talentRequest2 = TalentRequest.builder()
                .talentRequestId(UUID.randomUUID())
                .talentRequestStatus(TalentRequestStatus.builder()
                        .talentRequestStatusId(UUID.randomUUID())
                        .talentRequestStatusName("Rejected")
                        .build())
                .talentWishlist(TalentWishlist.builder()
                        .talentWishlistId(UUID.randomUUID())
                        .talent(Talent.builder()
                                .talentId(UUID.randomUUID())
                                .talentName("Jonathan")
                                .build())
                        .client(Client.builder()
                                .clientId(UUID.randomUUID())
                                .agencyName("Nemesis")
                                .build())
                        .build())
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentRequestList() {
        when(talentRequestRepository.findAll()).thenReturn(Arrays.asList(talentRequest, talentRequest2));

        // when
        List<TalentRequest> foundTalentRequest = talentRequestRepository.findAll();


        // then
        assertThat(foundTalentRequest).isNotEmpty();
        assertThat(foundTalentRequest.size()).isEqualTo(2);
        assertThat(foundTalentRequest.get(0).getTalentRequestStatus().getTalentRequestStatusName()).isEqualTo("Approved");
        assertThat(foundTalentRequest.get(1).getTalentRequestStatus().getTalentRequestStatusName()).isEqualTo("Rejected");
    }
    @Test
    void whenSave_thenTalentRequestIsSaved() {
        // Mock the repository's behavior
        when(talentRequestRepository.save(talentRequest)).thenReturn(talentRequest);

        // When
        TalentRequest savedTalentRequest = talentRequestRepository.save(talentRequest);

        // Then
        assertThat(savedTalentRequest).isNotNull();
        assertThat(savedTalentRequest.getTalentRequestId()).isEqualTo(talentRequest.getTalentRequestId());
        assertThat(savedTalentRequest.getTalentRequestStatus().getTalentRequestStatusName()).isEqualTo(talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
    }
}


