package com.huttels.domain.BackLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BacklogRepository extends JpaRepository<Backlog, Long> {
    
}
