package com.truelayer.interview.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionStage;

import static java.net.http.HttpResponse.BodyHandlers;

@Singleton
public class RestClient {


    private HttpClient client = HttpClient.newHttpClient();

    public HttpClient getClient() {
        return client;
    }

    public CompletionStage<JsonNode> sendRequest(HttpRequest req) {
        return this.client.sendAsync(req, BodyHandlers.ofByteArray()).thenApply(this::byteArrayToJson);
    }

    public JsonNode byteArrayToJson(HttpResponse<byte[]> resp) {
        if (resp.statusCode() < 200 || resp.statusCode() > 299) {
            String s = new String(resp.body());
            throw new HttpStatusException(HttpStatus.valueOf(resp.statusCode()), s);
        }
        try {
            return new ObjectMapper().readTree(resp.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
