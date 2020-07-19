package br.com.challenge.dextra.api.service;

import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface CharacterService {

    Page<Character> listCharacters(Pageable pageable);

    Page<Character> listCharactersByHouse(Pageable pageable, String filter);

    Character getById(String id);

    Character merge(String token,Character character) throws IOException, HouseNotFoundException;

    boolean deleteById(String id);
}
