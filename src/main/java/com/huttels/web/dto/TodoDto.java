package com.huttels.web.dto;

import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodoDto {

    private Long id;
    private String content;
    private TodoState state;

    @Builder
    public TodoDto(Long id, String content, TodoState state){
        this.id = id;
        this.content = content;
        this.state = state;
    }

    public static TodoDto fromEntity(Todo todo){
        return TodoDto.builder().id(todo.getId()).content(todo.getContent()).state(todo.getState()).build();
    }

}
