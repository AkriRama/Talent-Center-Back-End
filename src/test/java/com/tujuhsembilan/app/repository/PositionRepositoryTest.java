package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Position;
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
public class PositionRepositoryTest {

    @Mock
    private PositionRepository positionRepository;

    private Position position, position2, position3, position4;

    @BeforeEach
    void setUp() {
        position = Position.builder()
                .positionId(UUID.randomUUID())
                .positionName("Web Front-End Developer")
                .build();
        position2 = Position.builder()
                .positionId(UUID.randomUUID())
                .positionName("Web Back-End Developer")
                .build();
        position3 = Position.builder()
                .positionId(UUID.randomUUID())
                .positionName("Project Manager")
                .build();
        position4 = Position.builder()
                .positionId(UUID.randomUUID())
                .positionName("Android Developer")
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(positionRepository.findAll()).thenReturn(Arrays.asList(position, position2, position3, position4));

        // when
        List<Position> foundPosition = positionRepository.findAll();


        // then
        assertThat(foundPosition).isNotEmpty();
        assertThat(foundPosition.size()).isEqualTo(4);
        assertThat(foundPosition.get(0).getPositionName()).isEqualTo("Web Front-End Developer");
        assertThat(foundPosition.get(3).getPositionName()).isEqualTo("Android Developer");
    }

    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(positionRepository.save(position)).thenReturn(position);

        // When
        Position savedPosition = positionRepository.save(position);

        // Then
        assertThat(savedPosition).isNotNull();
        assertThat(savedPosition.getPositionId()).isEqualTo(position.getPositionId());
        assertThat(savedPosition.getPositionName()).isEqualTo(position.getPositionName());
    }
}
