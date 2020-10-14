package com.huttels.domain.Todo;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoId")
    private Long id;

    @Column
    private String content;

    private TodoState state;

    //private int period;

    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "backlogId")
    private Backlog backlog;

    @Builder
    public Todo(String content, int period, Backlog backlog, LocalDateTime endDate){
        this.content = content;
        //this.period = period;
        this.backlog = backlog;
        this.endDate = endDate;
        this.state = TodoState.TODO;
    }
    public void changeState(TodoState state){
        this.state = state;
    }
}
