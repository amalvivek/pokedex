package com.truelayer.interview.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.client.RestClient;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class PokemonServiceTest {

    @Inject
    PokemonService pokemonService;

    @Inject
    RestClient restClient;

    @Inject
    SpeciesService speciesService;

    @Test
    void fetchPokemon() throws ExecutionException, InterruptedException {
        Pokemon p = new Pokemon("name","url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        Map<String, String> m = new HashMap<>();
        m.put("name", "name");
        m.put("url", "url");
        Map<String, Map<String, String>> j = new HashMap<>();
        j.put("species", m);
        JsonNode json = new ObjectMapper().valueToTree(j);
        when(restClient.sendRequest(any())).thenReturn(CompletableFuture.completedFuture(json));
        when(speciesService.fetchSpecies(any())).thenReturn(CompletableFuture.completedFuture(p));

        Pokemon resp = pokemonService.fetchPokemon("name").toCompletableFuture().get();
        assertEquals(p.toString(), resp.toString());
    }

    @MockBean(RestClient.class)
    RestClient restClient() {
        return mock(RestClient.class);
    }

    @MockBean(SpeciesService.class)
    SpeciesService speciesService() {
        return mock(SpeciesService.class);
    }
}
