package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;



    @RequestMapping(value = "/save")
    @ResponseBody
    public ObjectRestResponse save(Major major) throws Exception {
        if(major.getId()==null){
            majorService.save(major);
        }else{
           majorService.update(major);
        }
        return new ObjectRestResponse(major);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ObjectRestResponse delete(Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        majorService.deleteLogic(id);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/findPage")
    @ResponseBody
    public ObjectRestResponse findPage(Major major, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        Page<Major> pageList = majorService.findPage(major, page, size);
        return new ObjectRestResponse(pageList);
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public ObjectRestResponse findAll(Major major, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        List<Major> pageList = majorService.findAll(new Major());
        return new ObjectRestResponse(pageList);
    }


}
