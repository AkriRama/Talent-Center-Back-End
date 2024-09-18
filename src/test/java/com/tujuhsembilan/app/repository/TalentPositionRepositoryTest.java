package com.tujuhsembilan.app.repository;


import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentPosition;
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
public class TalentPositionRepositoryTest {

    @Mock
    private TalentPositionRepository talentPositionRepository;

    private TalentPosition talentPosition, talentPosition2;

    @BeforeEach
    void setUp() {
        talentPosition = TalentPosition.builder()
                .talent(Talent.builder()
                        .talentId(UUID.randomUUID())
                        .talentName("John Doe")
                        .build())
                .position(Position.builder()
                        .positionId(UUID.randomUUID())
                        .positionName("Web Front-End Developer")
                        .build())
                .build();
        talentPosition2 = TalentPosition.builder()
                .talent(Talent.builder()
                        .talentId(UUID.randomUUID())
                        .talentName("John Doe")
                        .build())
                .position(Position.builder()
                        .positionId(UUID.randomUUID())
                        .positionName("Web Back-End Developer")
                        .build())
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(talentPositionRepository.findAll()).thenReturn(Arrays.asList(talentPosition, talentPosition2));

        // when
        List<TalentPosition> foundTalentPosition = talentPositionRepository.findAll();


        // then
        assertThat(foundTalentPosition).isNotEmpty();
        assertThat(foundTalentPosition.size()).isEqualTo(2);
        assertThat(foundTalentPosition.get(0).getTalent().getTalentName()).isEqualTo("John Doe");
        assertThat(foundTalentPosition.get(1).getPosition().getPositionName()).isEqualTo("Web Back-End Developer");
    }

    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(talentPositionRepository.save(talentPosition)).thenReturn(talentPosition);

        // When
        TalentPosition savedTalentPosition = talentPositionRepository.save(talentPosition);

        // Then
        assertThat(savedTalentPosition).isNotNull();
        assertThat(savedTalentPosition.getPosition().getPositionId()).isEqualTo(talentPosition.getPosition().getPositionId());
        assertThat(savedTalentPosition.getTalent().getTalentName()).isEqualTo(talentPosition.getTalent().getTalentName());
    }
}


