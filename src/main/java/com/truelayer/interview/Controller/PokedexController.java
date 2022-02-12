package com.truelayer.interview.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Service.PokemonService;
import com.truelayer.interview.Service.TranslationService;
import com.truelayer.interview.Utility.RestUtility;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.concurrent.CompletionStage;

@Controller("/pokemon")
public class PokedexController {

    @Inject
    PokemonService pokemonService;

    @Inject
    TranslationService translationService;

    @Get("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<MutableHttpResponse<Pokemon>> getPokemon(@PathVariable String name) {
        return pokemonService.fetchPokemon(name).thenApply(HttpResponse::ok).exceptionally(RestUtility::handleException);
    }

    @Get("translated/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<MutableHttpResponse<Pokemon>> getTranslatedPokemon(@PathVariable String name) {
        return pokemonService.fetchPokemon(name)
                .thenComposeAsync(translationService::translateDescription)
                .thenApply(HttpResponse::ok)
                .exceptionally(RestUtility::handleException);

    }


}
