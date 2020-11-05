package com.huttels.domain.BackLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    List<Backlog> findByProjectId(Long projectId);

    @Query("select b from Backlog b where b.state = :backlogState and b.project.id = :projectId")
    List<Backlog> findByStateByProjectId(@Param("projectId")Long projectId, @Param("backlogState")BacklogState backlogState);
}
