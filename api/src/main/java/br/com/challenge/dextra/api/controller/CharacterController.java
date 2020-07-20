package br.com.challenge.dextra.api.controller;

import br.com.challenge.dextra.api.controller.dto.CharacterDTO;
import br.com.challenge.dextra.api.controller.dto.ErrorFormDTO;
import br.com.challenge.dextra.api.controller.dto.Response;
import br.com.challenge.dextra.api.controller.exception.HouseNotFoundException;
import br.com.challenge.dextra.api.controller.form.CharacterForm;
import br.com.challenge.dextra.api.model.Character;
import br.com.challenge.dextra.api.service.CharacterService;
import io.swagger.annotations.*;
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

/**
 * Classe CharacterController, é uma Bean Spring do tipo RestController, ou seja, responsável por expor endpoints RESTful.
 * Neste caso, os endpoints que estão sendo expostos são os de manipulação de character , criar, listar, apagar, detalhar e atualizar
 */
@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterController.class);

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * @param house
     * @param pageable
     * @return Page<Response>
     *
     * O método listCharacter, é um endpoint do tipo GET, que pode receber informações personalizadas de paginação
     * e/ou um houseId para listar os characters por um filtro específico. Se houseId for nulo, não haverá filtro de busca
     */
    @ApiOperation(value = "Listar os personagens cadastrados",
            notes = ".listCharacters()")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem realizada com sucesso",
                    response = Page.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "house", paramType = "path",
                    value = "Id da casa que deseja filtrar, se não passar nenhum, a busca será por todas as casas")
    })
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

    /**
     * @param token
     * @param form
     * @param uriComponentsBuilder
     * @return ResponseEntity<Response>
     *
     *  O método create, é um endpoint do tipo POST, que recebe o header token, esse token é o token da Potter Api,
     *  E recebe o CharacterForm no corpo do método, esse form contém as informações necessárias para a criação de um
     *  Character
     *
     */
    @ApiOperation(value = "Criar um personagem",
            notes = ".create()")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Criação realizada com sucesso",
                    response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Não foi possível cadastrar este personagem, verificar os parâmetros informados",
                    response = Page.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "form", paramType = "body",
                    value = "Informações do personagem a ser cadastrado"),
            @ApiImplicitParam(name = "token", paramType = "header",
                    value = "Token da Potter API, se não tiver um, utilizar: $2a$10$ZOElEX6GhOLFACcXBcrSKuZXzBs0GJOpg/0/NO6P31tz97ntOQOhS")

    })
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

    /**
     * @param id
     * @return ResponseEntity<Response>
     *
     * O método detail, é um endpoint do tipo GET, que recebe o parâmetro id via URL, este método é responsável e fazer uma
     * consulta através do Id informado e retorna as informações de um usuário específico
     *
     */
    @ApiOperation(value = "Buscar um personagem",
            notes = ".detail()")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busca realizada com sucesso",
                    response = CharacterDTO.class),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este personagem, verificar os parâmetros informados",
                    response = Page.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", paramType = "path",
                    value = "Id do personagem cadastrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response> detail(@PathVariable String id){
        Character characterDB = characterService.getById(id);
        if(characterDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CharacterDTO(characterDB));
    }

    /**
     * @param form
     * @param id
     * @param token
     * @return ResponseEntity<Response>
     *
     *  O método update, é um endpoint do tipo PUT e é responsável por atualizar um character específico, atravéns do id informado pela
     *  URL. As novas informações, também são passadas via CharacterForm no body
     */
    @ApiOperation(value = "Atualizar um personagem",
            notes = ".update()")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualização realizada com sucesso",
                    response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Não foi possível atualizar este personagem, verificar os parâmetros informados",
                    response = Page.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "form", paramType = "body",
                    value = "Informações do personagem a ser cadastrado"),
            @ApiImplicitParam(name = "token", paramType = "header",
                    value = "Token da Potter API, se não tiver um, utilizar: $2a$10$ZOElEX6GhOLFACcXBcrSKuZXzBs0GJOpg/0/NO6P31tz97ntOQOhS"),
            @ApiImplicitParam(name = "id", paramType = "path",
                    value = "Id do personagem cadastrado")

    })
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

    /**
     * @param id
     * @return ResponseEntity<?>
     * O método delete, é um método do tipo DELETE e é responsável em excluir um usuário específico através do Id
     * informado na URL
     */
    @ApiOperation(value = "Deletar um personagem",
            notes = ".delete()")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleção realizada com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possível encontrar este personagem, verificar os parâmetros informados",
                    response = Page.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", paramType = "path",
                    value = "Id do personagem cadastrado")
    })
    @DeleteMapping("/{id}")
    @CacheEvict(value = "listCharacters", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            Character characterDB = characterService.getById(id);
            if(characterDB == null){
                return ResponseEntity.notFound().build();
            }
            characterService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
