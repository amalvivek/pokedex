package com.truelayer.interview.controller;

import com.truelayer.interview.Pokemon;
import com.truelayer.interview.service.PokemonService;
import com.truelayer.interview.service.TranslationService;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MicronautTest
class PokedexControllerTest {

    @Inject
    PokedexController pokedexController;

    @Inject
    PokemonService pokemonService;

    @Inject
    TranslationService translationService;

    @Test
    void getPokemon() throws ExecutionException, InterruptedException {
        Pokemon p = new Pokemon("name", "url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        when(pokemonService.fetchPokemon(anyString())).thenReturn(CompletableFuture.completedFuture(p));

        CompletionStage<MutableHttpResponse<Pokemon>> pokemon = pokedexController.getPokemon("name");
        Pokemon resp = pokemon.toCompletableFuture().get().body();
        assertEquals(p.toString(), resp.toString());
        verify(pokemonService, times(1)).fetchPokemon(anyString());

    }

    @Test
    void getTranslatedPokemon() throws ExecutionException, InterruptedException {
        Pokemon p = new Pokemon("name", "url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        when(pokemonService.fetchPokemon(anyString())).thenReturn(CompletableFuture.completedFuture(p));
        when(translationService.translateDescription(any())).thenReturn(CompletableFuture.completedFuture(p));

        CompletionStage<MutableHttpResponse<Pokemon>> pokemon = pokedexController.getTranslatedPokemon("name");
        Pokemon resp = pokemon.toCompletableFuture().get().body();
        assertEquals(p.toString(), resp.toString());
        verify(pokemonService, times(1)).fetchPokemon(anyString());
        verify(translationService, times(1)).translateDescription(any());
    }

    @MockBean(PokemonService.class)
    PokemonService pokemonService() { return mock(PokemonService.class);}

    @MockBean(TranslationService.class)
    TranslationService translationService() {
        return mock(TranslationService.class);
    }
}
