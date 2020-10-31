package com.huttels.domain.BackLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    List<Backlog> findByProjectId(Long projectId);

    @Query("select b from Backlog b where b.project.id = ?1 and b.state= ?2")
    List<Backlog> findByStateByProjectId(Long projectId, BacklogState backlogState);
}
