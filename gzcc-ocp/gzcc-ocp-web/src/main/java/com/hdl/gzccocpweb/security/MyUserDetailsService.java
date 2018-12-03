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
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findByUsername(name);
//        redisCacheTemplate.opsForValue().set("qwe", user);
//        User map2 = (User) redisCacheTemplate.opsForValue().get("qwe");
//        System.out.println(map2.getUsername());
//        List<Role> roleList=roleRepository.findByUserList(user);
//        List<Role> roleList=user.getRoleList();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role:user.getRoleList()){
//        for(Role role:roleList){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }
}
