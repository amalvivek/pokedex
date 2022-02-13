package com.truelayer.interview.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.truelayer.interview.Pokemon;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class RestUtilityTest {

    @Test
    void buildGetRequest() {
        URI uri = UriBuilder.of("http://localhost/").build();
        HttpRequest expected = HttpRequest.newBuilder(uri).GET().build();
        HttpRequest req = RestUtility.buildGetRequest(uri);
        assertEquals(expected, req);
    }
    @Test
    void buildPostRequest() {
        URI uri = UriBuilder.of("http://localhost/").build();
        ObjectNode o = new ObjectMapper().createObjectNode();
        o.put("text", "description");
        HttpRequest expected = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString(o.toString())).build();
        Pokemon p = new Pokemon("","");
        p.setDescription("description");
        HttpRequest req = RestUtility.buildPostRequest(uri, p);
        assertEquals(expected, req);
    }

    @Test
    void buildUri() {
        URI expected = UriBuilder.of("http://localhost/" + "").build();
        URI uri = RestUtility.buildUri("http://localhost/", "");
        assertEquals(expected, uri);
    }

    @Test
    void constructPokemon() {
        Map<String, String> m = new HashMap<>();
        m.put("name", "name");
        m.put("url", "url");
        Map<String, Map<String, String>> j = new HashMap<>();
        j.put("species", m);
        JsonNode json = new ObjectMapper().valueToTree(j);
        Pokemon expected = new Pokemon("name","url");
        Pokemon res = RestUtility.constructPokemon(json);
        assertEquals(expected.toString(), res.toString());
    }

    @Test
    void addSpeciesDetails() throws JsonProcessingException {
        Pokemon p = new Pokemon("name", "url");
        String jsonString = "{" +
                "\"is_legendary\": true," +
                "\"habitat\": { \"name\": \"habitat\"}," +
                "\"flavor_text_entries\": [\n" +
                "    {\n" +
                "      \"flavor_text\": \"desc\"," +
                "      \"language\": {\n" +
                "        \"name\": \"en\"\n" +
                "      }" +
                "    }]}";
        JsonNode json = new ObjectMapper().readTree(jsonString);
        Pokemon res = RestUtility.addSpeciesDetails(json, p);
        p.setDescription("desc");
        p.setLegendary(true);
        p.setHabitat("habitat");
        assertEquals(p, res);
    }

    @Test
    void updateTranslatedDescription() throws JsonProcessingException {
        Pokemon p = new Pokemon("name","url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        String jsonString = "{\"contents\": {\"translated\":\"translated text\"}}";
        JsonNode json = new ObjectMapper().readTree(jsonString);
        Pokemon res = RestUtility.updateTranslatedDescription(json, p);
        p.setDescription("translated text");
        assertEquals(p.toString(), res.toString());
    }

    @Test
    void handleException() {
        Throwable t = new Exception(new HttpStatusException(HttpStatus.valueOf(404), "Not Found"));
        MutableHttpResponse<Pokemon> res = RestUtility.handleException(t);
        assertEquals(HttpResponse.status(((HttpStatusException) t.getCause()).getStatus(), t.getCause().getMessage()).getStatus(), res.getStatus());

        t = new Exception(new RuntimeException("Error"));
        res = RestUtility.handleException(t);
        assertEquals(HttpResponse.serverError().getStatus(), res.getStatus());
    }


}
