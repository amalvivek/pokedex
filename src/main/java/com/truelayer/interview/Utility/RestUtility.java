package com.truelayer.interview.Utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.truelayer.interview.Pokemon;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import java.net.URI;
import java.net.http.HttpRequest;

@Singleton
public class RestUtility {

    public static HttpRequest buildGetRequest(URI uri) {
        return HttpRequest.newBuilder(uri).GET().build();
    }

    public static HttpRequest buildPostRequest(URI uri, Pokemon p) {
        ObjectNode o = new ObjectMapper().createObjectNode();
        o.put("text", p.getDescription());
        return HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString(o.toString())).build();
    }

    public static URI buildUri(String base, String resource) {
        return UriBuilder.of(base + resource).build();
    }

    public static Pokemon constructPokemon(JsonNode json) {
        return new Pokemon(json.get("species").get("name").asText(), json.get("species").get("url").asText());
    }

    public static Pokemon addSpeciesDetails(JsonNode json, Pokemon pokemon) {
        for (JsonNode n : json.get("flavor_text_entries")) {
            if (n.get("language").get("name").asText().equals("en")) {
                pokemon.setDescription(n.get("flavor_text").asText());
                break;
            }
        }
        pokemon.setHabitat(json.get("habitat").get("name").asText());
        pokemon.setLegendary(json.get("is_legendary").asBoolean());
        return pokemon;
    }

    public static Pokemon updateTranslatedDescription(JsonNode json, Pokemon pokemon) {
        if (json.get("contents").get("translated").isTextual()) {
            pokemon.setDescription(json.get("contents").get("translated").asText());
        }
        return pokemon;
    }

    public static MutableHttpResponse<Pokemon> handleException(Throwable t) {
        if (t.getCause() instanceof HttpStatusException) {
            return io.micronaut.http.HttpResponse.status(((HttpStatusException) t.getCause()).getStatus(), t.getCause().getMessage());
        }
        return HttpResponse.serverError();
    }

}
