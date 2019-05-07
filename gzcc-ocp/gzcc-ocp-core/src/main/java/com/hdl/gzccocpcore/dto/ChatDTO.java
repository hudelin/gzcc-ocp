package com.hdl.gzccocpcore.dto;

import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class ChatDTO {

    @Getter @Setter
    private UserDTO mine=new UserDTO();
    @Getter @Setter
    private UserDTO to=new UserDTO();

    @Getter@Setter
    private List<Friend> friend=new ArrayList<>();

    @Getter@Setter
    private List<GroupChatDTO> group=new ArrayList<>();

    @Getter@Setter
    private List<User> list=new ArrayList<>();
}
