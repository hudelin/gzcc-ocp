package com.hdl.gzccocpcore.repository;


import com.hdl.gzccocpcore.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long>{


    Role findByName(String name);
}
