package br.com.challenge.dextra.api.repository;

import br.com.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Essa classe é responsável por fazer as operações com o banco de dados. Neste caso, estou utilizando o spring-data
 * com MongoDB
 */
public interface CharacterRepository extends MongoRepository<Character,String> {

    /**
     * @param pageable
     * @return Page<Character>
     *
     *  Esse método retorna todos os characters cadastrados no banco de forma paginada.
     */
    @Override
    Page<Character> findAll(Pageable pageable);


    /**
     * @param pageable
     * @param house
     * @return Page<Character>
     *
     * Esse método retorna todos os characters cadastrados no banco que fazem parte de uma escola específica, de forma paginada.
     */
    Page<Character> findAllByHouse(Pageable pageable, String house);
}
