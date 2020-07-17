package com.br.challenge.dextra.api.controller.dto;

import com.br.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public class CharacterDTO implements Serializable {

    private static final long serialVersionUID = -6636737612641449445L;
    private Long id;
    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;

    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.role = character.getRole();
        this.school = character.getSchool();
        this.house = character.getHouse();
        this.patronus = character.getPatronus();
    }

    public static Page<CharacterDTO> converter(Page<Character> characterPage) {
        return characterPage.map(CharacterDTO::new);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }
}
