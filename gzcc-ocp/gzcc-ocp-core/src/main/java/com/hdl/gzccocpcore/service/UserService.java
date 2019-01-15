package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.User;


public interface UserService extends BaseService<User,Long>  {

    /**
    * 方法实现说明 根据用户名查找用户
    * @author      HuDeLin
    * @param      username
    * @return      用户类User
    * @exception   
    * @date        2018/12/24 12:02
    */
    User findByUsername(String username);

   User encryption(User user);

}
