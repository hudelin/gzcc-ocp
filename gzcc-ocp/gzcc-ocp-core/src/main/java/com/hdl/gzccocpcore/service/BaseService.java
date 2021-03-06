package com.hdl.gzccocpcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T ,ID extends Serializable>{
//   BaseRepository<T, ID> getRepository();
// void setRepository(BaseRepository<T,ID> baseRepository);
    <D> D transToDTO(T t, Class<D> clazz) throws Exception;

    <D> List<D> transToDTOList(List<T> tList, Class<D> clazz) throws Exception;

    <D> D getDTO(ID id, Class<D> clazz)throws Exception;

    T save(T t) throws Exception;

    T update(T t)throws Exception;

    T get(ID id)throws Exception;

    void delete(ID id)throws Exception;

    void delete(T t)throws Exception;

    boolean exists(ID id)throws Exception;

    long count()throws Exception;

    List<T> findByCondition(T t)throws Exception;

    List<T> findByCondition(T t,Sort sort)throws Exception;

    List<T> findAll()throws Exception;

    List<T> findAll(T t)throws Exception;

    List<T> findAll(Sort sort)throws Exception;

    Page<T> findPage(Integer page, Integer size)throws Exception;

    Page<T> findPageByCondition(T t, Integer page, Integer size)throws Exception;

    Page<T> findPageByConditionAndSort(T t, Integer page, Integer size, Sort sort)throws Exception;




}
