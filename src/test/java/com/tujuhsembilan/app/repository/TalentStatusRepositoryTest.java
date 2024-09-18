package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentStatus;
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
public class TalentStatusRepositoryTest {

    @Mock
    private TalentStatusRepository talentStatusRepository;

    private TalentStatus talentStatus, talentStatus2;

    @BeforeEach
    void setUp() {
        talentStatus = TalentStatus.builder()
                .talentStatusId(UUID.randomUUID())
                .talentStatusName("Onsite")
                .isActive(true)
                .build();

        talentStatus2 = TalentStatus.builder()
                .talentStatusId(UUID.randomUUID())
                .talentStatusName("Not Onsite")
                .isActive(true)
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(talentStatusRepository.findAll()).thenReturn(Arrays.asList(talentStatus, talentStatus2));

        // when
        List<TalentStatus> foundTalentStatus = talentStatusRepository.findAll();


        // then
        assertThat(foundTalentStatus).isNotEmpty();
        assertThat(foundTalentStatus.size()).isEqualTo(2);
        assertThat(foundTalentStatus.get(0).getTalentStatusName()).isEqualTo("Onsite");
    }
    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(talentStatusRepository.save(talentStatus)).thenReturn(talentStatus);

        // When
        TalentStatus savedTalentStatus = talentStatusRepository.save(talentStatus);

        // Then
        assertThat(savedTalentStatus).isNotNull();
        assertThat(savedTalentStatus.getTalentStatusId()).isEqualTo(talentStatus.getTalentStatusId());
    }
}
