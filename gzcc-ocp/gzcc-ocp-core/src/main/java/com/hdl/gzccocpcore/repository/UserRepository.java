package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.management.relation.RoleList;
import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    public Page<User> findByRoleListId(Pageable pageable, Long roleListId);

    public List<User> findAll(Specification<User> spec);

//    public List<User> findByUsernameIsNotNull();

    User findByUsername(String username);

    User findByAccount(String account);

    List<User> findByGroupChatListId(Long groupChatId);




}
