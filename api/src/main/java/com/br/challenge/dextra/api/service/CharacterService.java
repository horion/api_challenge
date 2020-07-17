package com.br.challenge.dextra.api.service;

import com.br.challenge.dextra.api.controller.exception.HouseNotFoundException;
import com.br.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface CharacterService {

    Page<Character> listCharacters(Pageable pageable);

    Page<Character> listCharactersByHouse(Pageable pageable, String filter);

    Character getById(Long id);

    Character merge(String token,Character character) throws IOException, HouseNotFoundException;

    void deleteById(Long id);
}
