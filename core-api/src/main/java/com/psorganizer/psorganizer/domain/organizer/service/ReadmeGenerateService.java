package com.psorganizer.psorganizer.domain.organizer.service;

import com.psorganizer.psorganizer.domain.organizer.dto.ProblemInfoDto;
import com.psorganizer.psorganizer.domain.organizer.dto.request.OrganizerRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadmeGenerateService {

    public String generateDirectoryName(ProblemInfoDto info) {
        String platform = info.getProblemPlatform();
        String number = info.getProblemNumber();
        String title = info.getProblemTitle();

        if ("PGMS".equals(platform)) {
            return String.format("%s_%s", platform, number);
        }
        else {
            return String.format("%s_%s_%s", platform, number, title);
        }
    }

    public String generateReadme(OrganizerRequestDto request, ProblemInfoDto problemInfoDto){
        StringBuilder readme = new StringBuilder();

        String directoryName = generateDirectoryName(problemInfoDto);
        readme.append("# ").append(directoryName).append("\n\n");

        readme.append("## 문제 링크\n");
        readme.append(request.getProblemLink()).append("\n\n");

        readme.append("## 카테고리\n");
        List<String> categories = request.getProblemCategories();
        if (categories != null && !categories.isEmpty()) {
            categories.forEach(category ->
                    readme.append("`").append(category).append("` ")
            );
        }
        readme.append("\n\n");

        return readme.toString();
    }
}
