package com.huttels.service;

import com.huttels.domain.project.Project;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import com.sun.org.apache.bcel.internal.generic.LXOR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;



    public List<ProjectDto> findAllProjectsByNickName(Long userId){
        List<UserProject>  userProjects = userProjectRepository.findAllByUserId(userId);
        List<ProjectDto> projectDtos = new ArrayList<>();

        for( UserProject userProject : userProjects){
            projectDtos.add(ProjectDto.fromEntity(userProject.getProject()));
        }
        return projectDtos;
    }
}
