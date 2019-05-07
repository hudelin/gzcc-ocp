package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Comment;
import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.CommentService;
import com.hdl.gzccocpcore.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("index",1);
        mv.setViewName("/major/major.btl");
        return mv;
    }

    @RequestMapping(value = "/{majorId}")
    public ModelAndView majorIndex( @PathVariable Long majorId,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("majorId",majorId);
        mv.setViewName("/major/index.btl");
        return mv;
    }


    @RequestMapping(value = "/content/{majorId}")
    public ModelAndView content(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long majorId) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("majorId",majorId);
        mv.setViewName("/major/content.btl");
        return mv;
    }

    @RequestMapping(value = "/video/{majorId}/{resourceId}")
    public ModelAndView video( @PathVariable Long resourceId, @PathVariable Long majorId,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("resourceId",resourceId);
        mv.addObject("majorId",majorId);
        mv.setViewName("/major/video.btl");
        return mv;
    }

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

    @RequestMapping(value = "/findById")
    @ResponseBody
    public ObjectRestResponse enabled(Long majorId) throws Exception {
        return new ObjectRestResponse(majorService.get(majorId));
    }

    @RequestMapping(value = "/enabled")
    @ResponseBody
    public ObjectRestResponse enabled(Major major) throws Exception {
        majorService.enabled(major);
        return new ObjectRestResponse(major);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ObjectRestResponse delete(Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        majorService.deleteLogic(id);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/changeUser")
    public ObjectRestResponse changeUser(String type,Long majorId, Long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Major major=majorService.changeUser(type,majorId,userId);
        return new ObjectRestResponse(major);
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

    @RequestMapping(value = "/uploadVideo")
    @ResponseBody
    public ObjectRestResponse uploadVideo(Long majorId,String videoName, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        majorService.uploadVideo(majorId,videoName);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/deleteVideo")
    @ResponseBody
    public ObjectRestResponse deleteVideo(Long majorId,String videoName, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        majorService.deleteVideo(majorId,videoName);
        return new ObjectRestResponse();
    }
    @RequestMapping(value = "/uploadResource")
    @ResponseBody
    public ObjectRestResponse uploadResource(Long majorId,String resourceName, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        majorService.uploadResource(majorId,resourceName);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/deleteResource")
    @ResponseBody
    public ObjectRestResponse deleteResource(Long majorId,String resourceName, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        majorService.deleteResource(majorId,resourceName);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/comment")
    @ResponseBody
    public ObjectRestResponse deleteResource(Long resourceId) throws Exception {
        List<Comment> commentList=commentService.findByResourceId(resourceId);
        return new ObjectRestResponse(commentList);
    }




}
