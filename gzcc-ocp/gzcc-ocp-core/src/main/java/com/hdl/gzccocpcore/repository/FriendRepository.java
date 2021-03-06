package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.Friend;

import java.util.List;

public interface FriendRepository extends BaseRepository<Friend, Long> {

    List<Friend> findByUserIdAndDeleted(Long userId,Boolean deleted);

    Friend findByUserIdAndListIdAndDeleted(Long userId,Long listId,Boolean deleted);
}
