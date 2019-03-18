package com.hdl.gzccocpweb.security;


import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
//    @Autowired
//    private RedisTemplate redisCacheTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByAccount(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role:user.getRoleList()){
//        for(Role role:roleList){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getAccount(), user.getPassword(),true,true,true,user.getEnabled(),authorities);
        return userDetails;
    }
}
