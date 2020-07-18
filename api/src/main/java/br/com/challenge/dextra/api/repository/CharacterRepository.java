package br.com.challenge.dextra.api.repository;

import br.com.challenge.dextra.api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character,Long> {

    Page<Character> findAll(Pageable pageable);

    Page<Character> findAllByHouse(Pageable pageable, String house);
}
