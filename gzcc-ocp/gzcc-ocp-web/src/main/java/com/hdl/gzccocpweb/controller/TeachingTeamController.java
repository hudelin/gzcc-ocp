//package com.hdl.gzccocpweb.controller;
//
//
//import com.hdl.gzccocpcore.entity.TeachingTeam;
//import com.hdl.gzccocpcore.response.ObjectRestResponse;
//import com.hdl.gzccocpcore.service.TeachingTeamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@Controller
//@RequestMapping("/teachingTeam")
//public class TeachingTeamController {
//
//    @Autowired
//    private TeachingTeamService teachingTeamService;
//
//    @RequestMapping(value = "/save")
//    @ResponseBody
//    public ObjectRestResponse save(TeachingTeam teachingTeam) throws Exception {
//        if(teachingTeam.getId()==null){
//            teachingTeamService.save(teachingTeam);
//        }else{
//            teachingTeamService.update(teachingTeam);
//        }
//        return new ObjectRestResponse(teachingTeam);
//    }
//
//    @ResponseBody
//    @RequestMapping("/delete")
//    public ObjectRestResponse delete(Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        teachingTeamService.deleteLogic(id);
//        return new ObjectRestResponse();
//    }
//
//
//    @RequestMapping(value = "/findPage")
//    @ResponseBody
//    public ObjectRestResponse findPage(TeachingTeam teachingTeam, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
//        Page<TeachingTeam> teachingTeamPage = teachingTeamService.findPage(teachingTeam, page, size);
//        return new ObjectRestResponse(teachingTeamPage);
//    }
//
//    @RequestMapping(value = "/findListAll")
//    @ResponseBody
//    public ObjectRestResponse findListAll() throws Exception {
//        List<TeachingTeam> teachingTeamList = teachingTeamService.findAll();
//        return new ObjectRestResponse(teachingTeamList);
//    }
//
//}
