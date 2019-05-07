package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.FriendRepository;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend,Long> implements FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserService userService;

    @Override
    public Friend update(Friend friend) throws Exception {
        return friendRepository.save(friend);
    }

    @Override
    public Friend createFriend(Long userId) throws Exception {
        Friend friend=new Friend();
        friend.setGroupname("我的好友");
        friend.setUser(userService.get(userId));
        friendRepository.save(friend);
        return friend;
    }

    @Override
    public List<Friend> findByUserId(Long userId) throws Exception {
        return friendRepository.findByUserIdAndDeleted(userId,false);
    }

    @Override
    public void addFriend(Long userId, Long id) throws Exception {
        Friend friend=get(id);
        List<User> userList = friend.getList();
        boolean exist=false;
        for(User user:userList){
            if(user.getId()==userId){
                exist=true;
            }
        }
        if(!exist){
            userList.add(userService.get(userId));
            friend.setList(userList);
            friendRepository.save(friend);
        }
    }

    @Override
    public void moveFriend(Long userId, Long id,Long myId) throws Exception {
        Friend friendOld = friendRepository.findByUserIdAndListIdAndDeleted(myId, userId,false);
        if(friendOld.getId()!=id){
            deleteFriend(userId,myId);
            addFriend(userId,id);
        }
    }

    @Override
    public void deleteFriend(Long userId, Long myId) throws Exception {
        Friend friend = friendRepository.findByUserIdAndListIdAndDeleted(myId, userId,false);
        List<User> userList = friend.getList();
        List<User> list = new ArrayList<>();
        for(User user:userList){
            if(user.getId()!=userId){
                list.add(user);
            }
        }
        friend.setList(list);
        friendRepository.save(friend);
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        Friend friend = get(id);
        friend.setList(new ArrayList<>());
        friend.setDeleted(true);

    }

    @Override
    public Friend createGroup(Long id, String groupname) throws Exception {
        Friend friend=new Friend();
        friend.setGroupname(groupname);
        friend.setUser(userService.get(id));
        friendRepository.save(friend);
        return friend;
    }

    @Override
    public void changeGroupName(Long id,String groupname) throws Exception {
        Friend friend = get(id);
        friend.setGroupname(groupname);
        friendRepository.save(friend);
    }


}
