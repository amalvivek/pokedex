package com.truelayer.interview.Service;

import com.truelayer.interview.Client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Utility.RestUtility;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.net.http.HttpResponse.BodyHandlers;

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
                .sendRequest(RestUtility.buildRequest(uri))
                .thenApply(RestUtility::constructPokemon)
                .thenComposeAsync(speciesService::fetchSpecies);
    }

}
