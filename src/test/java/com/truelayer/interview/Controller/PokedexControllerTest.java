package com.truelayer.interview.Controller;

import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Service.PokemonService;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MicronautTest
class PokedexControllerTest {

    @Inject
    PokedexController pokedexController;

    @Inject
    PokemonService pokemonService;


    @Test
    void getPokemon() throws ExecutionException, InterruptedException {
        System.out.println("TESTING@!!!!!!!!!!");
        Pokemon p = new Pokemon("name", "url");
        p.setHabitat("habitat");
        p.setDescription("desc");
        p.setLegendary(true);
        when(pokemonService.fetchPokemon(anyString())).thenReturn(CompletableFuture.completedFuture(p));

        CompletionStage<HttpResponse<Pokemon>> pokemonCompletableFuture = pokedexController.getPokemon("name");
        Pokemon resp = pokemonCompletableFuture.toCompletableFuture().get().body();
        assertEquals(p.toString(), resp.toString());
        verify(pokemonService, times(1)).fetchPokemon(anyString());

    }

    @MockBean(PokemonService.class)
    PokemonService pokemonService() { return mock(PokemonService.class);};
}
