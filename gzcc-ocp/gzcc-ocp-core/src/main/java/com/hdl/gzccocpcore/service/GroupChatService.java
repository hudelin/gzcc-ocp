package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.GroupChat;

import java.util.List;

public interface GroupChatService extends BaseService<GroupChat,Long>  {


    List<GroupChat> findByUserId(Long userId) throws Exception;
}
