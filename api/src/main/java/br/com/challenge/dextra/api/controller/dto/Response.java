package br.com.challenge.dextra.api.controller.dto;

import java.io.Serializable;

/**
 * A classe Response, é uma classe abstrata, utilizada para generalizar o retorno, pois na API, retornamos o Response
 * e as classes como CharacterDTO e ErroFormDTO, extendem de Response.
 */
public abstract class Response  implements Serializable {
    private static final long serialVersionUID = 4038864544862343794L;
}
