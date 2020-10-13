package com.huttels.web.dto;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogState;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BacklogDto {

    private Long id;
    private String title;


    @Builder
    public BacklogDto(Long id, String title){
        this.id = id;
        this.title = title;
    }

    public static BacklogDto fromEntity(Backlog backlog){
        return  BacklogDto.builder().id(backlog.getId()).title(backlog.getTitle()).build();
    }
}
