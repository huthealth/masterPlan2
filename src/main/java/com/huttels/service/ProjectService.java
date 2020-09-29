package com.huttels.service;


import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.userProject.UserProject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserProjectService userProjectService;
}
