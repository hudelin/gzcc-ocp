package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.response.ObjectRestResponse;


import com.hdl.gzccocpcore.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/resource")
public class ResourceController {



    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile file,Long userId,String type) throws Exception {

        Resource resource=resourceService.uploadResource(file,userId,type);
        resource.setSrc("https://res.layui.com/static/images/layui/logo.png");
        return new ObjectRestResponse(resource);
    }
}
