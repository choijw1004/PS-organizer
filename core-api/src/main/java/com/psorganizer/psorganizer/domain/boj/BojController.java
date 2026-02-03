package com.psorganizer.psorganizer.domain.boj;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/boj")
@RequiredArgsConstructor
public class BojController {
    private final BojApiClient bojApiClient;

    @GetMapping("/{problemId}")
    public BojResponseDto getProblem(@PathVariable String problemId) throws Exception {
        return bojApiClient.getProblem(problemId);
    }
}
