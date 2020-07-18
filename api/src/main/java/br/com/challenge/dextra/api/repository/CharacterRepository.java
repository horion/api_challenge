package br.com.challenge.dextra.api.repository;

import br.com.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character,String> {

    Page<Character> findAll(Pageable pageable);

    Page<Character> findAllByHouse(Pageable pageable, String house);
}
