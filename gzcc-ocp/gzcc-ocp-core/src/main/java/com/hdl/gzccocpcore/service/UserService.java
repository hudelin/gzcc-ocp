package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService extends BaseService<User, Long> {

    /**
     * 方法实现说明 根据用户名查找用户
     *
     * @param username
     * @return 用户类User
     * @throws
     * @author HuDeLin
     * @date 2018/12/24 12:02
     */
    User findByUsername(String username) throws Exception;

    User saveUser(User user)throws Exception;

    User saveTeacher(User user)throws Exception;

    User findByAccount(String account) throws Exception;

    User collectNote(Long userId, Long noteId) throws Exception;

    User removeNote(Long userId, Long noteId) throws Exception;

    List<User> findByGroupChatId(Long groupChatId)throws Exception;

    void deleteLogic(Long id)throws Exception;

    Page<User> findAllUser(User user, Integer page, Integer size)throws Exception;

    Page<User> findAllTeacher(User user, Integer page, Integer size)throws Exception;


}
