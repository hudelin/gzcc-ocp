package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.UserRepository;
import com.hdl.gzccocpcore.service.RoleService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Page<User> findAllUser(User user, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        return userRepository.findByRoleListId(pageable,roleService.getUserRole().getId());
    }

    @Override
    public Page<User> findAllTeacher(User user, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        return userRepository.findByRoleListId(pageable,roleService.getTeacherRole().getId());
    }
    //    @Override
//    @Transactional(readOnly = true)
//    public Page<User> findAll(Integer page, Integer size) {
//        Sort sort = new Sort(Sort.Direction.ASC,"id");
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<User> userEntityPage= userRepository.findAll(pageable);
//        List<User> ordinaryUserList = userRepository.findAll();
//        return userEntityPage;
//
//    }
//
//    @Override
//    public Page<User> findPageAllDynamic(User ordinaryUser, Integer page, Integer size) {
//        Sort sort = new Sort(Sort.Direction.ASC,"id");
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        return userRepository.findAll(new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//
//                Predicate stuNameLike = null;
//                if(null != ordinaryUser && !StringUtils.isEmpty(ordinaryUser.getUsername())) {
//                // 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
//                    stuNameLike = cb.like(root.<String> get("name"), "%" + ordinaryUser.getUsername() + "%");
//                }
//
//
//                if(null != stuNameLike) query.where(stuNameLike);
//                return null;
//            }
//        },pageable);
//    }
//
//    @Override
//    public List<User> findAllDynamic(User ordinaryUser) {
//        return userRepository.findAll(new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//
//                Predicate stuNameLike = null;
//                if(null != ordinaryUser && !StringUtils.isEmpty(ordinaryUser.getUsername())) {
//                    // 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
//                    stuNameLike = cb.like(root.<String> get("name"), "%" + ordinaryUser.getUsername() + "%");
//                }
//
//                if(null != stuNameLike) query.where(stuNameLike);
//                return null;
//            }
//        });
//    }


}
