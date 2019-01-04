package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpweb.response.ObjectRestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile file){
//        try {  /*获取文件的后缀，对文件进行重命名*/
//            String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String fileName = user.getName() + "." + prefix;
//            File f = new File(rootPath + fileName);
//            file.transferTo(f);
//            user.setPicture("/uploads/" + fileName);
//            userRepository.save(user);
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        }
        Resource resource=new Resource();
        resource.setSrc("https://res.layui.com/static/images/layui/logo.png");
        ObjectRestResponse uploadResponse=new ObjectRestResponse(resource);
        return uploadResponse;
    }
}
