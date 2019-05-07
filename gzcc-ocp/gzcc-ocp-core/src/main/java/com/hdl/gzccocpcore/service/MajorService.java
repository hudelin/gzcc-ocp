package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MajorService extends BaseService<Major,Long>  {
    Major findByName(String name);

    Page<Major> findPage(Major major,Integer page, Integer size) throws Exception;

    void deleteLogic(Long id) throws Exception;

    void enabled(Major major) throws Exception;

    Major changeUser(String type, Long majorId, Long userId) throws Exception;

    void uploadVideo(Long majorId,String videoName)throws Exception;

    void deleteVideo(Long majorId, String videoName)throws Exception;

    void uploadResource(Long majorId, String resourceName)throws Exception;

    void deleteResource(Long majorId, String resourceName)throws Exception;
}
