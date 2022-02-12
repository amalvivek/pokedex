package com.truelayer.interview.Service;

import com.truelayer.interview.Client.RestClient;
import com.truelayer.interview.Pokemon;
import com.truelayer.interview.Utility.RestUtility;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.net.URI;
import java.util.concurrent.CompletionStage;

@Singleton
public class TranslationService {

    @Inject
    RestClient client;

    @Value("${restApi.shakespeare}")
    private String shakespeare;

    @Value("${restApi.yoda}")
    private String yoda;

    public CompletionStage<Pokemon> translateDescription(Pokemon pokemon) {
        URI uri = RestUtility.buildUri((pokemon.isLegendaryOrLivesInCave()) ? yoda : shakespeare, "");
        return client
                .sendRequest(RestUtility.buildPostRequest(uri, pokemon))
                .thenApply(json -> RestUtility.updateTranslatedDescription(json, pokemon));
    }


}
