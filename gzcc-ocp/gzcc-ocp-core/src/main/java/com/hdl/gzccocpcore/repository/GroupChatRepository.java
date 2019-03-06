package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.User;

import java.util.List;

public interface GroupChatRepository extends BaseRepository<GroupChat, Long> {

    List<GroupChat> findByUserListId(Long userId);


}
