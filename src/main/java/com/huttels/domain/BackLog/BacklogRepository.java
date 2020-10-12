package com.huttels.domain.BackLog;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    
}
