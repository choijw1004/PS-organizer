package com.psorganizer.psorganizer.domain.organizer.controller;

import com.psorganizer.psorganizer.domain.organizer.dto.request.OrganizerRequestDto;
import com.psorganizer.psorganizer.domain.organizer.dto.response.OrganizerResponseDto;
import com.psorganizer.psorganizer.domain.organizer.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/analyze")
    public OrganizerResponseDto analyze(@RequestBody OrganizerRequestDto request)
            throws Exception {
        return problemService.analyzeProblem(request);
    }
}
