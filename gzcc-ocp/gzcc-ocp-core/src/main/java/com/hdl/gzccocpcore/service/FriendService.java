package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Friend;

import java.util.List;

public interface FriendService extends BaseService<Friend,Long>  {
    List<Friend> findByUserId(Long userId) throws Exception;
}
