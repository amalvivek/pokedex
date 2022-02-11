package com.truelayer.interview.Controller;

import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Service.PokemonService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

@Controller("/pokemon")
public class PokedexController {

    @Inject
    PokemonService pokemonService;

    @Get("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<HttpResponse<Pokemon>> getPokemon(@PathVariable String name) {
        return pokemonService.fetchPokemon(name).thenApply(HttpResponse::ok);
    }
}
