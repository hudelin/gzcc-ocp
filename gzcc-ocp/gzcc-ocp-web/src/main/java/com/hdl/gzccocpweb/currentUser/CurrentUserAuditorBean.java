package com.hdl.gzccocpweb.currentUser;


import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class CurrentUserAuditorBean implements AuditorAware<Long> {

    @Autowired
    private UserService userService;

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication test = SecurityContextHolder.getContext().getAuthentication();
//        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();/*获取身份的字符串*/
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Optional<Long> id=Optional.of((long)123);

//        SecurityContext ctx = SecurityContextHolder.getContext();

//        if (ctx.getAuthentication() == null) {
//            return null;
//        }
        if (securityContext.getAuthentication().getPrincipal() == "anonymousUser") {
            return Optional.empty();
        }
        String username=securityContext.getAuthentication().getName();
        if (username != null && username!="anonymousUser" ) {
            User user= null;
            try {
                user = userService.findByUsername(username);
            } catch (Exception e) {
                e.printStackTrace();
            }
            id= Optional.of(user.getId());
            return id;
        }
//        Object principal = ctx.getAuthentication().getPrincipal();
//        if (principal.getClass().isAssignableFrom(Long.class)) {
//            return (Optional<Long>) principal;
//        } else {
//            return null;
//        }
//        return null;

        return Optional.empty();
    }
}
