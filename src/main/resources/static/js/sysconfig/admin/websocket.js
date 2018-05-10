$(function(){

    /**
     * 用户对当前时间进行格式化
     * @param fmt
     * @returns {*}
     * @constructor
     */
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    /**
     * 字段判空
     */
    var page_writer = null;
    var page_reader = null;
    var page_wimgsrc = null;
    var page_rimgsrc = null;
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param == "" || param == "null");
    };

    /**
     * 获取url的请求参数
     */
    var str_geturlparam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };

    /**
     * 异步获取所有的聊天数据
     */
    var async_loadmysocketlist = function(){
        var username = str_geturlparam("username");
        if(username == null){
            return ;
        }
        page_reader = username;
        $(".p-info").text("当前正与"+username+"进行聊天");
        $.ajax({
            async: false,    /* 保证page_*系列数据有值 */
            url: "/userwebsocket_con/loadmysocketlist.json",
            data: {
                reader: username
            },
            dataType: "json",
            success: function(data){
                var list = data.list;
                var mine = data.mine;
                var now = data.now;
                page_writer = mine;
                $(".container .m-main").append("<p class=\"p-enterinfo\">------------------------------    <span>"+mine+"</span>"+now+"&nbsp;加入了会话    ------------------------------</p>");
                if(list == null){
                    return ;
                }
                $.each(list, function(index, item){
                    var writer = item.writer;
                    var wimgsrc = item.wimgsrc;
                    var reader = item.reader;
                    var rimgsrc = item.rimgsrc;
                    var ptime = item.ptime;
                    var info = item.info;
                    //我为发送者
                    if(writer === mine){
                        page_wimgsrc = wimgsrc;
                        $(".container .m-main").append("<div class=\"main-show show-right\">\n" +
                            "            <ul class=\"clearfix\">\n" +
                            "                <li class=\"pull-right\">\n" +
                            "                    <a href=\"javascript:void(0)\">\n" +
                            "                        <img src=\""+wimgsrc+"\" class=\"img-userface\" />\n" +
                            "                    </a>\n" +
                            "                </li>\n" +
                            "                <li class=\"pull-right li-info\">\n" +
                            "                    <pre class=\"main-info\">"+info+"</pre>\n" +
                            "                </li>\n" +
                            "                <li class=\"pull-right li-ptime\">\n" +
                            "                    <p class=\"main-time\">"+ptime+"</p>\n" +
                            "                </li>\n" +
                            "            </ul>\n" +
                            "        </div>");
                    }else{
                        //我是接收者
                        page_rimgsrc = wimgsrc;
                        $(".container .m-main").append("<div class=\"main-show show-left\">\n" +
                            "            <ul class=\"clearfix\">\n" +
                            "                <li class=\"pull-left\">\n" +
                            "                    <a href=\"javascript:void(0)\">\n" +
                            "                        <img src=\""+wimgsrc+"\" class=\"img-userface\" />\n" +
                            "                    </a>\n" +
                            "                </li>\n" +
                            "                <li class=\"pull-left li-info\">\n" +
                            "                    <pre class=\"main-info\">"+info+"</pre>\n" +
                            "                </li>\n" +
                            "                <li class=\"pull-left li-ptime\">\n" +
                            "                    <p class=\"main-time\">"+ptime+"</p>\n" +
                            "                </li>\n" +
                            "            </ul>\n" +
                            "        </div>");
                    }
                });
            }
        });
    };
    async_loadmysocketlist();

    /**
     * 创建一个websocket实例
     */
    var websocket = null;
    var websocket_connect = function(host){
      if('WebSocket' in window){
          websocket = new WebSocket(host);
      }else if('MozWebSocket' in window){
          websocket = new MozWebSocket(host);
      }else{
          alert("浏览器不支持websocket，无法进行通信");
          websocket = null;
      }
    };
    var websocket_inital = function(host){

        if(window.location.protocol == "http:"){
            websocket_connect("ws://"+host+"/openwebsocket");
        }else{
            websocket_connect("wss://"+host+"/openwebsocket");
        }
    };
    websocket_inital(window.location.host);

    /**
     * 监听websocket的连接
     */
    var websocket_open = function(){
        //发送模拟数据
        websocket.send("{别学我}: "+page_writer+"{别学我}: "+page_reader+"{别学我}: open");
    };
    websocket.onopen = websocket_open;

    /**
     * 点击发送消息
     */
    var click_sendmessage = function(){
        var msg = $(".container .m-write p").text();
        var ptime = new Date().Format("yyyy-MM-dd hh:mm:ss");
        $.ajax({
            async: false,
            type: "post",
            url: "/userwebsocket_con/savewebsocket.json",
            data: JSON.stringify({
                "username": page_writer,
                "tutorName": page_reader,
                "courseName": msg,
                "startTime": ptime
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status != "valid"){
                    alert("消息发送失败，请重新发送");
                    return ;
                }
            }
        });
        $(".container .m-write p").text("");
        websocket.send("{别学我}: "+page_writer+"{别学我}: "+page_reader+"{别学我}: "+msg+"{别学我}: "+ptime);
    };
    $(".container .m-write button").click(click_sendmessage);

    /**
     * 接收websocket连接消息
     */
    var websocket_message = function(event){

        var data = event.data;
        var obj = data.split("{别学我}: ");
        var writer = obj[1];
        var reader = obj[2];
        var msg = obj[3];
        var ptime = obj[4];
        if(msg === "open" && ptime == null){
            var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
            if(writer === page_reader && reader === page_writer){
                $(".container .m-main").append("<p class=\"p-enterinfo\">------------------------------    <span>"+ writer +"</span>"+now+"&nbsp;加入了会话    ------------------------------</p>");
            }
            return ;
        }else if(msg === "close" && ptime == null){
            var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
            if(writer === page_reader && reader === page_writer) {
                $(".container .m-main").append("<p class=\"p-enterinfo\">------------------------------    <span>" + writer + "</span>" + now + "&nbsp;离开了会话    ------------------------------</p>");
                $(".m-main.new").removeClass("new");
            }
            return ;
        }
        //本人发的别人接的
        if(writer == page_writer && reader == page_reader){
            $(".container .m-main").append("<div class=\"main-show show-right\">\n" +
                "            <ul class=\"clearfix\">\n" +
                "                <li class=\"pull-right\">\n" +
                "                    <a href=\"javascript:void(0)\" data-writer='"+writer+"' data-reader='"+reader+"'>\n" +
                "                        <img src=\""+page_wimgsrc+"\" class=\"img-userface\" />\n" +
                "                    </a>\n" +
                "                </li>\n" +
                "                <li class=\"pull-right li-info\">\n" +
                "                    <pre class=\"main-info\">"+msg+"</pre>\n" +
                "                </li>\n" +
                "                <li class=\"pull-right li-ptime\">\n" +
                "                    <p class=\"main-time\">"+ptime+"</p>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>");
        }else if(writer == page_reader && reader == page_writer){
            //别人发的我接的
            $(".container .m-main").append("<div class=\"main-show show-left\">\n" +
                "            <ul class=\"clearfix\">\n" +
                "                <li class=\"pull-left\">\n" +
                "                    <a href=\"javascript:void(0)\" data-writer='"+writer+"' data-reader='"+reader+"'>\n" +
                "                        <img src=\""+page_rimgsrc+"\" class=\"img-userface\" />\n" +
                "                    </a>\n" +
                "                </li>\n" +
                "                <li class=\"pull-left li-info\">\n" +
                "                    <pre class=\"main-info\">"+msg+"</pre>\n" +
                "                </li>\n" +
                "                <li class=\"pull-left li-ptime\">\n" +
                "                    <p class=\"main-time\">"+ptime+"</p>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>");
        }
    };
    websocket.onmessage = websocket_message;

    /**
     * 页面离开时关闭socket连接
     */
    $(window).bind('beforeunload', function (event) {

        //发送模拟数据
        websocket.send("{别学我}: "+page_writer+"{别学我}: "+page_reader+"{别学我}: close");
        websocket.close();
    });
});