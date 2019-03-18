//package com.hdl.gzccocpcore.service.serviceImpl;
//
//import com.hdl.gzccocpcore.entity.Major;
//import com.hdl.gzccocpcore.entity.TeachingTeam;
//import com.hdl.gzccocpcore.repository.MajorRepository;
//import com.hdl.gzccocpcore.repository.TeachingTeamRepository;
//import com.hdl.gzccocpcore.service.TeachingTeamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.lang.reflect.Field;
//
//@Service
//public class TeachingTeamServiceImpl extends BaseServiceImpl<TeachingTeam, Long> implements TeachingTeamService {
//
//    @Autowired
//    private TeachingTeamRepository teachingTeamRepository;
//
//    @Override
//    public void deleteLogic(Long id) throws Exception {
//        TeachingTeam teachingTeam=get(id);
//        teachingTeam.setDeleted(true);
//        teachingTeamRepository.save(teachingTeam);
//    }
//
//    @Override
//    public TeachingTeam update(TeachingTeam teachingTeam) throws Exception {
//        TeachingTeam old = get(teachingTeam.getId());
//        if(!StringUtils.isEmpty(teachingTeam.getName())){
//            old.setName(teachingTeam.getName());
//        }
//        return teachingTeamRepository.save(teachingTeam);
//    }
//
//    @Override
//    public TeachingTeam findByName(String name) {
//        return teachingTeamRepository.findByName(name);
//    }
//
//    @Override
//    public Page<TeachingTeam> findPage(TeachingTeam teachingTeam, Integer page, Integer size) throws Exception {
//        Sort sort=new Sort(Sort.Direction.ASC,"id");
//        Pageable pageable = PageRequest.of(page-1, size,sort);
//        Page<TeachingTeam> teachingTeamPage = findPageByT(teachingTeam, pageable);
//        return teachingTeamPage;
//    }
//
//    private Page<TeachingTeam> findPageByT(TeachingTeam teachingTeam, Pageable pageable) {
//        Page<TeachingTeam> teachingTeamPage = teachingTeamRepository.findAll(new Specification<TeachingTeam>() {
//            @Override
//            public Predicate toPredicate(Root<TeachingTeam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate predicate  = cb.equal(root.get("deleted"),false);
//                if(null != teachingTeam ) {
//                    if(!StringUtils.isEmpty(teachingTeam.getName())){
//                        predicate = cb.and(cb.like(root.get("name"), "%" + teachingTeam.getName() + "%"),predicate);
//                    }
//                }
//                if(null != predicate) query.where(predicate);
//                return null;
//            }
//        },pageable);
//        return teachingTeamPage;
//    }
//
//}
