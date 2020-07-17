package com.br.challenge.dextra.api.controller.form;

import com.br.challenge.dextra.api.model.Character;

public class CharacterForm implements Form<Character> {
    public Character converter() {
        return new Character(this.name, this.role, this.school, this.house, this.patronus);
    }

    private String name;
    private String role;
    private String school;
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
