package br.com.challenge.dextra.api.service.impl;

import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.properties.Properties;
import br.com.challenge.dextra.api.repository.CharacterRepository;
import br.com.challenge.dextra.api.service.CharacterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
class CharacterServiceImplTest {

    private CharacterRepository characterRepository;
    private CharacterService characterService;
    private RestTemplate restTemplate;
    private Properties properties;
    private final String returnApi = "[{\"_id\":\"5a05e2b252f721a3cf2ea33f\",\"name\":\"Gryffindor\",\"mascot\":\"lion\",\"headOfHouse\":\"Minerva McGonagall\",\"houseGhost\":\"Nearly Headless Nick\",\"founder\":\"Goderic Gryffindor\",\"__v\":0,\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"members\":[{\"_id\":\"5a0fa648ae5bc100213c2332\",\"name\":\"Katie Bell\"},{\"_id\":\"5a0fa67dae5bc100213c2333\",\"name\":\"Cuthbert Binns\"},{\"_id\":\"5a0fa7dcae5bc100213c2338\",\"name\":\"Sirius Black\"},{\"_id\":\"5a107e1ae0686c0021283b19\",\"name\":\"Lavender Brown\"},{\"_id\":\"5a10944f3dc2080021cd8755\",\"name\":\"Colin Creevey\"},{\"_id\":\"5a10947c3dc2080021cd8756\",\"name\":\"Dennis Creevey\"},{\"_id\":\"5a1096b33dc2080021cd8762\",\"name\":\"Aberforth Dumbledore\"},{\"_id\":\"5a1097653dc2080021cd8763\",\"name\":\"Albus Dumbledore\"},{\"_id\":\"5a1098fd3dc2080021cd876e\",\"name\":\"Seamus Finnigan\"},{\"_id\":\"5a109af13dc2080021cd877a\",\"name\":\"Hermione Granger\"},{\"_id\":\"5a109bfc3dc2080021cd877f\",\"name\":\"Godric Gryffindor\"},{\"_id\":\"5a109c3d3dc2080021cd8780\",\"name\":\"Rubeus Hagrid\"},{\"_id\":\"5a109cb83dc2080021cd8784\",\"name\":\"Angelina Johnson\"},{\"_id\":\"5a109cd33dc2080021cd8785\",\"name\":\"Lee Jordan\"},{\"_id\":\"5a109e143dc2080021cd878d\",\"name\":\"Alice Longbottom\"},{\"_id\":\"5a109e1e3dc2080021cd878e\",\"name\":\"Frank Longbottom\"},{\"_id\":\"5a109e253dc2080021cd878f\",\"name\":\"Augusta Longbottom\"},{\"_id\":\"5a109e543dc2080021cd8790\",\"name\":\"Neville Longbottom\"},{\"_id\":\"5a109f053dc2080021cd8793\",\"name\":\"Remus Lupin\"},{\"_id\":\"5a1226520f5ae10021650d76\",\"name\":\"Parvati Patil\"},{\"_id\":\"5a1226d70f5ae10021650d77\",\"name\":\"Peter Pettigrew\"},{\"_id\":\"5a12292a0f5ae10021650d7e\",\"name\":\"Harry Potter\"},{\"_id\":\"5a12298d0f5ae10021650d7f\",\"name\":\"James Potter I\"},{\"_id\":\"5a1229e10f5ae10021650d80\",\"name\":\"Lily J. Potter\"},{\"_id\":\"5a122cbe0f5ae10021650d89\",\"name\":\"Demelza Robins\"},{\"_id\":\"5a1233ff0f5ae10021650d98\",\"name\":\"Alicia Spinnet\"},{\"_id\":\"5a1234cb0f5ae10021650d9b\",\"name\":\"Dean Thomas\"},{\"_id\":\"5a1237480f5ae10021650da3\",\"name\":\"Romilda Vane\"},{\"_id\":\"5a1237c00f5ae10021650da5\",\"name\":\"Arthur Weasley\"},{\"_id\":\"5a1238070f5ae10021650da6\",\"name\":\"William Weasley\"},{\"_id\":\"5a1238350f5ae10021650da7\",\"name\":\"Charles Weasley\"},{\"_id\":\"5a12387a0f5ae10021650da8\",\"name\":\"Fred Weasley\"},{\"_id\":\"5a1238b20f5ae10021650da9\",\"name\":\"George Weasley\"},{\"_id\":\"5a1239130f5ae10021650daa\",\"name\":\"Ginevra Weasley\"},{\"_id\":\"5a12393d0f5ae10021650dab\",\"name\":\"Molly Weasley\"},{\"_id\":\"5a12395f0f5ae10021650dac\",\"name\":\"Percy Weasley\"},{\"_id\":\"5a1239c80f5ae10021650dad\",\"name\":\"Ronald Weasley\"},{\"_id\":\"5a1239f10f5ae10021650dae\",\"name\":\"Oliver Wood\"},{\"_id\":\"5a123b450f5ae10021650db7\",\"name\":\"Cadogan\"},{\"_id\":\"5a123f130f5ae10021650dcc\",\"name\":\"Nicholas de Mimsy-Porpington\"}],\"values\":[\"courage\",\"bravery\",\"nerve\",\"chivalry\"],\"colors\":[\"scarlet\",\"gold\"]}]";
    private final String returnErrorApi = "{\"message\":\"Cast to ObjectId failed for value \\\"5a05e2b2522f721a3cf2ea33f\\\" at path \\\"_id\\\" for model \\\"Houses\\\"\",\"name\":\"CastError\",\"stringValue\":\"\\\"5a05e2b2522f721a3cf2ea33f\\\"\",\"kind\":\"ObjectId\",\"value\":\"5a05e2b2522f721a3cf2ea33f\",\"path\":\"_id\"}";


    @BeforeEach
    void setUp() {
        characterRepository = Mockito.mock(CharacterRepository.class);
        restTemplate = Mockito.mock(RestTemplate.class);
        properties = Mockito.mock(Properties.class);
        characterService = new CharacterServiceImpl(restTemplate, properties, characterRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listCharacters() {
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry","5a05e2b252f721a3cf2ea33f","stag");
        character.setId("2813751298093rfgidhks");

        Page<Character> page = new PageImpl<>(Collections.singletonList(character));

        when(characterRepository.findAll(any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> all = characterService.listCharacters(pageable);
        assertEquals(1,all.getTotalElements());
    }


    @Test
    void listCharactersEmpty() {
        Page<Character> page = new PageImpl<>(Collections.emptyList());
        when(characterRepository.findAll(any(Pageable.class))).thenReturn(page);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Character> all = characterService.listCharacters(pageable);
        assertEquals(0,all.getTotalElements());
    }



    @Test
    void listCharactersByHouse() {
        String houseId = "5a05e2b252f721a3cf2ea33f";
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", houseId,"stag");
        character.setId("2813751298093rfgidhks");

        Page<Character> page = new PageImpl<>(Collections.singletonList(character));

        when(characterRepository.findAllByHouse(any(Pageable.class),eq(houseId))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> all = characterService.listCharactersByHouse(pageable,houseId);

        assertEquals(1,all.getTotalElements());
    }


    @Test
    void listCharactersByHouseIdNotFound() {
        String houseId = "NAO_EXISTE_ESSE_ID";

        Page<Character> page = new PageImpl<>(Collections.emptyList());

        when(characterRepository.findAllByHouse(any(Pageable.class),eq(houseId))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> all = characterService.listCharactersByHouse(pageable,houseId);

        assertEquals(0,all.getTotalElements());
    }

    @Test
    void getById() {
        String houseId = "5a05e2b252f721a3cf2ea33f";
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", houseId,"stag");
        character.setId("2813751298093rfgidhks");

        when(characterRepository.findById(eq("2813751298093rfgidhks"))).thenReturn(java.util.Optional.of(character));

        Character characterDB = characterService.getById("2813751298093rfgidhks");

        assertEquals(character,characterDB);
    }

    @Test
    void getByIdNotFound() {
        when(characterRepository.findById(eq("NAO_EXISTE_ESSE_ID"))).thenReturn(Optional.empty());

        Character characterDB = characterService.getById("NAO_EXISTE_ESSE_ID");

        assertNull(characterDB);
    }

    @Test
    void merge() throws Exception {
        String houseId = "5a05e2b252f721a3cf2ea33f";
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", houseId,"stag");

        when(characterRepository.save(any(Character.class))).thenReturn(character);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(returnApi,HttpStatus.OK);

        when(properties.getServer()).thenReturn("https://www.potterapi.com/v1");

        when(restTemplate.exchange(eq("https://www.potterapi.com/v1/houses/5a05e2b252f721a3cf2ea33f/?key=17wwwwdgy7qw5e2qe2q1221")
                , eq(HttpMethod.GET),eq(null),eq(String.class))).thenReturn(responseEntity);


        Character characterDB = characterService.merge("17wwwwdgy7qw5e2qe2q1221",character);

        assertNotNull(characterDB);
    }

    @Test
    void mergeFailed() throws Exception {
        String houseId = "5a05e2b252f721a3cf2ea33f";
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", houseId,"stag");
        character.setId("2813751298093rfgidhks");

        when(characterRepository.save(character)).thenReturn(character);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(returnErrorApi,HttpStatus.OK);

        when(properties.getServer()).thenReturn("https://www.potterapi.com/v1");

        when(restTemplate.exchange(eq("https://www.potterapi.com/v1/houses/5a05e2b252f721a3cf2ea33f/?key=17132uaedguqwiasgidgasui")
                , eq(HttpMethod.GET),eq(null),eq(String.class))).thenReturn(responseEntity);

        assertThrows(HouseNotFoundException.class, () -> characterService.merge("17132uaedguqwiasgidgasui",character));
    }

    @Test
    void deleteById() {

        characterService.deleteById("2813751298093rfgidhks");

        verify(characterRepository,times(1)).deleteById(eq("2813751298093rfgidhks"));

    }
}