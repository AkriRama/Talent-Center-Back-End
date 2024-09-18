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
public class TalentSkillsetRepositoryTest {

    @Mock
    private TalentSkillsetRepository talentSkillsetRepository;

    private TalentSkillset talentSkillset, talentSkillset2;

    @BeforeEach
    void setUp() {
        talentSkillset = TalentSkillset.builder()
                .talent(Talent.builder()
                        .talentId(UUID.randomUUID())
                        .talentName("John Doe")
                        .build())
                .skillset(Skillset.builder()
                        .skillsetId(UUID.randomUUID())
                        .skillsetName("Java")
                        .build())
                .build();
        talentSkillset2 = TalentSkillset.builder()
                .talent(Talent.builder()
                        .talentId(UUID.randomUUID())
                        .talentName("John Doe")
                        .build())
                .skillset(Skillset.builder()
                        .skillsetId(UUID.randomUUID())
                        .skillsetName("C")
                        .build())
                .build();

    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(talentSkillsetRepository.findAll()).thenReturn(Arrays.asList(talentSkillset, talentSkillset2));

        // when
        List<TalentSkillset> foundTalentSkillset = talentSkillsetRepository.findAll();


        // then
        assertThat(foundTalentSkillset).isNotEmpty();
        assertThat(foundTalentSkillset.size()).isEqualTo(2);
        assertThat(foundTalentSkillset.get(0).getTalent().getTalentName()).isEqualTo("John Doe");
        assertThat(foundTalentSkillset.get(1).getSkillset().getSkillsetName()).isEqualTo("C");
    }

    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(talentSkillsetRepository.save(talentSkillset)).thenReturn(talentSkillset);

        // When
        TalentSkillset savedTalentSkillset = talentSkillsetRepository.save(talentSkillset);

        // Then
        assertThat(savedTalentSkillset).isNotNull();
        assertThat(savedTalentSkillset.getSkillset().getSkillsetId()).isEqualTo(talentSkillset.getSkillset().getSkillsetId());
        assertThat(savedTalentSkillset.getTalent().getTalentName()).isEqualTo(talentSkillset.getTalent().getTalentName());
    }
}


