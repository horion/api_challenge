package br.com.challenge.dextra.api.controller.form;

import br.com.challenge.dextra.api.model.Character;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CharacterForm implements Form<Character> {

    public Character converter() {
        return new Character(this.name, this.role, this.school, this.house, this.patronus);
    }

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String role;
    @NotNull
    @NotEmpty
    private String school;
    @NotNull
    @NotEmpty
    private String house;

    private String patronus;

    public Character update(Character characterDB) {
        characterDB.setName(this.name);
        characterDB.setRole(this.role);
        characterDB.setSchool(this.school);
        characterDB.setHouse(this.house);
        characterDB.setPatronus(this.patronus);
        return characterDB;
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
