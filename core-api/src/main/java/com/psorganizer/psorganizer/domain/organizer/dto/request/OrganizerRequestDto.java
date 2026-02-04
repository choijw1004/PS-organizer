package com.psorganizer.psorganizer.domain.organizer.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrganizerRequestDto {
    private String problemLink;
    private List<String> problemCategories;
    private String problemApproach;
}
