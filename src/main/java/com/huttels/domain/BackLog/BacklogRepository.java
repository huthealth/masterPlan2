package com.huttels.domain.BackLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    List<Backlog> findByProjectId(Long projectId);
}
