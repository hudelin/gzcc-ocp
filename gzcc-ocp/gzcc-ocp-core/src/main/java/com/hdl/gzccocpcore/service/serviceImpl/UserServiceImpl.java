package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.constant.BaseConstant;
import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.UserRepository;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User update(User user) throws Exception {
        User userOld = get(user.getId());
//        if(!StringUtils.isEmpty(user.getNickname())){
        userOld.setUsername(user.getUsername());
//        }
        if (!StringUtils.isEmpty(user.getAvatar())) {
            userOld.setAvatar(user.getAvatar());
        }
        userOld.setCollectNote(user.getCollectNote());
        userOld.setGender(user.getGender());
        return userRepository.save(userOld);
    }

    @Override
    public User save(User user) throws Exception {
        if (this.findByAccount(user.getAccount()) != null) {
            throw new BaseException(OcpErrorConstant.ACCOUNT_EXIST, "账号已存在！");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
//    ("user.service.findByUsername")
    public User findByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User encryption(User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public void collectNote(Long userId, Long noteId) throws Exception {
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
    }

    @Override
    public void removeNote(Long userId, Long noteId) throws Exception {
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
