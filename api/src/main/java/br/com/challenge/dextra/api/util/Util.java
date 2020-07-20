package br.com.challenge.dextra.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Classe Util, é responsável por encapsular métodos utilitários, que são utilizado em vários lugares do sistema
 */
public class Util {

    /**
     * @param result
     * @return JsonNode
     * @throws IOException
     *
     * O método getJson, recebe uma string em formato de Json e encapsula essas informações dentro de um objeto JsonNode
     * Esse tipo de objeto pode ser acessado mais facilmente.
     */
    public static JsonNode getJson(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(result);
    }

}
