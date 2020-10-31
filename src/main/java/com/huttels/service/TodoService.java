package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoRepository;
import com.huttels.domain.Todo.TodoState;
import com.huttels.domain.project.ProjectState;
import com.huttels.web.dto.BacklogDto;
import com.huttels.web.dto.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final BacklogService backlogService;

    private final ProjectService projectService;

    @Transactional
    public void saveAll(List<List<String>> todos, String period) {

        Map<Long,Backlog> backlogMap = new HashMap<>();

        int periodNum = 0;
        try{
            periodNum = Integer.parseInt(period);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("period에 자연수를 넣어주세요.");
        }

        ZoneId seoul = ZoneId.of("Asia/Seoul");
        LocalDateTime endDate =  LocalDateTime.now(seoul).plusDays(periodNum).with(LocalTime.of(23,59,59));
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
            Todo todo = Todo.builder().content(content).endDate(endDate).backlog(backlogMap.get(backlogId)).build();

            todoRepository.save(todo);
        }
        for(Backlog backlog : backlogMap.values()) {
            backlogService.changeState(backlog, BacklogState.DOING);
        }

    }

    @Transactional
    public Map<String, List<TodoDto>> findAllByProjectId(Long projectId) {
        Map<String, List<TodoDto>> todoMap = new HashMap<>();
        List<Backlog> backlogs = backlogService.findAllByProjectId(projectId);
        List<TodoDto> todos = new ArrayList<>();
        List<TodoDto> doings = new ArrayList<>();
        List<TodoDto> dones = new ArrayList<>();
        for(Backlog backlog : backlogs) {
            if(!(backlog.getState() == BacklogState.DOING)) continue;
            List<Todo> todolist = findAllByBacklogId(backlog.getId());
            for(Todo todo : todolist) {
                System.out.println("todoId : "+todo.getId() + "todoContent : "+ todo.getContent()+" state : "+todo.getState());
                TodoState state = todo.getState();
                if(state == TodoState.TODO) todos.add(TodoDto.fromEntity(todo));
                else if(state == TodoState.DOING) doings.add(TodoDto.fromEntity(todo));
                else dones.add(TodoDto.fromEntity(todo));
            }
        }
        todoMap.put("todos",todos);
        todoMap.put("doings",doings);
        todoMap.put("dones",dones);
        return todoMap;
    }

    @Transactional
    public List<Todo> findAllByBacklogId(Long backlogId){
        return todoRepository.findByBacklogId(backlogId);
    }

    @Transactional
    public void changeAllState(List<Map<String, String>> result) {
        for(int i = 0; i< result.size();i++) {
            Map<String,String> todos = result.get(i);
            Long todoId = Long.parseLong(todos.get("id"));
            TodoState state = TodoState.valueOf(todos.get("state"));
            Todo todo = findById(todoId);
            todo.changeState(state);
            System.out.println("todoId : "+todo.getId() + "todoContent : "+ todo.getContent()+" state : "+todo.getState());
        }
    }

    @Transactional
    public Todo findById(Long todoId){
        return todoRepository.findById(todoId).orElse(null);
    }

    @Transactional
    public long getLeftDay(Long projectId) {
        long leftDay = -1;
        List<Backlog> backlogs = backlogService.findAllByProjectId(projectId);
        for (Backlog backlog : backlogs) {
            if (!(backlog.getState() == BacklogState.DOING)) continue;
            List<Todo> todolist = findAllByBacklogId(backlog.getId());
            Todo todo = todolist.get(0);
            LocalDateTime endDay = todo.getEndDate();
            ZoneId seoul = ZoneId.of("Asia/Seoul");
            LocalDateTime today = LocalDateTime.now(seoul);
            leftDay = DAYS.between(today, endDay);
            break;
        }
        return leftDay;
    }

    @Transactional
    public void reviewTodos(Long projectId) {
        List<Backlog> doingBacklogList =  backlogService.findDoingStateBackLog(projectId);
        int totalBacklogCount = doingBacklogList.size();
        int countComplete = 0;
        for(Backlog backlog : doingBacklogList) {
            long backlogId = backlog.getId();
            List<Todo> todoList = todoRepository.findByBacklogId(backlogId);
            boolean isComplete = true;
            for(Todo todo : todoList ) {
                if(todo.getState() != TodoState.DONE) {
                    isComplete = false;
                }
                todo.changeState(TodoState.DELETE);
            }
            if(isComplete) {
                backlog.changeState(BacklogState.DONE);
            }
            else{
                backlog.changeState(BacklogState.TODO);
            }
        }
    }
}
