package com.huttels.domain.Todo;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private int period;

    @ManyToOne
    @JoinColumn(name = "backlogId")
    private Backlog backlog;

    @Builder
    public Todo(String content, int period, Backlog backlog){
        this.content = content;
        this.period = period;
        this.backlog = backlog;
        this.state = TodoState.TODO;
    }

}
