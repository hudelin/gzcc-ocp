package com.hdl.gzccocpcore.dto;

import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
public class MessageDTO {
    @Getter@Setter
    private Long id; //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
    @Getter @Setter
    private String username;//消息来源用户名
    @Getter @Setter
    private String avatar; //消息来源用户头像
    @Getter @Setter
    private String type;//聊天窗口来源类型，从发送消息传递的to里面获取
    @Getter @Setter
    private String content;//消息内容
    @Getter @Setter
    private Long cid; //消息id，可不传。除非你要对消息进行一些操作（如撤回）
    @Getter @Setter
    private  Boolean mine=false; //是否我发送的消息，如果为true，则会显示在右方
    @Getter @Setter
    private  Boolean system=false;
    @Getter @Setter
    private Long fromid= Long.valueOf(0);//消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
    @Getter @Setter
    private Long toid= Long.valueOf(0);
    @Getter @Setter
    private Long timestamp;
}
