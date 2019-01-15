package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.repository.FriendRepository;
import com.hdl.gzccocpcore.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend,Long> implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Friend update(Friend friend) throws Exception {
        return friendRepository.save(friend);
    }

    @Override
    public List<Friend> findByUserId(Long userId) throws Exception {
        return friendRepository.findByUserId(userId);
    }
}
