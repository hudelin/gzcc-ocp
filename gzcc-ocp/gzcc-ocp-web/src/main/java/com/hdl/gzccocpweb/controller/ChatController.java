package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.constant.BaseConstant;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpweb.exception.BaseException;
import com.hdl.gzccocpweb.response.ObjectRestResponse;
import com.hdl.gzccocpweb.webSocket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/test")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/chat/test.btl");
        return mv;
    }

    @RequestMapping("/json")
    @ResponseBody
    public String getJson(){
        return "{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": {\n" +
                "    \"mine\": {\n" +
                "      \"username\": \"纸飞机\"\n" +
                "      ,\"id\": \"100000\"\n" +
                "      ,\"status\": \"online\"\n" +
                "      ,\"sign\": \"在深邃的编码世界，做一枚轻盈的纸飞机\"\n" +
                "      ,\"avatar\": \"/uploads/1.jpg\"\n" +
                "    }\n" +
                "    ,\"friend\": [{\n" +
                "      \"groupname\": \"知名人物\"\n" +
                "      ,\"id\": 0\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"贤心\"\n" +
                "        ,\"id\": \"100001\"\n" +
                "        ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg\"\n" +
                "        ,\"sign\": \"这些都是测试数据，实际使用请严格按照该格式返回\"\n" +
                "        ,\"status\": \"online\"\n" +
                "      },{\n" +
                "        \"username\": \"刘小涛\"\n" +
                "        ,\"id\": \"100001222\"\n" +
                "        ,\"sign\": \"如约而至，不负姊妹欢乐颂\"\n" +
                "        ,\"avatar\": \"//tva4.sinaimg.cn/crop.0.1.1125.1125.180/475bb144jw8f9nwebnuhkj20v90vbwh9.jpg\"\n" +
                "      },{\n" +
                "        \"username\": \"谢小楠\"\n" +
                "        ,\"id\": \"10034001\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.1.0.747.747.180/633f068fjw8f9h040n951j20ku0kr74t.jpg\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"马小云\"\n" +
                "        ,\"id\": \"168168\"\n" +
                "        ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.180.180.180/7fde8b93jw1e8qgp5bmzyj2050050aa8.jpg\"\n" +
                "        ,\"sign\": \"让天下没有难写的代码\"\n" +
                "      },{\n" +
                "        \"username\": \"徐小峥\"\n" +
                "        ,\"id\": \"666666\"\n" +
                "        ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.512.512.180/6a4acad5jw8eqi6yaholjj20e80e8t9f.jpg\"\n" +
                "        ,\"sign\": \"代码在囧途，也要写到底\"\n" +
                "      }]\n" +
                "    },{\n" +
                "      \"groupname\": \"网红声优\"\n" +
                "      ,\"id\": 1\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"罗小凤\"\n" +
                "        ,\"id\": \"121286\"\n" +
                "        ,\"avatar\": \"//tva4.sinaimg.cn/crop.0.0.640.640.180/4a02849cjw8fc8vn18vktj20hs0hs75v.jpg\"\n" +
                "        ,\"sign\": \"在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱\"\n" +
                "      },{\n" +
                "        \"username\": \"Z_子晴\"\n" +
                "        ,\"id\": \"108101\"\n" +
                "        ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.23.1242.1242.180/8693225ajw8fbimjimpjwj20yi0zs77l.jpg\"\n" +
                "        ,\"sign\": \"微电商达人\"\n" +
                "      },{\n" +
                "        \"username\": \"大鱼_MsYuyu\"\n" +
                "        ,\"id\": \"12123454\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.0.512.512.180/005LMAegjw8f2bp9qg4mrj30e80e8dg5.jpg\"\n" +
                "        ,\"sign\": \"我瘋了！這也太準了吧  超級笑點低\"\n" +
                "      },{\n" +
                "        \"username\": \"醋醋cucu\"\n" +
                "        ,\"id\": \"102101\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.0.640.640.180/648fbe5ejw8ethmg0u9egj20hs0ht0tn.jpg\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"柏雪近在它香\"\n" +
                "        ,\"id\": \"3435343\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.8.751.751.180/961a9be5jw8fczq7q98i7j20kv0lcwfn.jpg\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      }]\n" +
                "    },{\n" +
                "      \"groupname\": \"女神艺人\"\n" +
                "      ,\"id\": 2\n" +
                "      ,\"list\": [{\n" +
                "        \"username\": \"王小贤\"\n" +
                "        ,\"id\": \"76543\"\n" +
                "        ,\"avatar\": \"//wx2.sinaimg.cn/mw690/5db11ff4gy1flxmew7edlj203d03wt8n.jpg\"\n" +
                "        ,\"sign\": \"我爱贤心\"\n" +
                "      },{\n" +
                "        \"username\": \"佟小娅\"\n" +
                "        ,\"id\": \"4803920\"\n" +
                "        ,\"avatar\": \"//tva3.sinaimg.cn/crop.0.0.750.750.180/5033b6dbjw8etqysyifpkj20ku0kuwfw.jpg\"\n" +
                "        ,\"sign\": \"我也爱贤心吖吖啊\"\n" +
                "      }]\n" +
                "    }]\n" +
                "    ,\"group\": [{\n" +
                "      \"groupname\": \"前端群\"\n" +
                "      ,\"id\": \"101\"\n" +
                "      ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.200.200.50/006q8Q6bjw8f20zsdem2mj305k05kdfw.jpg\"\n" +
                "    },{\n" +
                "      \"groupname\": \"Fly社区官方群\"\n" +
                "      ,\"id\": \"102\"\n" +
                "      ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg\"\n" +
                "    }]\n" +
                "  }\n" +
                "}";
    }

    @RequestMapping("/getMembers")
    @ResponseBody
    public String getMembers(String id){
        return "{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": {\n" +
                "    \"list\": [{\n" +
                "      \"username\": \"贤心\"\n" +
                "      ,\"id\": \"100001\"\n" +
                "      ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg\"\n" +
                "      ,\"sign\": \"这些都是测试数据，实际使用请严格按照该格式返回\"\n" +
                "    },{\n" +
                "      \"username\": \"Z_子晴\"\n" +
                "      ,\"id\": \"108101\"\n" +
                "      ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.23.1242.1242.180/8693225ajw8fbimjimpjwj20yi0zs77l.jpg\"\n" +
                "      ,\"sign\": \"微电商达人\"\n" +
                "    },{\n" +
                "      \"username\": \"Lemon_CC\"\n" +
                "      ,\"id\": \"102101\"\n" +
                "      ,\"avatar\": \"//tva4.sinaimg.cn/crop.0.0.180.180.180/6d424ea5jw1e8qgp5bmzyj2050050aa8.jpg\"\n" +
                "      ,\"sign\": \"\"\n" +
                "    },{\n" +
                "      \"username\": \"马小云\"\n" +
                "      ,\"id\": \"168168\"\n" +
                "      ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.180.180.180/7fde8b93jw1e8qgp5bmzyj2050050aa8.jpg\"\n" +
                "      ,\"sign\": \"让天下没有难写的代码\"\n" +
                "    },{\n" +
                "      \"username\": \"徐小峥\"\n" +
                "      ,\"id\": \"666666\"\n" +
                "      ,\"avatar\": \"//tva1.sinaimg.cn/crop.0.0.512.512.180/6a4acad5jw8eqi6yaholjj20e80e8t9f.jpg\"\n" +
                "      ,\"sign\": \"代码在囧途，也要写到底\"\n" +
                "    },{\n" +
                "      \"username\": \"罗小凤\"\n" +
                "      ,\"id\": \"121286\"\n" +
                "      ,\"avatar\": \"//tva4.sinaimg.cn/crop.0.0.640.640.180/4a02849cjw8fc8vn18vktj20hs0hs75v.jpg\"\n" +
                "      ,\"sign\": \"在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱\"\n" +
                "    },{\n" +
                "      \"username\": \"刘小涛\"\n" +
                "      ,\"id\": \"100001222\"\n" +
                "      ,\"avatar\": \"//tva4.sinaimg.cn/crop.0.1.1125.1125.180/475bb144jw8f9nwebnuhkj20v90vbwh9.jpg\"\n" +
                "      ,\"sign\": \"如约而至，不负姊妹欢乐颂\"\n" +
                "    },{\n" +
                "        \"username\": \"大鱼_MsYuyu\"\n" +
                "        ,\"id\": \"12123454\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.0.512.512.180/005LMAegjw8f2bp9qg4mrj30e80e8dg5.jpg\"\n" +
                "        ,\"sign\": \"我瘋了！這也太準了吧  超級笑點低\"\n" +
                "      },{\n" +
                "        \"username\": \"谢小楠\"\n" +
                "        ,\"id\": \"10034001\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.1.0.747.747.180/633f068fjw8f9h040n951j20ku0kr74t.jpg\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"柏雪近在它香\"\n" +
                "        ,\"id\": \"3435343\"\n" +
                "        ,\"avatar\": \"//tva2.sinaimg.cn/crop.0.8.751.751.180/961a9be5jw8fczq7q98i7j20kv0lcwfn.jpg\"\n" +
                "        ,\"sign\": \"\"\n" +
                "      },{\n" +
                "        \"username\": \"王小贤\"\n" +
                "        ,\"id\": \"76543\"\n" +
                "        ,\"avatar\": \"//cdn.layui.com/upload/2018_7/168_1532072603409_58354.jpg\"\n" +
                "        ,\"sign\": \"我爱贤心\"\n" +
                "      },{\n" +
                "        \"username\": \"佟小娅\"\n" +
                "        ,\"id\": \"4803920\"\n" +
                "        ,\"avatar\": \"//tva3.sinaimg.cn/crop.0.0.750.750.180/5033b6dbjw8etqysyifpkj20ku0kuwfw.jpg\"\n" +
                "        ,\"sign\": \"我也爱贤心吖吖啊\"\n" +
                "      }]\n" +
                "  }\n" +
                "}";
    }

    @RequestMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public void pushToWeb(@PathVariable String cid,String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
//            BeanUtils.copyProperties(user, note);
        } catch (IOException e) {
            e.printStackTrace();
//            return ApiReturnUtil.error(cid+"#"+e.getMsg());
        }
//        return ApiReturnUtil.success(cid);
    }

    @ResponseBody
    @RequestMapping("/send")
    public ObjectRestResponse send( String message) throws Exception {
        User user=new User();
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<User> userList=new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        objectRestResponse.data(userList);
        throw new BaseException(BaseConstant.SYSTEM_ERROR, "系统错误！");
//        return objectRestResponse;

    }

}
