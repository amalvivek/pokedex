package com.truelayer.interview.Service;

import com.truelayer.interview.Client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Utility.RestUtility;
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
