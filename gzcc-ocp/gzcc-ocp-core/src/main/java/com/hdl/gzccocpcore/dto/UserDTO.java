package com.hdl.gzccocpcore.dto;

import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDTO {


    @Getter @Setter
    private Long id;
    @Getter @Setter
    private Long userId;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    private String content;
    @Getter @Setter
    private Boolean mine=true;
    @Getter @Setter
    private Boolean deleted=true;
    @Getter @Setter
    private String type;
    @Getter @Setter
    private String sign;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String email;
    @Getter@Setter
    private String gender;
    @Getter@Setter
    private Boolean ban;
    @Getter@Setter
    private List<Role> roleList=new ArrayList<>();
    @Getter@Setter
    private List<UserDTO> userList=new ArrayList<>();
    @Getter@Setter
    private String collectNote;
    @Getter@Setter
    private String introduction;
    @Getter@Setter
    private User user;
}
