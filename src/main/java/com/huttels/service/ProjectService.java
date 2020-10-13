package com.huttels.service;


import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.project.ProjectState;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserProjectService userProjectService;

    private final UserService userService;

    @Transactional
    public Long save(String userNickName, ProjectDto projectDto) {
        User user = userService.findUserByNickName(userNickName);
        Project project = projectRepository.save(projectDto.toEntity());
        userProjectService.save(UserProject.builder().project(project).user(user).build());
        return project.getId();
    }

    @Transactional
    public ProjectState getState(Long projectId) {
        Project project = findById(projectId);
        return project.getState();
    }

    @Transactional
    public Project findById(Long projectId){
        return projectRepository.findById(projectId).orElse(null);
    }

    @Transactional
    public void changeState(Long projectId, ProjectState projectState) {
        Project project = findById(projectId);
        project.changeState(projectState);
    }
}
