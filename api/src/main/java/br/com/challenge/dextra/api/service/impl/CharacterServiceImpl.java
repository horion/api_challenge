package br.com.challenge.dextra.api.service.impl;

import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.properties.Properties;
import br.com.challenge.dextra.api.repository.CharacterRepository;
import br.com.challenge.dextra.api.service.CharacterService;
import br.com.challenge.dextra.api.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * A classe CharacterServiceImpl, é um Bean do Spring, do tipo Service, responsável por encapsular a lógica de negócio
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    private static final String HOUSES = "/houses/";
    private static final String KEY = "/?key=";
    private static final String ID = "_id";
    private final RestTemplate template;
    private final Properties properties;
    private final CharacterRepository characterRepository;


    public CharacterServiceImpl(RestTemplate template, Properties properties, CharacterRepository characterRepository) {
        this.template = template;
        this.properties = properties;
        this.characterRepository = characterRepository;
    }

    /**
     * @param pageable
     * @return Page<Character>
     * O método listCharacters, recebe o pageable do Controller e chama o characterRepository para fazer a requisição ao banco.
     */
    @Override
    public Page<Character> listCharacters(Pageable pageable) {
        return characterRepository.findAll(pageable);
    }

    /**
     * @param pageable
     * @param house
     * @return Page<Character>
     * O método listCharactersByHouse, recebe o pageable e o house Id do Controller e chama o characterRepository para fazer a requisição ao banco.
     */
    @Override
    public Page<Character> listCharactersByHouse(Pageable pageable, String house) {
        return characterRepository.findAllByHouse(pageable,house);
    }

    /**
     * @param id
     * @return Character
     * O método getById, recebe o Id do Controller e chama o characterRepository para fazer a requisição ao banco.
     * O método characterRepository.findById, entrega um Optional, então usamos a estrutura orElse, para caso o registro não seja encotrado
     * passamos para a camada de cima, o null.
     */
    @Override
    public Character getById(String id) {
        return characterRepository.findById(id).orElse(null);
    }

    /**
     * @param token
     * @param character
     * @return Character
     * @throws IOException
     * @throws HouseNotFoundException
     *
     * O método merge, recebe o token da Potter Api e o Character a ser salvo, do CharacterController. Fazemos algumas validações de negócio e caso tudo
     * estja correto, é invocado o método save do characterRepository.
     */
    @Override
    public Character merge(String token,Character character) throws IOException, HouseNotFoundException {
        if(!validationHouse(character.getHouse(),token)){
            throw new HouseNotFoundException();
        }
        return characterRepository.save(character);
    }

    /**
     * @param houseId
     * @param token
     * @return boolean
     * @throws IOException
     *
     * O método validationHouse, é responsável por pegar o houseId e verificar se ele está presente na Potter API, para isso, é necessário receber
     * o token da Potter Api e o houseId do character que queremos validar.
     */
    public boolean validationHouse(String houseId,String token) throws IOException {
        String endpoint = properties.getServer().concat(HOUSES).concat(houseId).concat(KEY).concat(token);
        ResponseEntity<String> responseEntity = template.exchange(endpoint, HttpMethod.GET, null, String.class);
        String body =  responseEntity.getBody();
        JsonNode jsonNode = Util.getJson(body);
        return jsonNode.get(0) != null && jsonNode.get(0).get(ID) != null;
    }

    /**
     * @param id
     * @return boolean
     * O método deleteById, recebe o Id do Controller e chama o método deleteById do characterRepository.
     */
    @Override
    public boolean deleteById(String id) {
        characterRepository.deleteById(id);
        return true;
    }
}
