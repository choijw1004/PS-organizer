package com.psorganizer.psorganizer.domain.organizer.service;

import com.psorganizer.psorganizer.domain.boj.BojApiClient;
import com.psorganizer.psorganizer.domain.boj.BojResponseDto;
import com.psorganizer.psorganizer.domain.leetcode.LeetApiClient;
import com.psorganizer.psorganizer.domain.leetcode.LeetResponseDto;
import com.psorganizer.psorganizer.domain.organizer.dto.ProblemInfoDto;
import com.psorganizer.psorganizer.domain.organizer.dto.request.OrganizerRequestDto;
import com.psorganizer.psorganizer.domain.organizer.dto.response.OrganizerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final BojApiClient bojApiClient;
    private final LeetApiClient leetApiClient;
    private final ReadmeGenerateService generator;

    public OrganizerResponseDto analyzeProblem(OrganizerRequestDto request) throws Exception{
        String problemLink = request.getProblemLink();
        ProblemInfoDto problemInfoDto;

        if(isBojUrl(problemLink)){
            String problemId = extractBojProblemId(problemLink);

            BojResponseDto boj = bojApiClient.getProblem(problemId);
            problemInfoDto = ProblemInfoDto.fromBoj(boj);
        }

        else if(isLeetCodeUrl(problemLink)){

            String title = extractLeetCodeTitleSlug(problemLink);
            LeetResponseDto leet = leetApiClient.getProblem(title);
            problemInfoDto = ProblemInfoDto.fromLeetCode(leet);
        }

        else {
            String problemNumber = extractProgrammersProblemNumber(problemLink);
            problemInfoDto = ProblemInfoDto.forProgrammers(problemNumber);
        }

        String readme = generator.generateReadme(request, problemInfoDto);

        String directoryName = generator.generateDirectoryName(problemInfoDto);
        String commitMessage = generateCommitMessage(directoryName);

        return OrganizerResponseDto.builder()
                .directoryName(directoryName)
                .commitMessage(commitMessage)
                .readmeContent(readme)
                .platform(problemInfoDto.getProblemPlatform())
                .problemNumber(problemInfoDto.getProblemNumber())
                .problemTitle(problemInfoDto.getProblemTitle())
                .build();
    }

    private String extractBojProblemId(String url) {
        // https://www.acmicpc.net/problem/1000 → "1000"
        Pattern pattern = Pattern.compile("problem/(\\d+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("백준 문제 번호를 찾을 수 없습니다: " + url);
    }

    private String extractLeetCodeTitleSlug(String url) {
        // https://leetcode.com/problems/two-sum/ → "two-sum"
        Pattern pattern = Pattern.compile("problems/([^/]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("LeetCode titleSlug를 찾을 수 없습니다: " + url);
    }

    private String extractProgrammersProblemNumber(String url) {
        // https://programmers.co.kr/learn/courses/30/lessons/43165 → "43165"
        Pattern pattern = Pattern.compile("lessons/(\\d+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("프로그래머스 문제 번호를 찾을 수 없습니다: " + url);
    }

    private String generateCommitMessage(String directoryName) {
        return "feat: " + directoryName;
    }

    private boolean isBojUrl(String url) {
        return url.contains("acmicpc.net") || url.contains("solved.ac");
    }

    private boolean isLeetCodeUrl(String url) {
        return url.contains("leetcode.com");
    }

    private boolean isProgrammersUrl(String url) {
        return url.contains("programmers.co.kr");
    }

}
