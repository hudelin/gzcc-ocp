package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ResourceService;
import com.hdl.gzccocpcore.service.UserService;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private ResourceService resourceService;

    private RequestCache requestCache=new HttpSessionRequestCache();

    private String header="/user";

    private String login=header+"/login.btl";
    private String home=header+"/home.btl";
    private String index=header+"/index.btl";
    private String set=header+"/set.btl";
    private String message=header+"/message.btl";
    private String forget=header+"/forget.btl";
    private String register=header+"/register.btl";


//    @Autowired
//    private RedisTemplate redisCacheTemplate;
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String targetUrl="/";
        try {
            targetUrl = savedRequest.getRedirectUrl();
        }catch (Exception e){

        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",targetUrl);
        mv.setViewName(login);
        return mv;
    }

    @RequestMapping(value = "/home")
    public ModelAndView home(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(home);
        return mv;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(index);
        return mv;
    }

    @RequestMapping(value = "/set")
    public ModelAndView set(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username=securityContext.getAuthentication().getName();
        mv.addObject("user",userService.findByUsername(username));
        mv.setViewName(set);
        return mv;
    }

    @RequestMapping(value = "/message")
    public ModelAndView message(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(message);
        return mv;
    }

    @RequestMapping(value = "/forget")
    public ModelAndView forget(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(forget);
        return mv;
    }
    @RequestMapping(value = "/register")
    public ModelAndView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(register);
        return mv;
    }


    @RequestMapping("/getDetail")
    @ResponseBody
    public ObjectRestResponse getDetail() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username=securityContext.getAuthentication().getName();
        User user=userService.findByAccount(username);
        return new ObjectRestResponse(user);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {
//        String imageCode = (String) redisCacheTemplate.opsForValue().get("imageCode");
        List<User> userList=userService.findAll();
        return userList;
    }

    @ResponseBody
    @RequestMapping("/findByUsername")
    public User findByUsername(String username) throws Exception {
        User user=userService.findByUsername(username);
        return user;
    }

    @RequestMapping(value = "/findUserPage")
    @ResponseBody
    public Page<User> findNotePage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, String orderType) throws Exception {
        User user=new User();
        Page<User> notePage = userService.findPageByConditionAndSort(user,page, size,new Sort(Sort.Direction.ASC, "id"));
        return notePage;
    }

    @ResponseBody
    @RequestMapping("/save")
    public ObjectRestResponse save( User user,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if(user.getId()!=null){
            user=userService.update(user);
            httpServletRequest.getSession().setAttribute("user",userService.get(user.getId()));
        }else{
            user=userService.save(user);
        }
        return new ObjectRestResponse(user);
    }

//    @ResponseBody
//    @RequestMapping("/update")
//    public ObjectRestResponse update( User user,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        return new ObjectRestResponse(user);
//    }

    @ResponseBody
    @RequestMapping("/delete")
    public ObjectRestResponse delete( Long id,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        userService.deleteLogic(id);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/registerUser")
    public ObjectRestResponse registerUser( User user) throws Exception {
        user=userService.save(user);
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/send")
    public ObjectRestResponse send(String message) throws Exception {
        List<User> userList = userService.findAll();
        List<User> test=new ArrayList<>();
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BeanUtils.copyProperties(userList,test);
        objectRestResponse.data(userList);
        return objectRestResponse;
    }

    @ResponseBody
    @RequestMapping("/collectNote")
    public ObjectRestResponse collectNote(Long userId,Long noteId) throws Exception {
        User user= userService.collectNote(userId,noteId);
        return new ObjectRestResponse();
    }
    @ResponseBody
    @RequestMapping("/removeNote")
    public ObjectRestResponse removeNote(Long userId,Long noteId) throws Exception {
        User user= userService.removeNote(userId,noteId);
        return new ObjectRestResponse(user);
    }



    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile multipartFile, Long userId,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Resource resource=resourceService.uploadResource(multipartFile,userId, OcpConstant.RESOURCE_TYPE_USER_AVATAR);
        return new ObjectRestResponse(resource);
    }

    @RequestMapping("/test")
    @ResponseBody
    public ObjectRestResponse test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        User user=new User();
        List<User> userList=userService.findByCondition(user,new Sort(Sort.Direction.ASC,"lastModifiedTime"));
        List<User> u=userService.transToDTOList(userList,User.class);
        return new ObjectRestResponse(u);
    }
}
