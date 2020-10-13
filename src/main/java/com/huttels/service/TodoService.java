package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final BacklogService backlogService;

    private final ProjectService projectService;

    @Transactional
    public void saveAll(List<List<String>> todos, int period, long projectId) {
        Map<Long,Backlog> backlogMap = new HashMap<>();

        for(List<String> todoList : todos){
            Long backlogId = Long.parseLong(todoList.get(0));
            String content = todoList.get(1);
            if(!backlogMap.containsKey(backlogId)) {
                Backlog backlog = backlogService.findById(backlogId);
                if(backlog == null) {
                    throw new RuntimeException("backlogId가 존재하지 않습니다.");
                }
                backlogMap.put(backlogId,backlog);
            }
            Todo todo = Todo.builder().content(content).period(period).backlog(backlogMap.get(backlogId)).build();
            todoRepository.save(todo);
        }
        for(Backlog backlog : backlogMap.values()) {
            backlogService.changeState(backlog, BacklogState.DOING);
        }

        projectService.changeState(projectId, ProjectState.BOARD);
    }
}
