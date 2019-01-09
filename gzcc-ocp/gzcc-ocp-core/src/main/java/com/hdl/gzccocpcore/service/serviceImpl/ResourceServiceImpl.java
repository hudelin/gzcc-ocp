package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.entity.Resource;

import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.ResourceRepository;
import com.hdl.gzccocpcore.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ResourceServiceImpl extends BaseServiceImpl<Resource,Long> implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource update(Resource resource) throws Exception {
        return null;
    }

    @Override
    public Resource uploadResource(MultipartFile multipartFile) throws Exception {

        if(multipartFile==null ||multipartFile.isEmpty()){
            throw new BaseException(OcpConstant.RESOURCE_IS_NULL, "系统错误！");
        }
        return null;
    }
}
