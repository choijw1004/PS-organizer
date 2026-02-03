package com.psorganizer.psorganizer.domain.leetcode;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leet")
@RequiredArgsConstructor
public class LeetController {
    private final LeetApiClient leetApiClient;

    @GetMapping("/{titleSlug}")
    public LeetResponseDto getProblem (@PathVariable String titleSlug) throws Exception{
        return leetApiClient.getProblem(titleSlug);
    }
}
