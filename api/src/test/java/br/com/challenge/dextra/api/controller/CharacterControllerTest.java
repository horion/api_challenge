package br.com.challenge.dextra.api.controller;

import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.controller.form.CharacterForm;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.service.impl.CharacterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@DirtiesContext
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterServiceImpl characterService;


    @Test
    void listCharacters() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        Page<Character> page = new PageImpl<>(Collections.singletonList(character));

        given(characterService.listCharacters(any(Pageable.class))).willReturn(page);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters")).andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":\"12782617282\",\"name\":\"Harry Potter\",\"role\":\"student\",\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"house\":\"5a05e2b252f721a3cf2ea33f\",\"patronus\":\"stag\"}],\"pageable\":\"INSTANCE\",\"last\":true,\"totalPages\":1,\"totalElements\":1,\"first\":true,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"number\":0,\"numberOfElements\":1,\"size\":1,\"empty\":false}"));
    }

    @Test
    void listCharactersEmpty() throws Exception {


        Page<Character> page = new PageImpl<>(Collections.emptyList());

        given(characterService.listCharacters(any(Pageable.class))).willReturn(page);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters")).andExpect(status().isOk());
    }

    @Test
    void listCharactersByHouse() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        Page<Character> page = new PageImpl<>(Collections.singletonList(character));

        given(characterService.listCharactersByHouse(any(Pageable.class),eq("5a05e2b252f721a3cf2ea33f"))).willReturn(page);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters/?house=5a05e2b252f721a3cf2ea33f")).andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":\"12782617282\",\"name\":\"Harry Potter\",\"role\":\"student\",\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"house\":\"5a05e2b252f721a3cf2ea33f\",\"patronus\":\"stag\"}],\"pageable\":\"INSTANCE\",\"last\":true,\"totalPages\":1,\"totalElements\":1,\"first\":true,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"number\":0,\"numberOfElements\":1,\"size\":1,\"empty\":false}"));
    }

    @Test
    void create() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        CharacterForm characterForm = new CharacterForm();
        characterForm.setName("Harry Potter");
        characterForm.setRole("student");
        characterForm.setSchool("Hogwarts School of Witchcraft and Wizardry");
        characterForm.setPatronus("stag");
        characterForm.setHouse("5a05e2b252f721a3cf2ea33f");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(characterForm);

        given(characterService.validationHouse(eq("5a05e2b252f721a3cf2ea33f"),eq("871278ewdfu183y2881211332"))).willReturn(true);

        given(characterService.merge(eq("871278ewdfu183y2881211332"),any(Character.class))).willReturn(character);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/characters")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson).header("token","871278ewdfu183y2881211332"))
                .andDo(print()).andExpect(status().isCreated());

    }

    @Test
    void createHouseNotFound() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        CharacterForm characterForm = new CharacterForm();
        characterForm.setName("Harry Potter");
        characterForm.setRole("student");
        characterForm.setSchool("Hogwarts School of Witchcraft and Wizardry");
        characterForm.setPatronus("stag");
        characterForm.setHouse("5a05e2b252f721a3cf2ea33f");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(characterForm);

        given(characterService.validationHouse(eq("5a05e2b252f721a3cf2ea33f"),eq("871278ewdfu183y2881211332"))).willReturn(false);

        given(characterService.merge(eq("871278ewdfu183y2881211332"),any(Character.class))).willThrow(HouseNotFoundException.class);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/characters")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson).header("token","871278ewdfu183y2881211332"))
                .andDo(print()).andExpect(status().isBadRequest());

    }

    @Test
    void detail() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        given(characterService.getById(eq("12782617282"))).willReturn(character);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters/{id}","12782617282")
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("12782617282"));
    }

    @Test
    void detailNotFound() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        given(characterService.getById(eq("12782617282"))).willReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters/{id}","12782617282")
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        CharacterForm characterForm = new CharacterForm();
        characterForm.setName("Harry Potter");
        characterForm.setRole("student");
        characterForm.setSchool("Hogwarts School of Witchcraft and Wizardry");
        characterForm.setPatronus("stag");
        characterForm.setHouse("5a05e2b252f721a3cf2ea33f");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(characterForm);

        given(characterService.getById(eq("12782617282"))).willReturn(character);

        given(characterService.validationHouse(eq("5a05e2b252f721a3cf2ea33f"),eq("871278ewdfu183y2881211332"))).willReturn(true);

        given(characterService.merge(eq("871278ewdfu183y2881211332"),any(Character.class))).willReturn(character);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/characters/{id}","12782617282")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson).header("token","871278ewdfu183y2881211332"))
                .andDo(print()).andExpect(status().isOk());



    }


    @Test
    void updateNotFound() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        CharacterForm characterForm = new CharacterForm();
        characterForm.setName("Harry Potter");
        characterForm.setRole("student");
        characterForm.setSchool("Hogwarts School of Witchcraft and Wizardry");
        characterForm.setPatronus("stag");
        characterForm.setHouse("5a05e2b252f721a3cf2ea33f");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(characterForm);

        given(characterService.validationHouse(eq("5a05e2b252f721a3cf2ea33f"),eq("871278ewdfu183y2881211332"))).willReturn(true);

        given(characterService.merge(eq("871278ewdfu183y2881211332"),any(Character.class))).willReturn(character);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/characters/{id}","12782617282")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson).header("token","871278ewdfu183y2881211332"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void updateHouseNotFound() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("1212121212121");

        CharacterForm characterForm = new CharacterForm();
        characterForm.setName("Harry Potter");
        characterForm.setRole("student");
        characterForm.setSchool("Hogwarts School of Witchcraft and Wizardry");
        characterForm.setPatronus("stag");
        characterForm.setHouse("1212121212121");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(characterForm);

        given(characterService.getById(eq("12782617282"))).willReturn(character);

        given(characterService.validationHouse(eq("1212121212121"),eq("871278ewdfu183y2881211332"))).willReturn(false);

        given(characterService.merge(eq("871278ewdfu183y2881211332"),any(Character.class))).willThrow(HouseNotFoundException.class);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/characters/{id}","12782617282")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson).header("token","871278ewdfu183y2881211332"))
                .andDo(print()).andExpect(status().isBadRequest());



    }

    @Test
    void delete() throws Exception {

        Character character = new Character();
        character.setId("12782617282");
        character.setName("Harry Potter");
        character.setRole("student");
        character.setSchool("Hogwarts School of Witchcraft and Wizardry");
        character.setPatronus("stag");
        character.setHouse("5a05e2b252f721a3cf2ea33f");

        given(characterService.getById(eq("12782617282"))).willReturn(character);

        given(characterService.deleteById(eq("12782617282"))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/characters/{id}","12782617282"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {

        given(characterService.deleteById(eq("12782617282"))).willReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/characters/{id}","12782617282"))
                .andDo(print()).andExpect(status().isNotFound());
    }

}