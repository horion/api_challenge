package br.com.challenge.dextra.api.service.impl;

import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.repository.CharacterRepository;
import br.com.challenge.dextra.api.service.CharacterService;
import br.com.challenge.dextra.api.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

@Service
public class CharacterServiceImpl implements CharacterService {

    public static final String HOUSES = "/houses/";
    public static final String KEY = "/?key=";
    public static final String ID = "_id";
    private final RestTemplate template;
    @Value("${harry.potter.server}")
    private String server;
    private final CharacterRepository characterRepository;


    public CharacterServiceImpl(RestTemplate template, CharacterRepository characterRepository) {
        this.template = template;
        this.characterRepository = characterRepository;
    }

    @Override
    public Page<Character> listCharacters(Pageable pageable) {
        return characterRepository.findAll(pageable);
    }

    @Override
    public Page<Character> listCharactersByHouse(Pageable pageable, String house) {
        return characterRepository.findAllByHouse(pageable,house);
    }

    @Override
    public Character getById(String id) {
        return characterRepository.findById(id).orElse(null);
    }

    @Override
    public Character merge(String token,Character character) throws IOException, HouseNotFoundException {
        if(!validationHouse(character.getHouse(),token)){
            throw new HouseNotFoundException();
        }
        return characterRepository.save(character);
    }

    private boolean validationHouse(String houseId,String token) throws IOException {
        String endpoint = server.concat(HOUSES).concat(houseId).concat(KEY).concat(token);
        ResponseEntity<String> responseEntity = template.exchange(endpoint, HttpMethod.GET, null, String.class);
        String body =  responseEntity.getBody();
        JsonNode jsonNode = Util.getJson(body);
        return jsonNode.get(0) != null && jsonNode.get(0).get(ID) != null;
    }

    @Override
    public void deleteById(String id) {
        characterRepository.deleteById(id);
    }
}
