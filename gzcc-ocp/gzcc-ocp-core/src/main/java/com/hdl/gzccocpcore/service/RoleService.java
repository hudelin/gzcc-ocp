package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Role;

public interface RoleService extends BaseService<Role,Long>  {
    Role findByName(String name);
}
