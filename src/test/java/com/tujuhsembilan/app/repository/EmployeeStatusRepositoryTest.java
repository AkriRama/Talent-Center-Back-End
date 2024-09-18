package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.EmployeeStatus;
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
public class EmployeeStatusRepositoryTest {

    @Mock
    private EmployeeStatusRepository employeeStatusRepository;

    private EmployeeStatus employeeStatus, employeeStatus2;

    @BeforeEach
    void setUp() {
        employeeStatus = EmployeeStatus.builder()
                .employeeStatusId(UUID.randomUUID())
                .employeeStatusName("Active")
                .build();
        employeeStatus2 = EmployeeStatus.builder()
                .employeeStatusId(UUID.randomUUID())
                .employeeStatusName("Not Active")
                .build();
    }

    @Test
    void whenFindAll_thenReturnTalentList() {
        when(employeeStatusRepository.findAll()).thenReturn(Arrays.asList(employeeStatus, employeeStatus2));

        // when
        List<EmployeeStatus> foundEmployeeStatus = employeeStatusRepository.findAll();


        // then
        assertThat(foundEmployeeStatus).isNotEmpty();
        assertThat(foundEmployeeStatus.size()).isEqualTo(2);
        assertThat(foundEmployeeStatus.get(0).getEmployeeStatusName()).isEqualTo("Active");
        assertThat(foundEmployeeStatus.get(1).getEmployeeStatusName()).isEqualTo("Not Active");
    }
    @Test
    void whenSave_thenTalentIsSaved() {
        // Mock the repository's behavior
        when(employeeStatusRepository.save(employeeStatus)).thenReturn(employeeStatus);

        // When
        EmployeeStatus savedEmployeeStatus = employeeStatusRepository.save(employeeStatus);

        // Then
        assertThat(savedEmployeeStatus).isNotNull();
        assertThat(savedEmployeeStatus.getEmployeeStatusId()).isEqualTo(employeeStatus.getEmployeeStatusId());
        assertThat(savedEmployeeStatus.getEmployeeStatusName()).isEqualTo(employeeStatus.getEmployeeStatusName());
    }
}


