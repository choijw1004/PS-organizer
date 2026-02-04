package com.psorganizer.psorganizer.domain.organizer.dto;

import com.psorganizer.psorganizer.domain.boj.BojResponseDto;
import com.psorganizer.psorganizer.domain.leetcode.LeetResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Builder
public class ProblemInfoDto {
    private String problemPlatform;
    private String problemNumber;
    private String problemTitle;

    public static ProblemInfoDto fromBoj(BojResponseDto boj){
        return ProblemInfoDto.builder()
                .problemPlatform("BOJ")
                .problemNumber(String.valueOf(boj.getProblemId()))
                .problemTitle(boj.getTitleKo().replaceAll(" ", "_"))
                .build();
    }

    public static ProblemInfoDto fromLeetCode(LeetResponseDto leet) {
        String title = Arrays.stream(leet.getQuestionTitle().split("-"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining("_"));

        return ProblemInfoDto.builder()
                .problemPlatform("Leet")
                .problemNumber(leet.getQuestionId())
                .problemTitle(title)
                .build();
    }

    public static ProblemInfoDto forProgrammers(String problemNumber) {
        return ProblemInfoDto.builder()
                .problemPlatform("PGMS")
                .problemNumber(problemNumber)
                .problemTitle(null)
                .build();
    }
}
