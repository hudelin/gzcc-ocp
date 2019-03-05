package com.hdl.gzccocpcore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.entity.Resource;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
* @Description:    帖子类
* @Author:         HuDeLin
* @CreateDate:     2018/12/24 11:49
* @UpdateUser:     HuDeLin
* @UpdateDate:     2018/12/24 11:49
* @UpdateRemark:   修改内容
*/
public class NoteDTO implements Serializable {


    private static final long serialVersionUID = -7048325638851182914L;
    @Getter@Setter
    private Long id;
    @Getter@Setter
    private Date createTime;
    @Getter@Setter
    private Date lastModifiedTime;
    @Getter@Setter
    private String title;
    @Getter@Setter
    private String content;
    @Getter@Setter
    private List<Reply> replyList= new ArrayList<>();
    @Getter@Setter
    private Boolean canReply;
    @Getter@Setter
    private Boolean top;
    @Getter@Setter
    private Boolean deleted;
    @Getter@Setter
    private String noteType;
    @Getter@Setter
    private User user;
    @Getter@Setter
    private String resource;
    @Getter@Setter
    private List<Resource> resourceList;



}
