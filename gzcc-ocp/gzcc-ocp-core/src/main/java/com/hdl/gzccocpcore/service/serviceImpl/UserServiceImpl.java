package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.UserRepository;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@CacheConfig
//@Cacheable(value="user")
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User update(User user) throws Exception {

        return userRepository.save(user);
    }

    @Override
    public User save(User user)  {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
//    ("user.service.findByUsername")
    public User findByUsername(String name){
        return userRepository.findByUsername(name);
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
