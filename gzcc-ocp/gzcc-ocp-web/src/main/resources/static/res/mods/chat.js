// layui.config({
//     version: "3.0.0"
//     , base: '../../res/mods/'
// }).extend({
//     socket: 'socket'
// })
layui.use(['layim', 'jquery', 'layer','request'], function () {
    var layim = layui.layim;
    var $ = layui.$;
    var layer = layui.layer;
    var request = layui.request;
    var socket;
    if (typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于
        socket = new WebSocket("ws://localhost:8060/webSocket/" + layui.sessionData('userData').user.id);

        //打开事件
        socket.onopen = function () {
            console.log("Socket 已打开");
            // socket.send("这是来自客户端的消息" + location.href + new Date());

        };

        var a={
            "type":"chatMessage",
            "data":{
                "mine":{
                    "username":"hdl",
                    "avatar":"/uploads/0019030515430300001.png",
                    "id":1,
                    "mine":true,
                    "content":"778"
                },
                "to":{
                    "id":1,
                    "createTime":"2019-03-05 23:46:34",
                    "lastModifiedTime":"2019-03-05 23:46:37",
                    "groupname":"一群","avatar":"/uploads/0019030515430300001.png",
                    "userList":[
                        {
                            "id":1,
                            "createTime":"2019-03-05 15:10:45",
                            "lastModifiedTime":"2019-03-05 15:43:04",
                            "account":"hdl",
                            "username":"hdl",
                            "email":null,
                            "gender":null,
                            "avatar":"/uploads/0019030515430300001.png",
                            "sign":null,
                            "ban":null,
                            "roleList":[{"id":1,"createTime":null,"lastModifiedTime":null,"name":"ROLE_ADMIN","description":null},{"id":2,"createTime":null,"lastModifiedTime":null,"name":"ROLE_USER","description":null}],
                            "collectNote":null},
                        {
                            "id":2,
                            "createTime":"2019-03-05 15:43:52",
                            "lastModifiedTime":"2019-03-05 15:43:52",
                            "account":"czx",
                            "username":"woerzhi",
                            "email":null,
                            "gender":null,
                            "avatar":"/uploads/0019030515430300001.png",
                            "sign":null,"ban":null,"roleList":[],
                            "collectNote":null
                        }],
                    "name":"一群",
                    "type":"group"
                }
             }
        }
        //获得消息事件
        socket.onmessage = function (res) {
            // console.log("发现消息进入");
            console.log(res);
            // console.log(res.data);
            // res = JSON.parse(res);
            //发现消息进入    开始处理前端触发逻辑
            var data = JSON.parse(res.data);
            // if(res.type === 'chatMessage'){
                layim.getMessage(data); //res.data即你发送消息传递的数据（阅读：监听发送的消息）
            // }
        };
        //关闭事件
        socket.onclose = function () {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
            // alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }

    //演示自动回复
    var autoReplay = [
        '您好，我现在有事不在，一会再和您联系。',
        '你没发错吧？face[微笑] ',
        '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
        '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
        'face[威武] face[威武] face[威武] face[威武] ',
        '<（@￣︶￣@）>',
        '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
        'face[黑线]  你慢慢说，别急……',
        '(*^__^*) face[嘻嘻] ，是贤心吗？'
    ];

    //基础配置
    layim.config({
        //初始化接口
        title: layui.data('userData').user.username,
        init: {
            url: '/chat/json',
            data: {
                userId: layui.data('userData').user.id
            }
        }
        //查看群员接口
        ,
        members: {
            url: '/chat/getMembers',
            data: {}
        }
        ,
        uploadImage: {
            url: '/note/upload' //（返回的数据格式见下文）
            ,
            type: '' //默认post
        },
        uploadFile: {
            url: '' //（返回的数据格式见下文）
            ,
            type: '' //默认post
        }

        ,
        isAudio: true //开启聊天工具栏音频
        ,
        isVideo: true //开启聊天工具栏视频

        //扩展工具栏
        ,
        tool: [{
            alias: 'code',
            title: '代码',
            icon: '&#xe64e;'
        }]

        //,brief: true //是否简约模式（若开启则不显示主面板）

        //,title: 'WebIM' //自定义主面板最小化时的标题
        //,right: '100px' //主面板相对浏览器右侧距离
        //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
        ,
        initSkin: '3.jpg' //1-5 设置初始背景
        //,skin: ['aaa.jpg'] //新增皮肤
        //,isfriend: false //是否开启好友
        //,isgroup: false //是否开启群组
        , min: true //是否始终最小化主面板，默认false
        //,notice: true //是否开启桌面消息提醒，默认false
        //,voice: false //声音提醒，默认开启，声音文件为：default.mp3

        ,
        msgbox: '/layim/demo/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        ,
        find: '/layim/demo/find.html' //发现页面地址，若不开启，剔除该项即可
        ,
        chatLog: '/layim/demo/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

    });
    //监听在线状态的切换事件
    layim.on('online', function (status) {
        layer.msg(status);
    });

    //监听签名修改
    layim.on('sign', function (value) {
        layer.msg(value);
    });
    //监听自定义工具栏点击，以添加代码为例
    layim.on('tool(code)', function (insert) {
        layer.prompt({
            title: '插入代码 - 工具栏扩展示例',
            formType: 2,
            shade: 0
        }, function (text, index) {
            layer.close(index);
            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
        });
    });

    //监听layim建立就绪
    layim.on('ready', function (res) {
        //console.log(res.mine);
        layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
    });
    //监听发送消息
    layim.on('sendMessage', function (data) {
        var To = data.to;
        console.log(data);
        socket.send(JSON.stringify({
            type: 'chatMessage' //随便定义，用于在服务端区分消息类型
            ,data: data
        }));
        // if (To.type === 'friend') {
        //     layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
        //     request.ajax({
        //         type: 'POST',
        //         url: '/chat/send',
        //         data: {userId: 1},
        //     }).then(function (res) {
        //
        //     })
        //
        // }else{
        //
        // }

        //演示自动回复
        // setTimeout(function () {
        //     var obj = {};
        //     if (To.type === 'group') {
        //         obj = {
        //             username: '模拟群员' + (Math.random() * 100 | 0),
        //             avatar: layui.cache.dir + 'images/face/' + (Math.random() * 72 | 0) + '.gif',
        //             id: To.id,
        //             type: To.type,
        //             content: autoReplay[Math.random() * 9 | 0]
        //         }
        //     } else {
        //         obj = {
        //             username: To.name,
        //             avatar: To.avatar,
        //             id: To.id,
        //             type: To.type,
        //             content: autoReplay[Math.random() * 9 | 0]
        //         }
        //         layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
        //     }
        //     layim.getMessage(obj);
        // }, 1000);
    });
    //监听查看群员
    layim.on('members', function (data) {
        console.log(data);
    });

    //监听聊天窗口的切换
    layim.on('chatChange', function (res) {
        var type = res.data.type;
        console.log(res.data.id)
        if (type === 'friend') {
            //模拟标注好友状态
            // layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
        } else if (type === 'group') {
            //模拟系统消息
            layim.getMessage({
                system: true,
                id: res.data.id,
                type: "group",
                content: '模拟群员' + (Math.random() * 100 | 0) + '加入群聊'
            });
        }
    });

    layim.on('ready', function (options) {

        // console.log(options);
        // console.log($('body').find('.layim-list-friend'),"zhao")
        var index;
        $('body').find('.layim-list-friend .layui-layim-list li').contextmenu(function (e) {
            if (index != null) {
                layer.close(index);
            }
            var othis = $(this);
            var id = othis[0].id;
            console.log(e)
            console.log(e.currentTarget.classList[0])
            var html = '<ul id="contextmenu_' + othis[0].id + '" data-id="' + othis[0].id + '" data-index="' + othis.data('index') + '">';
            // html += '<li layim-event="menu_chat">' + '发送即时消息</li>';
            // html += '<li layim-event="menu_profile">' + '查看资料</li>';
            // html += '<li layim-event="menu_history">' + '消息记录</li>';
            // html += '<li layim-event="menu_nomsg">' + '屏蔽消息</li>';
            html += '<li layim-event="menu_delete" style="padding:5px 0px;width:110px;text-align:center;" >' + '删除好友</li>';
            html += '<li layim-event="menu_moveto" style="padding:5px 0px;width:110px;text-align:center;">' + '移动分组</li></ul>';
            // layer.tips('默认就是向右的', '.layim-friend4');

            index = layer.open({
                shade: 0,
                type: 1,
                title: false,
                closeBtn: 0,
                content: html, //这里content是一个普通的String
                offset: [(document.body.offsetHeight-e.clientY)>58? e.clientY:e.clientY-58 , (document.body.offsetWidth-e.clientX)>110? e.clientX:e.clientX-110]
            });
        });
        $('body').click(function (e) {
            console.log(e)
            if (index != null) {
                layer.close(index);
            }
        })
    });


    //面板外的操作
    var $ = layui.jquery,
        active = {
            chat: function () {
                //自定义会话
                layim.chat({
                    name: '小闲',
                    type: 'friend',
                    avatar: '//tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg',
                    id: 1008612
                });
                layer.msg('也就是说，此人可以不在好友面板里');
            },
            message: function () {
                //制造好友消息
                layim.getMessage({
                    username: "贤心",
                    avatar: "//tp1.sinaimg.cn/1571889140/180/40030060651/1",
                    id: "100001",
                    type: "friend",
                    content: "嗨，你好！欢迎体验LayIM。演示标记：" + new Date().getTime(),
                    timestamp: new Date().getTime()
                });
            },
            messageAudio: function () {
                //接受音频消息
                layim.getMessage({
                    username: "林心如",
                    avatar: "//tp3.sinaimg.cn/1223762662/180/5741707953/0",
                    id: "76543",
                    type: "friend",
                    content: "audio[http://gddx.sc.chinaz.com/Files/DownLoad/sound1/201510/6473.mp3]",
                    timestamp: new Date().getTime()
                });
            },
            messageVideo: function () {
                //接受视频消息
                layim.getMessage({
                    username: "林心如",
                    avatar: "//tp3.sinaimg.cn/1223762662/180/5741707953/0",
                    id: "76543",
                    type: "friend",
                    content: "video[http://www.w3school.com.cn//i/movie.ogg]",
                    timestamp: new Date().getTime()
                });
            },
            messageTemp: function () {
                //接受临时会话消息
                layim.getMessage({
                    username: "小酱",
                    avatar: "//tva1.sinaimg.cn/crop.7.0.736.736.50/bd986d61jw8f5x8bqtp00j20ku0kgabx.jpg",
                    id: "198909151014",
                    type: "friend",
                    content: "临时：" + new Date().getTime()
                });
            },
            add: function () {
                //实际使用时数据由动态获得
                layim.add({
                    type: 'friend',
                    username: '麻花疼',
                    avatar: '//tva1.sinaimg.cn/crop.0.0.720.720.180/005JKVuPjw8ers4osyzhaj30k00k075e.jpg',
                    submit: function (group, remark, index) {
                        layer.msg('好友申请已发送，请等待对方确认', {
                            icon: 1,
                            shade: 0.5
                        }, function () {
                            layer.close(index);
                        });

                        //通知对方
                        /*
                        $.post('/im-applyFriend/', {
                          uid: info.uid
                          ,from_group: group
                          ,remark: remark
                        }, function(res){
                          if(res.status != 0){
                            return layer.msg(res.msg);
                          }
                          layer.msg('好友申请已发送，请等待对方确认', {
                            icon: 1
                            ,shade: 0.5
                          }, function(){
                            layer.close(index);
                          });
                        });
                        */
                    }
                });
            },
            addqun: function () {
                layim.add({
                    type: 'group',
                    username: 'LayIM会员群',
                    avatar: '//tva2.sinaimg.cn/crop.0.0.180.180.50/6ddfa27bjw1e8qgp5bmzyj2050050aa8.jpg',
                    submit: function (group, remark, index) {
                        layer.msg('申请已发送，请等待管理员确认', {
                            icon: 1,
                            shade: 0.5
                        }, function () {
                            layer.close(index);
                        });

                        //通知对方
                        /*
                        $.post('/im-applyGroup/', {
                          uid: info.uid
                          ,from_group: group
                          ,remark: remark
                        }, function(res){

                        });
                        */
                    }
                });
            },
            addFriend: function () {
                var user = {
                    type: 'friend',
                    id: 1234560,
                    username: '李彦宏' //好友昵称，若申请加群，参数为：groupname
                    ,
                    avatar: '//tva4.sinaimg.cn/crop.0.0.996.996.180/8b2b4e23jw8f14vkwwrmjj20ro0rpjsq.jpg' //头像
                    ,
                    sign: '全球最大的中文搜索引擎'
                }
                layim.setFriendGroup({
                    type: user.type,
                    username: user.username,
                    avatar: user.avatar,
                    group: layim.cache().friend //获取好友列表数据
                    ,
                    submit: function (group, index) {
                        //一般在此执行Ajax和WS，以通知对方已经同意申请
                        //……

                        //同意后，将好友追加到主面板
                        layim.addList({
                            type: user.type,
                            username: user.username,
                            avatar: user.avatar,
                            groupid: group //所在的分组id
                            ,
                            id: user.id //好友ID
                            ,
                            sign: user.sign //好友签名
                        });

                        layer.close(index);
                    }
                });
            },
            addGroup: function () {
                layer.msg('已成功把[Angular开发]添加到群组里', {
                    icon: 1
                });
                //增加一个群组
                layim.addList({
                    type: 'group',
                    avatar: "//tva3.sinaimg.cn/crop.64.106.361.361.50/7181dbb3jw8evfbtem8edj20ci0dpq3a.jpg",
                    groupname: 'Angular开发',
                    id: "12333333",
                    members: 0
                });
            },
            removeFriend: function () {
                layer.msg('已成功删除[凤姐]', {
                    icon: 1
                });
                //删除一个好友
                layim.removeList({
                    id: 121286,
                    type: 'friend'
                });
            },
            removeGroup: function () {
                layer.msg('已成功删除[前端群]', {
                    icon: 1
                });
                //删除一个群组
                layim.removeList({
                    id: 101,
                    type: 'group'
                });
            }
            //置灰离线好友
            ,
            setGray: function () {
                layim.setFriendStatus(168168, 'offline');

                layer.msg('已成功将好友[马小云]置灰', {
                    icon: 1
                });
            }
            //取消好友置灰
            ,
            unGray: function () {
                layim.setFriendStatus(168168, 'online');

                layer.msg('成功取消好友[马小云]置灰状态', {
                    icon: 1
                });
            }
            //移动端版本
            ,
            mobile: function () {
                var device = layui.device();
                var mobileHome = '/layim/demo/mobile.html';
                if (device.android || device.ios) {
                    return location.href = mobileHome;
                }
                var index = layer.open({
                    type: 2,
                    title: '移动版演示 （或手机扫右侧二维码预览）',
                    content: mobileHome,
                    area: ['375px', '667px'],
                    shadeClose: true,
                    shade: 0.8,
                    end: function () {
                        layer.close(index + 2);
                    }
                });
                layer.photos({
                    photos: {
                        "data": [{
                            "src": "http://cdn.layui.com/upload/2016_12/168_1481056358469_50288.png",
                        }]
                    },
                    anim: 0,
                    shade: false,
                    success: function (layero) {
                        layero.css('margin-left', '350px');
                    }
                });
            }
        };
    $('.site-demo-layim').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});
