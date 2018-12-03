package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    public Page<User> findAll(Specification<User> spec, Pageable pageable);

    public List<User> findAll(Specification<User> spec);



//    public List<User> findByUsernameIsNotNull();



    User findByUsername(String username);
}
