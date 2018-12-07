package com.hdl.gzccocpweb.currentUser;


import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {
    @Autowired
    private UserService userService;

    public Long getUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username=securityContext.getAuthentication().getName();
        User user=userService.findByUsername(username);
        return user.getId();
    }

}
