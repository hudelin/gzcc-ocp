package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.repository.BaseRepository;
import com.hdl.gzccocpcore.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Transactional
public abstract class BaseServiceImpl<T , ID extends Serializable> implements BaseService<T, ID> {


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    BaseRepository<T , ID> baseRepository;

    @Autowired
    public void setRepository(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }


    @Override
    public <D> D transToDTO(T t, Class<D> clazz) throws Exception {
        if (t == null) {
            return null;
        }
        D d = clazz.newInstance();
        BeanUtils.copyProperties(t, d);
        return d;
    }

    @Override
    public <D> List<D> transToDTOList(List<T> tList, Class<D> clazz) throws Exception {
        List<D> dList = new ArrayList<>();
        if (tList != null && tList.size() > 0) {
            for (T t : tList) {
                D d = transToDTO(t, clazz);
                dList.add(d);
            }
        }
        return dList;
    }

    @Override
    public <D> D getDTO(ID id, Class<D> clazz) throws Exception {
        return transToDTO(get(id),clazz);
    }

    @Override
    public T save(T t) throws Exception {
        return baseRepository.save(t);
    }

//    @Override
//    public T update(T t) throws Exception {
//        Field[] fields = t.getClass().getDeclaredFields();
//
//        Field f=t.getClass().getDeclaredField("id");
//        f.setAccessible(true);
//        Object id = f.get(t);
//        T old= (T) baseRepository.getOne((ID) id);
//        Field[] abc = t.getClass().getDeclaredFields();
//
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            String name=field.getName();
//            Object value = field.get(t);
//            if(value!=null && !name.equals("id") ){
//
//                for(Field fold:abc){
//                    fold.setAccessible(true);
//                    String foldName=fold.getName();
//                    Object valueOld = fold.get(old);
//                    if(field.getName().equals(fold.getName())){
//                        fold.set(old,value);
//                    }
//                }
////                Field a=old.getClass().getDeclaredField(field.getName());
////                a.setAccessible(true);
////                Object valueOld = field.get(t);
//            }
//
//        }
//        return baseRepository.save(old);
//    }

    @Override
    public T get(ID id) throws Exception {
        return baseRepository.getOne(id);
    }

    @Override
    public void delete(ID id) throws Exception {
        baseRepository.deleteById(id);
    }

    @Override
    public void delete(T t) throws Exception {
        baseRepository.delete(t);
    }

    @Override
    public boolean exists(ID id) throws Exception {
        return baseRepository.existsById(id);
    }

    @Override
    public long count() throws Exception {
        return baseRepository.count();
    }

    private  List<T> findByT(T t,Sort sort) throws Exception{
        List<T> list= baseRepository.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = null;
                if (t != null) {
                    Field[] fields = t.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(t);
                            if (t != null && value != null && !"serialVersionUID".equals(field.getName()) && !"java.util.List".equals(field.getType().getName()) ) {
                                p = cb.equal(root.get(field.getName()),field.get(t));
//                                p = cb.like(root.get(field.getName()), "%" + field.get(t) + "%");
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (null != p){
                    query.where(p);
                }
                return null;
            }
        },sort);
        return list;
    }

    @Override
    public List<T> findByCondition(T t) throws Exception {
       return findByT(t,new Sort(Sort.Direction.ASC,"id"));
    }

    @Override
    public List<T> findByCondition(T t, Sort sort) throws Exception {
        return findByT(t,sort);
    }

    @Override
    public List<T> findAll() throws Exception {
        List<T> list = baseRepository.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("deleted"),false);
                if (null != predicate){
                    query.where(predicate);
                }
                return null;
            }
        });
        return list;
    }
    @Override
    public List<T> findAll(T t) throws Exception {
       List<T> list = baseRepository.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate like = null;

                if (t != null) {
                    Field[] fields = t.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(t);
                            if("deleted".equals(field.getName())){
                                like = cb.equal(root.get(field.getName()),false);
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
        });
        return list;

    }
    @Override
    public List<T> findAll(Sort sort) throws Exception {
        return baseRepository.findAll(sort);
    }

    @Override
    public Page<T> findPage(Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        return findPageByT( null,pageable);
    }

    @Override
    public Page<T> findPageByCondition(T t, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        return findPageByT(t, pageable);
    }

    @Override
    public Page<T> findPageByConditionAndSort(T t, Integer page, Integer size, Sort sort) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size, sort);
        return findPageByT(t, pageable);
    }

    private Page<T> findPageByT(T t, Pageable pageable) {
        Page<T> page = baseRepository.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate like = null;
                if (t != null) {
                    Field[] fields = t.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(t);
                            if("deleted".equals(field.getName())){
                                like = cb.equal(root.get(field.getName()),false);
                            }
                            if (t != null && value != null && !"serialVersionUID".equals(field.getName()) && !"java.util.List".equals(field.getType().getName()) ) {
                                like = cb.equal(root.get(field.getName()),false);
                            }
//                            if (null != t && null != value && !"serialVersionUID".equals(field.getName()) ) {
                                // 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
//                                if("java.lang.Boolean".equals(field.getType().getName())) {
//                                    like=cb.isFalse(root.<Boolean>get(field.getName()));
//                                }
//                                like = cb.like(root.<String>get(field.getName()), "%" + field.get(t) + "%");
//                                like = cb.equal(root.<String>get(field.getName()),field.get(t));
//                            }
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





    private <T> String getEntityName(Class<T> entityClass) {
        String entityName = entityClass.getSimpleName();
        Entity entity = entityClass.getAnnotation(Entity.class);
        if (StringUtils.isEmpty(entity.name())) {
            entityName = entity.name();
        }
        return entityName;
    }

}
