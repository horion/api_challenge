package br.com.challenge.dextra.api.controller.dto;

import br.com.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;

/**
 * A classe CharacterDTO, é uma classe de transporte. Seu papel é no retorno de informações da API
 */
public class CharacterDTO extends Response {

    private static final long serialVersionUID = -6636737612641449445L;
    private String id;
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

    public CharacterDTO() {
    }

    /**
     * O método converter, recebe um Page<Character> e converte para o objeto de transporte
     * @param characterPage
     * @return Page<Response>
     */
    public static Page<Response> converter(Page<Character> characterPage) {
        return characterPage.map(CharacterDTO::new);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
