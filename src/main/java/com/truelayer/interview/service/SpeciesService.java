package com.truelayer.interview.service;

import com.truelayer.interview.client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.utility.RestUtility;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.net.URI;
import java.util.concurrent.CompletionStage;

@Singleton
public class SpeciesService {

    @Inject
    RestClient client;

    public CompletionStage<Pokemon> fetchSpecies(Pokemon pokemon) {
        URI uri = RestUtility.buildUri(pokemon.getSpeciesURL(), "");
        return client.sendRequest(RestUtility.buildGetRequest(uri)).thenApply(resp -> RestUtility.addSpeciesDetails(resp, pokemon));
    }

}
