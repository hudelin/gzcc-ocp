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
public class SendDTO {

    @Getter @Setter
    private String type;
    @Getter @Setter
    private ChatDTO data=new ChatDTO();

}
