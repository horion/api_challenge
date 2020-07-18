package br.com.challenge.dextra.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class Util {

    public static HashMap<String,Object> getMapJson(String cred) throws IOException {
        cred = cred.replaceAll("'","\"");
        return new ObjectMapper().readValue(cred,HashMap.class);
    }

}
