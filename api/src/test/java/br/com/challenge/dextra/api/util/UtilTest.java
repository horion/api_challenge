package br.com.challenge.dextra.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private final String returnApi = "[{\"_id\":\"5a05e2b252f721a3cf2ea33f\",\"name\":\"Gryffindor\",\"mascot\":\"lion\",\"headOfHouse\":\"Minerva McGonagall\",\"houseGhost\":\"Nearly Headless Nick\",\"founder\":\"Goderic Gryffindor\",\"__v\":0,\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"members\":[{\"_id\":\"5a0fa648ae5bc100213c2332\",\"name\":\"Katie Bell\"},{\"_id\":\"5a0fa67dae5bc100213c2333\",\"name\":\"Cuthbert Binns\"},{\"_id\":\"5a0fa7dcae5bc100213c2338\",\"name\":\"Sirius Black\"},{\"_id\":\"5a107e1ae0686c0021283b19\",\"name\":\"Lavender Brown\"},{\"_id\":\"5a10944f3dc2080021cd8755\",\"name\":\"Colin Creevey\"},{\"_id\":\"5a10947c3dc2080021cd8756\",\"name\":\"Dennis Creevey\"},{\"_id\":\"5a1096b33dc2080021cd8762\",\"name\":\"Aberforth Dumbledore\"},{\"_id\":\"5a1097653dc2080021cd8763\",\"name\":\"Albus Dumbledore\"},{\"_id\":\"5a1098fd3dc2080021cd876e\",\"name\":\"Seamus Finnigan\"},{\"_id\":\"5a109af13dc2080021cd877a\",\"name\":\"Hermione Granger\"},{\"_id\":\"5a109bfc3dc2080021cd877f\",\"name\":\"Godric Gryffindor\"},{\"_id\":\"5a109c3d3dc2080021cd8780\",\"name\":\"Rubeus Hagrid\"},{\"_id\":\"5a109cb83dc2080021cd8784\",\"name\":\"Angelina Johnson\"},{\"_id\":\"5a109cd33dc2080021cd8785\",\"name\":\"Lee Jordan\"},{\"_id\":\"5a109e143dc2080021cd878d\",\"name\":\"Alice Longbottom\"},{\"_id\":\"5a109e1e3dc2080021cd878e\",\"name\":\"Frank Longbottom\"},{\"_id\":\"5a109e253dc2080021cd878f\",\"name\":\"Augusta Longbottom\"},{\"_id\":\"5a109e543dc2080021cd8790\",\"name\":\"Neville Longbottom\"},{\"_id\":\"5a109f053dc2080021cd8793\",\"name\":\"Remus Lupin\"},{\"_id\":\"5a1226520f5ae10021650d76\",\"name\":\"Parvati Patil\"},{\"_id\":\"5a1226d70f5ae10021650d77\",\"name\":\"Peter Pettigrew\"},{\"_id\":\"5a12292a0f5ae10021650d7e\",\"name\":\"Harry Potter\"},{\"_id\":\"5a12298d0f5ae10021650d7f\",\"name\":\"James Potter I\"},{\"_id\":\"5a1229e10f5ae10021650d80\",\"name\":\"Lily J. Potter\"},{\"_id\":\"5a122cbe0f5ae10021650d89\",\"name\":\"Demelza Robins\"},{\"_id\":\"5a1233ff0f5ae10021650d98\",\"name\":\"Alicia Spinnet\"},{\"_id\":\"5a1234cb0f5ae10021650d9b\",\"name\":\"Dean Thomas\"},{\"_id\":\"5a1237480f5ae10021650da3\",\"name\":\"Romilda Vane\"},{\"_id\":\"5a1237c00f5ae10021650da5\",\"name\":\"Arthur Weasley\"},{\"_id\":\"5a1238070f5ae10021650da6\",\"name\":\"William Weasley\"},{\"_id\":\"5a1238350f5ae10021650da7\",\"name\":\"Charles Weasley\"},{\"_id\":\"5a12387a0f5ae10021650da8\",\"name\":\"Fred Weasley\"},{\"_id\":\"5a1238b20f5ae10021650da9\",\"name\":\"George Weasley\"},{\"_id\":\"5a1239130f5ae10021650daa\",\"name\":\"Ginevra Weasley\"},{\"_id\":\"5a12393d0f5ae10021650dab\",\"name\":\"Molly Weasley\"},{\"_id\":\"5a12395f0f5ae10021650dac\",\"name\":\"Percy Weasley\"},{\"_id\":\"5a1239c80f5ae10021650dad\",\"name\":\"Ronald Weasley\"},{\"_id\":\"5a1239f10f5ae10021650dae\",\"name\":\"Oliver Wood\"},{\"_id\":\"5a123b450f5ae10021650db7\",\"name\":\"Cadogan\"},{\"_id\":\"5a123f130f5ae10021650dcc\",\"name\":\"Nicholas de Mimsy-Porpington\"}],\"values\":[\"courage\",\"bravery\",\"nerve\",\"chivalry\"],\"colors\":[\"scarlet\",\"gold\"]}]";


    @Test
    void getJson() throws IOException {
        JsonNode jsonNode = Util.getJson(returnApi);
        String houseName = jsonNode.get(0).get("name").asText();
        assertEquals("Gryffindor",houseName);
    }

    @Test
    void getJsonException() {
        assertThrows(IOException.class, () -> Util.getJson("returnApi"));
    }

    @Test
    void getJsonExceptionNotFound() throws IOException {
        JsonNode jsonNode = Util.getJson(returnApi);
        assertThrows(NullPointerException.class, () -> jsonNode.get(10).get("name"));
    }

    @Test
    void getJsonExceptionNotFoundByNotAccess() throws IOException {
        JsonNode jsonNode = Util.getJson(returnApi);
        assertNull(jsonNode.get(10));
    }
}