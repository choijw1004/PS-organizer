package com.psorganizer.psorganizer.domain.leetcode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.psorganizer.psorganizer.common.constants.ApiConstants.LEET_BASE_URL;

@Component
@RequiredArgsConstructor
public class LeetApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public LeetResponseDto getProblem(String titleSlug) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LEET_BASE_URL + titleSlug))
                .header("Accept", "application/json")
                .GET()
                .build();


        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("LeetCode API 호출 실패: " + response.statusCode() + " " + response.body());
        }

        LeetResponseDto result = objectMapper.readValue(response.body(), LeetResponseDto.class);

        if(result.getQuestionTitle() != null){
            result.setQuestionTitle(result.getQuestionTitle().replace(" ", "_"));
        }

        return result;
    }
}
