package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Role;

public interface RoleService extends BaseService<Role,Long>  {
    Role findByName(String name);

    Role getUserRole() throws Exception;

    Role getTeacherRole() throws Exception;

    Role getAdministratorRole() throws Exception;
}
