package com.hdl.gzccocpcore.dto;

import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class ChatDTO {

    @Getter @Setter
    private User mine=new User();

    @Getter@Setter
    private List<Friend> friend=new ArrayList<>();

    @Getter@Setter
    private List<Friend> group=new ArrayList<>();
}
