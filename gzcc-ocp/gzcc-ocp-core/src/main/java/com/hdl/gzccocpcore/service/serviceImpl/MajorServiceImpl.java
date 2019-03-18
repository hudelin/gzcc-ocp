package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.repository.MajorRepository;
import com.hdl.gzccocpcore.service.MajorService;
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

@Service
public class MajorServiceImpl extends BaseServiceImpl<Major, Long> implements MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Override
    public void deleteLogic(Long id) throws Exception {
        Major major=get(id);
        major.setDeleted(true);
        majorRepository.save(major);
    }

    @Override
    public Major update(Major major) throws Exception {
        Major old =majorRepository.getOne(major.getId());
        if(!StringUtils.isEmpty(major.getName()) ){
            old.setName(major.getName());
        }
        if(!StringUtils.isEmpty(major.getIntroduction())){
            old.setIntroduction(major.getIntroduction());
        }
        if(!StringUtils.isEmpty(major.getMeans())){
            old.setMeans(major.getMeans());
        }
        return majorRepository.save(old);
    }

    @Override
    public Major findByName(String name) {
        return majorRepository.findByName(name);
    }

    @Override
    public Page<Major> findPage(Major major, Integer page, Integer size) throws Exception {
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<Major> pageList = findPageByT(major, pageable);
        return pageList;
    }

    private Page<Major> findPageByT(Major major, Pageable pageable) {
        Page<Major> page = majorRepository.findAll(new Specification<Major>() {
            @Override
            public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate deleted = null;
                Predicate like = null;
                if (major != null) {
                    Field[] fields = major.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(major);
                            if("deleted".equals(field.getName())){
                                if(like!=null){
                                    like = cb.and(cb.equal(root.get(field.getName()),false),like);
                                }else{
                                    like = cb.and(cb.equal(root.get(field.getName()),false));
                                }

                            }
                            if (major != null && value != null && !"serialVersionUID".equals(field.getName()) && !"java.util.List".equals(field.getType().getName()) ) {
                                if("name".equals(field.getName())){
                                    if(like!=null){
                                        like = cb.and(cb.like(root.get(field.getName()), "%" + value + "%"),like);
                                    }else{
                                        like = cb.and(cb.like(root.get(field.getName()), "%" + value + "%"));
                                    }

                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (null != like){
                    query.where(like);
                }
                return null;
            }
        }, pageable);
        return page;
    }

}
