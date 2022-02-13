package com.truelayer.interview.service;

import com.truelayer.interview.client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.utility.RestUtility;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.net.URI;
import java.util.concurrent.CompletionStage;

@Singleton
public class PokemonService {

    @Inject
    RestClient client;

    @Value("${restApi.pokemon}")
    private String pokemonBase;

    @Inject
    SpeciesService speciesService;

    public CompletionStage<Pokemon> fetchPokemon(String name) {
        URI uri = RestUtility.buildUri(pokemonBase, name);
        return client
                .sendRequest(RestUtility.buildGetRequest(uri))
                .thenApply(RestUtility::constructPokemon)
                .thenComposeAsync(speciesService::fetchSpecies);
    }

}
