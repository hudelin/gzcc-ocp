package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Friend;

import java.util.List;

public interface FriendService extends BaseService<Friend, Long> {

    Friend createFriend(Long userId) throws Exception;

    List<Friend> findByUserId(Long userId) throws Exception;

    void addFriend(Long userId, Long id) throws Exception;

    void moveFriend(Long userId,Long id,Long myId) throws Exception;

    void deleteFriend(Long userId, Long myId) throws Exception;

    void deleteLogic(Long id) throws Exception;

    Friend createGroup(Long id,String groupname) throws Exception;

    void changeGroupName(Long id,String groupname) throws Exception;
}
