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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    private RequestCache requestCache = new HttpSessionRequestCache();

    private String header = "/user";

    private String login = header + "/login.btl";
    private String home = header + "/home.btl";
    private String note = header + "/note.btl";
    private String set = header + "/set.btl";
    private String message = header + "/message.btl";
    private String forget = header + "/forget.btl";
    private String register = header + "/register.btl";


    //    @Autowired
//    private RedisTemplate redisCacheTemplate;
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String targetUrl = "/";
        try {
            targetUrl = savedRequest.getRedirectUrl();
        } catch (Exception e) {

        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", targetUrl);
        mv.setViewName(login);
        return mv;
    }

    @RequestMapping(value = "/home/{userId}")
    public ModelAndView home(@PathVariable Long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userId", userId);
        mv.setViewName(home);
        return mv;
    }

    @RequestMapping(value = "/note")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(note);
        return mv;
    }

    @RequestMapping(value = "/set")
    public ModelAndView set(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        mv.addObject("user", userService.findByUsername(username));
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
        String username = securityContext.getAuthentication().getName();
        User user = userService.findByAccount(username);
        return new ObjectRestResponse(user);
    }

    @RequestMapping("/findByUserId")
    @ResponseBody
    public ObjectRestResponse findByUserId(Long userId) throws Exception {
        User user = userService.get(userId);
        return new ObjectRestResponse(user);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {
        List<User> userList = userService.findAll();
        return userList;
    }

    @ResponseBody
    @RequestMapping("/findByUsername")
    public User findByUsername(String username) throws Exception {
        User user = userService.findByUsername(username);
        return user;
    }

    @RequestMapping(value = "/findUserPage")
    @ResponseBody
    public Page<User> findUserPage( User user ,@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, String orderType) throws Exception {
//        Page<User> notePage = userService.findPageByConditionAndSort(user,page, size,new Sort(Sort.Direction.ASC, "id"));
        Page<User> notePage = userService.findAllUser(user, page, size);
        return notePage;
    }

    @RequestMapping(value = "/findTeacherPage")
    @ResponseBody
    public Page<User> findTeacherPage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, String orderType) throws Exception {
        User user = new User();
        Page<User> userPage = userService.findAllTeacher(user, page, size);
        return userPage;
    }

    @ResponseBody
    @RequestMapping("/save")
    public ObjectRestResponse save(User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (user.getId() != null) {
            user = userService.update(user);
            httpServletRequest.getSession().setAttribute("user", userService.get(user.getId()));
        } else {
            user = userService.save(user);
        }
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/saveUser")
    public ObjectRestResponse saveUser(User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (user.getId() != null) {
            user = userService.update(user);
        } else {
            user = userService.saveUser(user);
        }
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/saveTeacher")
    public ObjectRestResponse saveTeacher(User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (user.getId() != null) {
            user = userService.update(user);
        } else {
            user = userService.saveTeacher(user);
        }
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ObjectRestResponse delete(Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        userService.deleteLogic(id);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/registerUser")
    public ObjectRestResponse registerUser(User user) throws Exception {
        user = userService.save(user);
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/send")
    public ObjectRestResponse send(String message) throws Exception {
        List<User> userList = userService.findAll();
        List<User> test = new ArrayList<>();
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BeanUtils.copyProperties(userList, test);
        objectRestResponse.data(userList);
        return objectRestResponse;
    }

    @ResponseBody
    @RequestMapping("/collectNote")
    public ObjectRestResponse collectNote(Long userId, Long noteId) throws Exception {
        User user = userService.collectNote(userId, noteId);
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/removeNote")
    public ObjectRestResponse removeNote(Long userId, Long noteId) throws Exception {
        User user = userService.removeNote(userId, noteId);
        return new ObjectRestResponse(user);
    }

    @ResponseBody
    @RequestMapping("/find")
    public ObjectRestResponse find(String text) throws Exception {
        List<User> userList=userService.find(text);
        return new ObjectRestResponse(userList);
    }

    @ResponseBody
    @RequestMapping("/findTeacher")
    public ObjectRestResponse findTeacher(String text) throws Exception {
        List<User> userList=userService.findTeacher(text);
        return new ObjectRestResponse(userList);
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    private ObjectRestResponse changePassword(Long id,String password,String newPassword, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        userService.changePassword(id,password,newPassword);
        return new ObjectRestResponse();
    }



    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile multipartFile, Long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Resource resource = resourceService.uploadResource(multipartFile, userId, OcpConstant.RESOURCE_TYPE_USER_AVATAR);
        return new ObjectRestResponse(resource);
    }

    @Value("${gzcc.ocp.web.root-path}")
    private String rootPath;
    @RequestMapping("/test")
    @ResponseBody
    public ObjectRestResponse test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        User user=new User();
//        List<User> userList=userService.findByCondition(user,new Sort(Sort.Direction.ASC,"lastModifiedTime"));
//        List<User> u=userService.transToDTOList(userList,User.class);
//        userService.delete((long) 8);
//        String fileName = "default.jpg";//被下载文件的名称
//        String downloadFilePath = rootPath+fileName;//被下载的文件在服务器中的路径,
//
//
//        File file = new File(downloadFilePath);
//        if (file.exists()) {
//            httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开            
//            httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//            try {
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                OutputStream outputStream = httpServletResponse.getOutputStream();
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    outputStream.write(buffer, 0, i);
//                    i = bis.read(buffer);
//                }
//                return null;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//            return null;
//        }

        return new ObjectRestResponse(userService.findAllUser(new User(), 1, 20));
    }
}
