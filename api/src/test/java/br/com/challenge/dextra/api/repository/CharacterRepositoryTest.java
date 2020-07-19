package br.com.challenge.dextra.api.repository;

import br.com.challenge.dextra.api.model.Character;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@TestPropertySource("classpath:application.properties")
class CharacterRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CharacterRepository characterRepository;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().getCollection("character").drop();
    }

    @Test
    void findAll() {
        String houseId = "5a05e2b252f721a3cf2ea33f";
        Character character = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", houseId,"stag");

        mongoTemplate.insert(character);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> characterList = characterRepository.findAll(pageable);
        assertEquals(1,characterList.getTotalElements());
    }

    @Test
    void findAllEmpty() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> characterList = characterRepository.findAll(pageable);
        assertEquals(0,characterList.getTotalElements());
    }

    @Test
    void findAllByHouse() {
        String gryffindor = "5a05e2b252f721a3cf2ea33f";
        Character character1 = new Character("Harry Potter","student",
                "Hogwarts School of Witchcraft and Wizardry", gryffindor,"stag");


        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character2 = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");


        mongoTemplate.insert(character1);
        mongoTemplate.insert(character2);


        Pageable pageable = PageRequest.of(0, 10);

        Page<Character> characterList = characterRepository.findAllByHouse(pageable,gryffindor);
        assertEquals(1,characterList.getTotalElements());
    }

    @Test
    void findAllByOtherHouseId() {
        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");

        mongoTemplate.insert(character);

        Pageable pageable = PageRequest.of(0, 10);

        String gryffindor = "5a05e2b252f721a3cf2ea33f";

        Page<Character> characterList = characterRepository.findAllByHouse(pageable, gryffindor);
        assertEquals(0,characterList.getTotalElements());
    }

    @Test
    void findById(){
        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");

        mongoTemplate.insert(character);

        Character characterDB = characterRepository.findById(character.getId()).orElse(null);

        assertNotNull(characterDB);
    }

    @Test
    void findByOtherId(){
        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");

        mongoTemplate.insert(character);

        Character characterDB = characterRepository.findById("76327832781836hd").orElse(null);

        assertNull(characterDB);
    }

    @Test
    void merge(){
        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");

        Character characterDB = characterRepository.save(character);

        assertNotNull(characterDB.getId());
    }

    @Test
    void deleteById(){
        String slytherin = "5a05dc8cd45bd0a11bd5e071";
        Character character = new Character("Severo Snape","student",
                "Hogwarts School of Witchcraft and Wizardry", slytherin,"stag");

        mongoTemplate.insert(character);

        characterRepository.deleteById(character.getId());

        Character characterDB = characterRepository.findById(character.getId()).orElse(null);

        assertNull(characterDB);
    }
}