package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentLevel;
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
public class TalentLevelRepositoryTest {

    @Mock
    private TalentLevelRepository talentLevelRepository;

    private TalentLevel talentLevel, talentLevel2, talentLevel3;

    @BeforeEach
    void setUp() {
        talentLevel = TalentLevel.builder()
                .talentLevelId(UUID.randomUUID())
                .talentLevelName("Senior")
                .isActive(true)
                .build();
        talentLevel2 = TalentLevel.builder()
                .talentLevelId(UUID.randomUUID())
                .talentLevelName("Middle")
                .isActive(true)
                .build();
        talentLevel3 = TalentLevel.builder()
                .talentLevelId(UUID.randomUUID())
                .talentLevelName("Junior")
                .isActive(true)
                .build();

    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(talentLevelRepository.findAll()).thenReturn(Arrays.asList(talentLevel, talentLevel2, talentLevel3));

        // when
        List<TalentLevel> foundTalentLevel = talentLevelRepository.findAll();


        // then
        assertThat(foundTalentLevel).isNotEmpty();
        assertThat(foundTalentLevel.size()).isEqualTo(3);
        assertThat(foundTalentLevel.get(0).getTalentLevelName()).isEqualTo("Senior");
        assertThat(foundTalentLevel.get(2).getTalentLevelName()).isEqualTo("Junior");
    }
    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(talentLevelRepository.save(talentLevel)).thenReturn(talentLevel);

        // When
        TalentLevel savedTalentLevel = talentLevelRepository.save(talentLevel);

        // Then
        assertThat(savedTalentLevel).isNotNull();
        assertThat(savedTalentLevel.getTalentLevelId()).isEqualTo(talentLevel.getTalentLevelId());
        assertThat(savedTalentLevel.getTalentLevelName()).isEqualTo(talentLevel.getTalentLevelName());
    }
}

