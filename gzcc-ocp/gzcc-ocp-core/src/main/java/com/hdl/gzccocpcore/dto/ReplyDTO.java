package com.hdl.gzccocpcore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    @Getter
    @Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @Getter
    @Setter
    private Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Getter
    @Setter
    private Date lastModifiedTime;

    @Getter
    @Setter

    private String content;

    @Getter
    @Setter
    private Long praise = Long.valueOf(0);

    @Getter
    @Setter
    private String praiseUserIdString = "";

    @Getter
    @Setter
    private Boolean deleted = false;

    @Getter
    @Setter
    private Note note;

    @Getter
    @Setter
    private Boolean accepted = false;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private Long userId;


    @Getter
    @Setter
    private Long noteId;
    @Getter
    @Setter
    private String username;


}
