package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.repository.RoleRepository;
import com.hdl.gzccocpcore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role update(Role role) throws Exception {
        return roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role getUserRole() throws Exception {
        return roleRepository.findByName(OcpConstant.ROLE_USER);
    }

    @Override
    public Role getTeacherRole() throws Exception {
        return roleRepository.findByName(OcpConstant.ROLE_TEACHER);
    }

    @Override
    public Role getAdministratorRole() throws Exception {
        return roleRepository.findByName(OcpConstant.ROLE_ADMINISTRATOR);
    }
}
