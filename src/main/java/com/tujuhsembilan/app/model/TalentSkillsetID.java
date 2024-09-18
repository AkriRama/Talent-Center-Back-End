package com.tujuhsembilan.app.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TalentSkillsetID implements Serializable {
    private Talent talent;
    private Skillset skillset;
}
