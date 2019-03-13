package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Major;
import org.springframework.data.domain.Page;

public interface MajorService extends BaseService<Major,Long>  {
    Major findByName(String name);

    Page<Major> findPage(Major major,Integer page, Integer size) throws Exception;

    void deleteLogic(Long id) throws Exception;
}
