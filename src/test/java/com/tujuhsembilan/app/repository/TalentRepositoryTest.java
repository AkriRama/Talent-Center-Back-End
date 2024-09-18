package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TalentRepositoryTest {

    @Mock
    private TalentRepository talentRepository;

    private Talent talent;

    @BeforeEach
    void setUp() {
        talent = Talent.builder()
                .talentId(UUID.randomUUID())
                .talentName("John Doe")
                .talentPhotoFilename("photo.jpg")
                .employeeNumber("12345")
                .sex('L')
                .talentDesc("Saya adalah programmer")
                .talentCvFilename(("UploadCV"))
                .email("akbarM@gmail.com")
                .cellphone("08123456")
                .biographyVideoUrl("link")
                .totalProjectCompleted(0)
                .isAddToListEnable(true)
                .talentAvailability(true)
                .isActive(true)
                .talentStatus(TalentStatus.builder()
                        .talentStatusId(UUID.randomUUID())
                        .talentStatusName("Active").build())
                .employeeStatus(EmployeeStatus.builder()
                        .employeeStatusId(UUID.randomUUID())
                        .employeeStatusName("Full-time").build())
                .experience(5)
                .talentLevel(TalentLevel.builder()
                        .talentLevelId(UUID.randomUUID())
                        .talentLevelName("Senior").build())
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(talentRepository.findAll()).thenReturn(Arrays.asList(talent));

        // when
        List<Talent> foundTalents = talentRepository.findAll();


        // then
        assertThat(foundTalents).isNotEmpty();
        assertThat(foundTalents.size()).isEqualTo(1);
        assertThat(foundTalents.get(0).getTalentName()).isEqualTo("John Doe");
        assertThat(foundTalents.get(0).getTalentStatus().getTalentStatusName()).isEqualTo("Active");
    }
    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(talentRepository.save(talent)).thenReturn(talent);

        // When
        Talent savedTalent = talentRepository.save(talent);

        // Then
        assertThat(savedTalent).isNotNull();
        assertThat(savedTalent.getTalentId()).isEqualTo(talent.getTalentId());
    }
}
