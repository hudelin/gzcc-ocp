package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService extends BaseService<Resource,Long>  {

    public Resource uploadResource(MultipartFile multipartFile,Long userId,String resourceType) throws Exception;

    public Resource findByFormatName(String src) throws Exception;
}
