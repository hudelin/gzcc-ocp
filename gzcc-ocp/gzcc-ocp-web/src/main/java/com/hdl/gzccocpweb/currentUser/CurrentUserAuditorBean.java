//package com.hdl.gzccocpweb.currentUser;
//
//
//import com.hdl.gzccocpcore.entity.User;
//import com.hdl.gzccocpcore.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//@Configuration
//public class CurrentUserAuditorBean implements AuditorAware<Long> {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//
//        Authentication test = SecurityContextHolder.getContext().getAuthentication();
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//
//        Optional<Long> id;
//
//        if (securityContext.getAuthentication().getPrincipal() == "anonymousUser") {
//            return Optional.empty();
//        }
//        String username=securityContext.getAuthentication().getName();
//        if (username != null && username!="anonymousUser" ) {
//            User user = userService.findByUsername(username);
//            Long userId=user.getId();
//
//            if(user!=null){
//                id= Optional.of(user.getId());
//                return id;
//            }
//
//        }
//
//
//        return Optional.empty();
//    }
//}
