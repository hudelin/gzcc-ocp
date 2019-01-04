package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.entity.Resource;
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
        return null;
    }
}
