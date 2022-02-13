package com.truelayer.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.client.RestClient;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class SpeciesServiceTest {

    @Inject
    SpeciesService speciesService;

    @Inject
    RestClient restClient;

    @Test
    void fetchSpecies() throws JsonProcessingException, ExecutionException, InterruptedException {
        Pokemon p = new Pokemon("name", "http://localhost/");
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
        Pokemon expected = new Pokemon("name","http://localhost/");
        expected.setHabitat("habitat");
        expected.setDescription("desc");
        expected.setLegendary(true);
        when(restClient.sendRequest(any())).thenReturn(CompletableFuture.completedFuture(json));

        Pokemon resp = speciesService.fetchSpecies(p).toCompletableFuture().get();
        assertEquals(expected.toString(), resp.toString());
    }

    @MockBean(RestClient.class)
    RestClient restClient() {
        return mock(RestClient.class);
    }

}
