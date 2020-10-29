package com.huttels.service;

import com.huttels.domain.project.Project;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import com.sun.org.apache.bcel.internal.generic.LXOR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    private final UserService userService;

    private final ProjectService projectService;

    @Transactional
    public Long saveProject(String userNickName, ProjectDto projectDto) {
        User user = userService.findUserByNickName(userNickName);
        Project project = projectService.save(projectDto.toEntity());
        save(UserProject.builder().project(project).user(user).build());
        return project.getId();
    }

    @Transactional
    public List<ProjectDto> findAllProjectsByNickName(Long userId){
        List<UserProject>  userProjects = userProjectRepository.findByUserId(userId);
        List<ProjectDto> projectDtos = new ArrayList<>();

        for( UserProject userProject : userProjects){
            projectDtos.add(ProjectDto.fromEntity(userProject.getProject()));
        }
        return projectDtos;
    }

    @Transactional
    public void save(UserProject userProject) {
        userProjectRepository.save(userProject);
    }

    @Transactional
    public boolean isMatched(String userNickName, Long projectId) {
        List<UserProject>  userProjects = userProjectRepository.findByUserId(userService.findUserId(userNickName));
        for(UserProject userProject : userProjects){
            if(userProject.getProject().getId().equals(projectId)) return true;
        }
        return false;
    }

    @Transactional
    public boolean checkUserInProject(User user, Project project) {
        List<UserProject> userProjects = userProjectRepository.findByProject(project);
        for(UserProject userProject : userProjects) {
            if(userProject.getUser().equals(user)) return true;
        }
        return false;
    }

    @Transactional
    public boolean addUserToProject(String userNickName, Long projectId) {
        Project project = projectService.findById(projectId);
        User user = userService.findUserByNickName(userNickName);
        if(user == null || checkUserInProject(user,project)) return false;

        save(UserProject.builder().project(project).user(user).build());
        return true;
    }
}
