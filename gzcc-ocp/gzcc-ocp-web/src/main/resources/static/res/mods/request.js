layui.define(['layer', 'jquery'],function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var baseUrl = "http://localhost:8060"
    var $ = layui.jquery
        ,layer = layui.layer
    var object = {
        ajax: function (obj) {
            // alert('Hello ' + (str || 'request'));
            obj.url = baseUrl + obj.url
            var new_obj = {
                headers: {},
                async: true,
                type: obj.type || 'get',
                url: '',
                data: obj.data,
                crossDomain: true,
                cache: false
            }
            for (var key in obj) {
                new_obj[key] = obj[key]
            }
            return $.ajax(new_obj).then(function(e){
                console.log(e)
                return e
            })
        }
    };
    //输出test接口
    exports('request', object);
});