package com.truelayer.interview.Utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truelayer.interview.Pokemon;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RestUtility {

    public static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri).GET().build();
    }

    public static URI buildUri(String base, String resource) {
        return UriBuilder.of(base + resource).build();
    }

    public static Pokemon constructPokemon(JsonNode json) {
        System.out.println("CONSTRUCT POKEMON");
        return new Pokemon(json.get("species").get("name").asText(), json.get("species").get("url").asText());
    }

    public static Pokemon addSpeciesDetails(JsonNode json, Pokemon pokemon) {
        pokemon.setDescription(json.get("flavor_text_entries").get(0).get("flavor_text").asText());
        pokemon.setHabitat(json.get("habitat").get("name").asText());
        pokemon.setLegendary(json.get("is_legendary").asBoolean());
        System.out.println(pokemon.toString());
        return pokemon;
    }

}
