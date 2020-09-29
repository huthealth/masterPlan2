package com.huttels.web.dto;

import com.huttels.domain.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public ProjectDto(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static ProjectDto fromEntity(Project project){
        return new ProjectDto(project.getId(),project.getTitle(), project.getContent());
    }
}
