package br.com.challenge.dextra.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Util {

    public static JsonNode getJson(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(result);
    }

}
