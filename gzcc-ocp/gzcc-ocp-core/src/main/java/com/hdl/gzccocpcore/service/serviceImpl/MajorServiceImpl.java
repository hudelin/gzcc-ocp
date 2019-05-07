package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.MajorRepository;
import com.hdl.gzccocpcore.service.MajorService;
import com.hdl.gzccocpcore.service.ResourceService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class MajorServiceImpl extends BaseServiceImpl<Major, Long> implements MajorService {

    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;


    @Override
    public void deleteLogic(Long id) throws Exception {
        Major major = get(id);
        major.setDeleted(true);
        majorRepository.save(major);
    }

    @Override
    public void enabled(Major major) throws Exception {
        Major old = get(major.getId());
        if (old.getEnabled() == true) {
            old.setEnabled(!old.getEnabled());
        } else {
            if (StringUtils.isEmpty(old.getSynopsis())) {
                throw new BaseException(OcpErrorConstant.MAJOR_SYNOPSIS_NULL, "简介不存在！");
            } else if (StringUtils.isEmpty(old.getPicture())) {
                throw new BaseException(OcpErrorConstant.MAJOR_PICTURE_NULL, "课程图片不存在！");
            }
            if (StringUtils.isEmpty(old.getIntroduction())) {
                throw new BaseException(OcpErrorConstant.MAJOR_INTRODUCTION_NULL, "课程介绍不存在！");
            } else if (StringUtils.isEmpty(old.getMeans())) {
                throw new BaseException(OcpErrorConstant.MAJOR_MEANS_NULL, "教学方法不存在！");
            } else if (old.getTeacherList() == null || old.getTeacherList().size() <= 0) {
                throw new BaseException(OcpErrorConstant.MAJOR_TEACHER_NULL, "教学团队不存在！");
            } else {
                old.setEnabled(true);
            }
        }
    }

    @Override
    public Major changeUser(String type,Long majorId, Long userId) throws Exception {
        Major major=get(majorId);
        User user=userService.get(userId);
        List<User> teacherList=major.getTeacherList();
        if("add".equals(type)){
            boolean exist=false;
            for(User u:teacherList){
                if(u.getId()==userId){
                    exist=true;
                }
            }
            if(!exist){
                teacherList.add(user);
            }else{
                throw new BaseException(OcpErrorConstant.MAJOR_TEACHER_EXIST, "该教师已存在此教学团队！");
            }
        }else if("delete".equals(type)){
            List<User> userList=new ArrayList<>();
            for(User u:teacherList){
                if(u.getId()!=userId){
                    userList.add(u);
                }
            }
            major.setTeacherList(userList);
        }
        majorRepository.save(major);
        return major;
    }

    @Override
    public void uploadVideo(Long majorId,String videoName) throws Exception {
        Resource resource=resourceService.findByFormatName(videoName);
        Major major=get(majorId);
        List<Resource> resourceList=major.getVideoList();
        resourceList.add(resource);
        major.setVideoList(resourceList);
        majorRepository.save(major);
    }

    @Override
    public void deleteVideo(Long majorId, String videoName) throws Exception {
        Major major=get(majorId);
        List<Resource> resourceList=major.getVideoList();
        List<Resource> resourceListNew=new ArrayList<>();
        for(Resource resource:resourceList){
            if(!resource.getFormatName().equals(videoName)){
                resourceListNew.add(resource);
            }
        }
        major.setVideoList(resourceListNew);
        majorRepository.save(major);
    }

    @Override
    public void uploadResource(Long majorId, String resourceName) throws Exception {
        Resource resource=resourceService.findByFormatName(resourceName);
        Major major=get(majorId);
        List<Resource> resourceList=major.getResourceList();
        resourceList.add(resource);
        major.setResourceList(resourceList);
        majorRepository.save(major);
    }

    @Override
    public void deleteResource(Long majorId, String resourceName) throws Exception {
        Major major=get(majorId);
        List<Resource> resourceList=major.getResourceList();
        List<Resource> resourceListNew=new ArrayList<>();
        for(Resource resource:resourceList){
            if(!resource.getFormatName().equals(resourceName)){
                resourceListNew.add(resource);
            }
        }
        major.setResourceList(resourceListNew);
        majorRepository.save(major);
    }

    @Override
    public Major update(Major major) throws Exception {
        Major old = majorRepository.getOne(major.getId());
        if (!StringUtils.isEmpty(major.getName())) {
            old.setName(major.getName());
        }
        if (!StringUtils.isEmpty(major.getIntroduction())) {
            old.setIntroduction(major.getIntroduction());
        }
        if (!StringUtils.isEmpty(major.getMeans())) {
            old.setMeans(major.getMeans());
        }
        if (!StringUtils.isEmpty(major.getPicture())) {
            old.setPicture(major.getPicture());
        }
        if (!StringUtils.isEmpty(major.getSynopsis())) {
            old.setSynopsis(major.getSynopsis());
        }
        return majorRepository.save(old);
    }

    @Override
    public Major findByName(String name) {
        return majorRepository.findByName(name);
    }

    @Override
    public Page<Major> findPage(Major major, Integer page, Integer size) throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Major> pageList = findPageByT(major, pageable);
        return pageList;
    }

    private Page<Major> findPageByT(Major major, Pageable pageable) {
        Page<Major> page = majorRepository.findAll(new Specification<Major>() {
            @Override
            public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("deleted"), false);
                if (major != null) {
                    if (!StringUtils.isEmpty(major.getName())) {
                        predicate = cb.and(cb.like(root.get("name"), "%" + major.getName() + "%"), predicate);
                    }
                    if (major.getEnabled() != null) {
                        predicate = cb.and(cb.equal(root.get("enabled"), major.getEnabled()), predicate);
                    }
                }
                if (null != predicate) {
                    query.where(predicate);
                }
                return null;
            }
        }, pageable);
        return page;
    }

}
