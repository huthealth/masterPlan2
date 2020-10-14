package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectState;
import com.huttels.web.dto.BacklogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BacklogService {

    private final BacklogRepository backlogRepository;

    private final ProjectService projectService;

    @Transactional
    public boolean saveAll(List<String> backlogs,Long projectId){
        if(backlogs == null || backlogs.isEmpty()) return false;
        Project project = projectService.findById(projectId);
        if(project == null) return false;
        for (String s : backlogs) {
            Backlog backlog = Backlog.builder().title(s).project(project).build();
            backlogRepository.save(backlog);
        }
        return true;
    }

    @Transactional
    public Backlog findById(Long backlogId) {
        return backlogRepository.findById(backlogId).orElse(null);
    }

    @Transactional
    public void changeState(Backlog backlog, BacklogState state) {
        backlog.changeState(state);
    }

    @Transactional
    public List<BacklogDto> findAllDtoByProjectId(Long projectId) {
        List<Backlog> backlogs = backlogRepository.findAll();
        List<BacklogDto> backlogDtos = new ArrayList<>();
        for (Backlog backlog : backlogs) {
            BacklogDto backlogDto = BacklogDto.fromEntity(backlog);
            backlogDtos.add(backlogDto);
        }
        return backlogDtos;
    }

    @Transactional
    public List<Backlog> findAllByProjectId(Long projectId) {
        return backlogRepository.findAll();
    }
}
