package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.FriendRepository;
import com.hdl.gzccocpcore.repository.GroupChatRepository;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupChatServiceImpl extends BaseServiceImpl<GroupChat,Long> implements GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Override
    public GroupChat update(GroupChat friend) throws Exception {
        return groupChatRepository.save(friend);
    }

    @Override
    public List<GroupChat> findByUserId(Long userId) throws Exception {
        List<GroupChat> groupChatList=groupChatRepository.findByUserListId(userId);
        return groupChatList;
    }
}
