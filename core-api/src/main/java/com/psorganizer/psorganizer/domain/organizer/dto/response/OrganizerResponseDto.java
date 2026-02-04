package com.psorganizer.psorganizer.domain.organizer.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrganizerResponseDto {
    private String directoryName;
    private String commitMessage;
    private String readmeContent;
    private String platform;
    private String problemNumber;
    private String problemTitle;
}
