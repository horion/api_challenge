package br.com.challenge.dextra.api.controller;

import br.com.challenge.dextra.api.controller.dto.CharacterDTO;
import br.com.challenge.dextra.api.controller.dto.ErrorFormDTO;
import br.com.challenge.dextra.api.controller.dto.Response;
import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.controller.form.CharacterForm;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterController.class);

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    @Cacheable(value = "listCharacters")
    public Page<Response> listCharacters(@RequestParam(required = false) String house,
                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Character> characterPage;
        if(house == null)
            characterPage = characterService.listCharacters(pageable);
        else
            characterPage = characterService.listCharactersByHouse(pageable,house);

        return CharacterDTO.converter(characterPage);
    }

    @PostMapping
    @CacheEvict(value = "listCharacters", allEntries = true)
    public ResponseEntity<Response> create(@RequestHeader String token, @RequestBody
    @Valid CharacterForm form, UriComponentsBuilder uriComponentsBuilder){
        try {
            Character character = characterService.merge(token,form.converter());
            URI uri = uriComponentsBuilder.path("/character/{id}").buildAndExpand(character.getId()).toUri();
            return ResponseEntity.created(uri).body(new CharacterDTO(character));
        }catch (HouseNotFoundException e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.badRequest().body(new ErrorFormDTO("house","Not found house by id"));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> detail(@PathVariable String id){
        Character characterDB = characterService.getById(id);
        if(characterDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CharacterDTO(characterDB));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listCharacters", allEntries = true)
    public ResponseEntity<Response> update(@RequestBody @Valid CharacterForm form, @PathVariable String id,@RequestHeader String token){
        try{
            Character characterDB = characterService.getById(id);
            if(characterDB == null){
                return ResponseEntity.notFound().build();
            }
            CharacterDTO body = new CharacterDTO(characterService.merge(token,form.update(characterDB)));
            return ResponseEntity.ok(body);
        }catch (HouseNotFoundException e){
            LOGGER.error("House not Found by id");
            return ResponseEntity.badRequest().body(new ErrorFormDTO("house","Not found house by id"));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listCharacters", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable String id){
        Character characterDB = characterService.getById(id);
        if(characterDB == null){
            return ResponseEntity.notFound().build();
        }
        characterService.deleteById(id);
        return ResponseEntity.ok().build();
    }



}
