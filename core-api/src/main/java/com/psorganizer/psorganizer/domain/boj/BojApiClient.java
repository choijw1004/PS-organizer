package com.psorganizer.psorganizer.domain.boj;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.psorganizer.psorganizer.common.constants.ApiConstants.BOJ_BASE_URL;

@Component
@RequiredArgsConstructor
public class BojApiClient {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BojResponseDto getProblem(String problemId) throws IOException{
        String url = BOJ_BASE_URL + problemId;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-solvedac-language", "ko")
                .addHeader("Accept", "application/json")
                .build();

        try(Response response = httpClient.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("백준 API 호출 실패:" + response.code());
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, BojResponseDto.class);
        }

    }
}
