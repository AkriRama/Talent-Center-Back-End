package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Skillset;
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
public class SkillsetRepositoryTest {

    @Mock
    private SkillsetRepository skillsetRepository;

    private Skillset skillset, skillset2, skillset3, skillset4, skillset5, skillset6;

    @BeforeEach
    void setUp() {
        skillset = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("Java")
                .build();
        skillset2 = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("C")
                .build();
        skillset3 = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("PHP")
                .build();
        skillset4 = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("Laravel")
                .build();
        skillset5 = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("Golang")
                .build();
        skillset6 = Skillset.builder()
                .skillsetId(UUID.randomUUID())
                .skillsetName("Spring Boot")
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(skillsetRepository.findAll()).thenReturn(Arrays.asList(skillset, skillset2, skillset3, skillset4, skillset5, skillset6));

        // when
        List<Skillset> foundSkillset = skillsetRepository.findAll();


        // then
        assertThat(foundSkillset).isNotEmpty();
        assertThat(foundSkillset.size()).isEqualTo(6);
        assertThat(foundSkillset.get(0).getSkillsetName()).isEqualTo("Java");
        assertThat(foundSkillset.get(2).getSkillsetName()).isEqualTo("PHP");
        assertThat(foundSkillset.get(3).getSkillsetName()).isEqualTo("Laravel");
        assertThat(foundSkillset.get(5).getSkillsetName()).isEqualTo("Spring Boot");
    }

    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(skillsetRepository.save(skillset)).thenReturn(skillset);

        // When
        Skillset savedSkillset = skillsetRepository.save(skillset);

        // Then
        assertThat(savedSkillset).isNotNull();
        assertThat(savedSkillset.getSkillsetId()).isEqualTo(skillset.getSkillsetId());
        assertThat(savedSkillset.getSkillsetName()).isEqualTo(skillset.getSkillsetName());
    }
}

