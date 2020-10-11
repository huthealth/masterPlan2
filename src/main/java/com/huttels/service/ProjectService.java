package com.huttels.service;


import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserProjectService userProjectService;

    private final UserService userService;

    public Long save(String userNickName, ProjectDto projectDto) {
        User user = userService.findUserByNickName(userNickName);
        Project project = projectRepository.save(projectDto.toEntity());
        userProjectService.save(UserProject.builder().project(project).user(user).build());
        return project.getId();
    }
}
