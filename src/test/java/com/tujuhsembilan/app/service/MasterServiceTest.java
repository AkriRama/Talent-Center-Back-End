package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MasterServiceTest {
    @Mock
    private TalentLevelRepository talentLevelRepository;

    @Mock
    private SkillsetRepository skillsetRepository;

    @Mock
    private EmployeeStatusRepository employeeStatusRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private TalentStatusRepository talentStatusRepository;


    @InjectMocks
    private MasterService masterService;

    @Test
    public void testGetTalentLevels() {
        TalentLevel talentLevel1 = new TalentLevel();
        talentLevel1.setTalentLevelId(UUID.randomUUID());
        talentLevel1.setTalentLevelName("Senior");

        TalentLevel talentLevel2 = new TalentLevel();
        talentLevel2.setTalentLevelId(UUID.randomUUID());
        talentLevel2.setTalentLevelName("Middle");

        TalentLevel talentLevel3 = new TalentLevel();
        talentLevel3.setTalentLevelId(UUID.randomUUID());
        talentLevel3.setTalentLevelName("Junior");

        when(talentLevelRepository.findAll()).thenReturn(Arrays.asList(talentLevel1, talentLevel2, talentLevel3));

        ResponseEntity<TalentLevelResponse> response = masterService.getTalentLevel();
        System.out.println("Response TalentLevel: " + response);

        assertEquals("200", 3, response.getBody().getMappingParameter());
    }

    @Test
    public void testGetEmployeeStatus() {
        EmployeeStatus employeeStatus1 = new EmployeeStatus();
        employeeStatus1.setEmployeeStatusId(UUID.randomUUID());
        employeeStatus1.setEmployeeStatusName("Active");

        EmployeeStatus employeeStatus2 = new EmployeeStatus();
        employeeStatus2.setEmployeeStatusId(UUID.randomUUID());
        employeeStatus2.setEmployeeStatusName("Not Active");

        when(employeeStatusRepository.findAll()).thenReturn(Arrays.asList(employeeStatus1, employeeStatus2));

        ResponseEntity<EmployeeStatusResponse> response = masterService.getEmployeeStatus();
        System.out.println("Response Employee Status: " + response);

        assertEquals("200", 2, response.getBody().getMappingParameter());
    }

    @Test
    public void testGetSkillsets() {
        Skillset skillset1 = new Skillset();
        skillset1.setSkillsetId(UUID.randomUUID());
        skillset1.setSkillsetName("Java");

        Skillset skillset2 = new Skillset();
        skillset2.setSkillsetId(UUID.randomUUID());
        skillset2.setSkillsetName("C++");

        Skillset skillset3 = new Skillset();
        skillset3.setSkillsetId(UUID.randomUUID());
        skillset3.setSkillsetName("Python");

        Skillset skillset4 = new Skillset();
        skillset4.setSkillsetId(UUID.randomUUID());
        skillset4.setSkillsetName("C#");


        when(skillsetRepository.findAll()).thenReturn(Arrays.asList(skillset1, skillset2, skillset3, skillset4));


        ResponseEntity<SkillsetResponse> response = masterService.getSkillset();
        System.out.println("Response Skillset: " + response);

        assertEquals("Total Data", 4, response.getBody().getMappingParameter());
        assertEquals("Status Code", HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("skillName", "Java", response.getBody().getMappingResponse().get(0).getSkillName());
        assertNotNull("Test Data Not Null", response);
        assertNotNull("Test Data Not Null V2 by Body", response.getBody());

    }

    @Test
    public void testGetPositions() {
        Position position1 = new Position();
        position1.setPositionId(UUID.randomUUID());
        position1.setPositionName("Developer");

        Position position2 = new Position();
        position2.setPositionId(UUID.randomUUID());
        position2.setPositionName("Designer");

        when(positionRepository.findAll()).thenReturn(Arrays.asList(position1, position2));


        ResponseEntity<PositionResponse> response = masterService.getPosition();
        System.out.println("Response Position: " + response);

        assertEquals("200", 2, response.getBody().getMappingParameter());
    }

    @Test
    public  void testGetTalentStatus() {
        TalentStatus talentStatus1 = new TalentStatus();
        talentStatus1.setTalentStatusId(UUID.randomUUID());
        talentStatus1.setTalentStatusName("Onsite");

        TalentStatus talentStatus2 = new TalentStatus();
        talentStatus2.setTalentStatusId(UUID.randomUUID());
        talentStatus2.setTalentStatusName("Not Onsite");

        when(talentStatusRepository.findAll()).thenReturn(Arrays.asList(talentStatus1, talentStatus2));

        ResponseEntity<TalentStatusResponse> response = masterService.getTalentStatus();
        System.out.println("Response Talent Status: " + response);

        assertEquals("200", 2, response.getBody().getMappingParameter());
    }
}
