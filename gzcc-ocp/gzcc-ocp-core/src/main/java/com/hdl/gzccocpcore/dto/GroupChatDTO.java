package com.hdl.gzccocpcore.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.hdl.gzccocpcore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GroupChatDTO implements Serializable{


    private static final long serialVersionUID = 20393427563199288L;

    @Getter @Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Getter@Setter
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Getter@Setter
    private Date lastModifiedTime;

    @Getter@Setter
    private String groupname;

    @Getter@Setter
    private String avatar;

    @Getter@Setter
    private List<User> userList=new ArrayList<>();

    @Getter@Setter
    private Boolean deleted=false;

    @Getter
    @Setter
    private User user;




}
