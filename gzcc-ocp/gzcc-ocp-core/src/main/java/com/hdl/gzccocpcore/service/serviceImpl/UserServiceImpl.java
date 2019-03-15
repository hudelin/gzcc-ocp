package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.UserRepository;
import com.hdl.gzccocpcore.service.RoleService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
//@CacheConfig
//@Cacheable(value="user")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Value("${avatar.path}")
    private String avatarPath;

    @Override
    public User update(User user) throws Exception {
        User userOld = get(user.getId());
        if(!StringUtils.isEmpty(user.getAccount())){
            userOld.setAccount(user.getAccount());
        }
        if (!StringUtils.isEmpty(user.getAvatar())) {
            userOld.setAvatar(user.getAvatar());
        }
        if (!StringUtils.isEmpty(user.getCollectNote())) {
            userOld.setCollectNote(user.getCollectNote());
        }
        if (!StringUtils.isEmpty(user.getUsername())) {
            userOld.setUsername(user.getUsername());
        }
        userOld.setGender(user.getGender());
        userOld.setSign(user.getSign());
        return userRepository.save(userOld);
    }

    @Override
    public User save(User user) throws Exception {
        if (this.findByAccount(user.getAccount()) != null) {
            throw new BaseException(OcpErrorConstant.ACCOUNT_EXIST, "账号已存在！");
        }
        if(!StringUtils.isEmpty(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            user.setPassword(passwordEncoder.encode("123456"));
        }
        if(StringUtils.isEmpty(user.getAvatar())){
           user.setAvatar(avatarPath);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User saveUser(User user) throws Exception {
        List<Role> roleList=new ArrayList<>();
        roleList.add(roleService.getUserRole());
        user.setRoleList(roleList);
        return save(user);
    }

    @Override
    public User saveTeacher(User user) throws Exception {
        List<Role> roleList=new ArrayList<>();
        roleList.add(roleService.getTeacherRole());
        user.setRoleList(roleList);
        return save(user);
    }

    @Override
//    ("user.service.findByUsername")
    public User findByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        return user;
    }




    @Override
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public User collectNote(Long userId, Long noteId) throws Exception {
        User user = get(userId);
        if (!StringUtils.isEmpty(user.getCollectNote())) {
            String[] noteIdList = user.getCollectNote().split(",");
            List<String> list = new ArrayList<>();
            for (String s : noteIdList) {
                if(Long.valueOf(s)==noteId){
                    throw new BaseException(OcpErrorConstant.ACCOUNT_NOTE_COLLECT,"帖子已收藏");
                }
                list.add(s);
            }
            list.add(noteId.toString());
            noteIdList = list.toArray(new String[list.size()]);
            user.setCollectNote(StringUtils.arrayToDelimitedString(noteIdList, ","));
        } else {
            user.setCollectNote(noteId.toString());
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User removeNote(Long userId, Long noteId) throws Exception {
        User user = get(userId);
        if (!StringUtils.isEmpty(user.getCollectNote())) {
            String[] noteIdList = user.getCollectNote().split(",");
            List<String> list = new ArrayList<>();
            for (String s : noteIdList) {
                if (noteId != Long.valueOf(s)) {
                    list.add(s);
                }
            }
            noteIdList = list.toArray(new String[list.size()]);
            user.setCollectNote(StringUtils.arrayToDelimitedString(noteIdList, ","));
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public List<User> findByGroupChatId(Long groupChatId) throws Exception {
        return userRepository.findByGroupChatListId(groupChatId);
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        User user=get(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

//    @Override
//    public Page<User> findAllUser(User user, Integer page, Integer size) throws Exception {
//        Pageable pageable = PageRequest.of(page-1, size);
//
//        return userRepository.findByRoleListId(pageable,roleService.getUserRole().getId());
//    }

    @Override
    public Page<User> findAllTeacher(User user, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> userPage= findAll(user,pageable,roleService.getTeacherRole().getName());
        return userPage;
    }

    @Override
    public Page<User> findAllUser(User user, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> userPage= findAll(user,pageable,roleService.getUserRole().getName());
        return userPage;
    }

    //动态查询某个角色的用户
    private Page<User> findAll(User user,Pageable pageable,String roleName)throws Exception {
        Page<User> userPage= userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate  = cb.equal(root.get("deleted"),false);
                if(!StringUtils.isEmpty(roleName)){
                    try {
                        ListJoin<User,Role> join= root.join(root.getModel().getList("roleList",Role.class), JoinType.LEFT);
                        predicate = cb.and(cb.equal(join.get("name"), roleName),predicate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(null != user ) {
                    if(!StringUtils.isEmpty(user.getUsername())){
                        predicate = cb.and(cb.like(root.get("username"), "%" + user.getUsername() + "%"),predicate);
                    }

                }
                if(null != predicate) query.where(predicate);
                return null;
            }
        },pageable);
        return userPage;
    }


}
