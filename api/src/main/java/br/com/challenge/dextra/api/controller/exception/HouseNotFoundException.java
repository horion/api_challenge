package br.com.challenge.dextra.api.controller.exception;

/**
 * Exceção levantada durante a validação do Id da Casa , na Potter API, caso não exista o Id informado, a aplicação
 * levantará HouseNotFoundException
 */
public class HouseNotFoundException extends Exception {
    private static final long serialVersionUID = 1457032604763786930L;
}
