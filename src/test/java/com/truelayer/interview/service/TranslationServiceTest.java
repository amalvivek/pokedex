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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class TranslationServiceTest {

    @Inject
    TranslationService translationService;

    @Inject
    RestClient restClient;

    @Test
    void translateDescription() throws JsonProcessingException, ExecutionException, InterruptedException {
        Pokemon p = new Pokemon("name","url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        String jsonString = "{\"contents\": {\"translated\":\"translated text\"}}";
        JsonNode json = new ObjectMapper().readTree(jsonString);
        when(restClient.sendRequest(any())).thenReturn(CompletableFuture.completedFuture(json));

        Pokemon resp = translationService.translateDescription(p).toCompletableFuture().get();
        p.setDescription("translated text");
        assertEquals(p.toString(), resp.toString());
    }

    @MockBean(RestClient.class)
    RestClient restClient() {
        return mock(RestClient.class);
    }
}
