package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.User;


public interface UserService extends BaseService<User,Long>  {

    User findByUsername(String username);

}
