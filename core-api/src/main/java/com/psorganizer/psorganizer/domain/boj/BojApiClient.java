package com.psorganizer.psorganizer.domain.boj;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.psorganizer.psorganizer.common.constants.ApiConstants.BOJ_BASE_URL;

@Component
@RequiredArgsConstructor
public class BojApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BojResponseDto getProblem(String problemId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BOJ_BASE_URL + problemId))
                .header("x-solvedac-language", "ko")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return objectMapper.readValue(response.body(), BojResponseDto.class);
    }
}
