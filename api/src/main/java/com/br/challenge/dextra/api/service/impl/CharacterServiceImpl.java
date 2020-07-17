package com.br.challenge.dextra.api.service.impl;

import com.br.challenge.dextra.api.controller.exception.HouseNotFoundException;
import com.br.challenge.dextra.api.controller.form.CharacterForm;
import com.br.challenge.dextra.api.model.Character;
import com.br.challenge.dextra.api.repository.CharacterRepository;
import com.br.challenge.dextra.api.service.CharacterService;
import com.br.challenge.dextra.api.util.Util;
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
    public Character getById(Long id) {
        return characterRepository.findById(id).orElse(null);
    }

    @Override
    public Character merge(String token,Character character) throws IOException, HouseNotFoundException {
        if(validationHouse(character.getHouse(),token)){
            throw new HouseNotFoundException();
        }
        return characterRepository.save(character);
    }

    private boolean validationHouse(String houseId,String token) throws IOException {
        String endpoint = server.concat(HOUSES).concat(houseId).concat(KEY).concat(token);
        ResponseEntity<String> responseEntity = template.exchange(endpoint, HttpMethod.GET, null, String.class);
        String body =  responseEntity.getBody();
        HashMap<String,Object> map = Util.getMapJson(body);
        return map.get(ID) != null;
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }
}
