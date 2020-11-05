package com.huttels.domain.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByBacklogId(Long backlogId);

    @Query("select t from Todo t where t.backlog.id = :backlogId and t.state <> com.huttels.domain.Todo.TodoState.DELETE ")
    List<Todo> findNotDeleteStateByBacklogId(Long backlogId);
}
