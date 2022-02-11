package com.truelayer.interview.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.truelayer.interview.Client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Utility.RestUtility;
import io.micronaut.test.annotation.MockBean;
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

class PokemonServiceTest {

    @Inject
    RestClient restClient;

    @Inject
    SpeciesService speciesService;

    @Inject
    PokemonService pokemonService;

    @Test
    void fetchPokemon() throws ExecutionException, InterruptedException {
        Map<String, String> m = new HashMap<>();
        m.put("name", "name");
        m.put("url", "url");
        ObjectNode o = new ObjectMapper().createObjectNode().putPOJO("species", m);
        when(restClient.sendRequest(any())).thenReturn(CompletableFuture.completedFuture(o));
        when(speciesService.fetchSpecies(any())).thenReturn(CompletableFuture.completedFuture(new Pokemon("name", "url")));

        Pokemon p = pokemonService.fetchPokemon("name").toCompletableFuture().get();
        assertEquals(new Pokemon("name", "url"), p);
        assertEquals(1,1);
    }

    @MockBean(RestClient.class)
    RestClient restClient() { return mock(RestClient.class);};

    @MockBean(SpeciesService.class)
    SpeciesService speciesService() { return mock(SpeciesService.class);};

}
