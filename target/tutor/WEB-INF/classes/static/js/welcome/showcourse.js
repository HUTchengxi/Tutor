$(function() {
    //获取系统时间-----------------
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "h+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
        return format;
    }

    /**
     * 判断当前用户是否登录
     */
    var login_check = function(){

        $("nav ul.navbar-right").text("");
        $.ajax({
            async: true,
            type: "post",
            url: "/login_con/login_statuscheck",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "nologin"){
                    $("nav ul.navbar-right").append("<li><a style='color: red;'>您好，请先登录</a></li>\n" +
                        "                    <li><a href='/forward_con/gologin'><span class='glyphicon glyphicon-log-in'></span> 登录</a></li>"+
                        "                    <li><a href='/forward_con/goregister'><span class='glyphicon glyphicon-user'></span> 注册</a></li>\n");
                }
                else{
                    var hour = new Date().getHours();
                    var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                    var hello = null;
                    if(hour>=6 && hour<=12){
                        hello = "早上好";
                    }
                    else if(hour>=13 && hour<18){
                        hello = "下午好";
                    }
                    else{
                        hello = "晚上好";
                    }
                    var nickname = data.nick;

                    //获取我的通知数量
                    var count = 0;
                    $.ajax({
                        async: false,
                        type: "post",
                        url: "/usermessage_con/getmymessagecount",
                        dataType: "json",
                        success: function(data){
                            var status = data.status;
                            if(status === "invalid"){
                                window.location = "/forward_con/welcome";
                            }
                            else if(status === "mysqlerr"){
                                window.alert("后台服务器异常导致无法获取通知数据，请刷新页面重试");
                            }
                            else{
                                count = data.count;
                            }
                        },
                        error: function(xhr, status){
                            window.alert("后台环境异常导致");
                            window.console.log(xhr);
                        }
                    });

                    $("nav ul.navbar-right").append("<li class='nav-time'><a>现在是：<i style='color: #00b6ff;'>"+now+"</i></a></li>" +
                        "                   <li><a style=\"color: black;\"><p>"+hello+"：<span class=\"span-cls-nick\" style=\"color: red;\">"+nickname+"</span></p></a></li>\n" +
                        "                    <li class=\"dropdown\">\n" +
                        "                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">个人中心<i class=\"caret\"></i></a>\n" +
                        "                        <ul class=\"dropdown-menu\">\n" +
                        "                            <li><a href='/forward_con/personal' target='_blank'>基本信息</a></li>\n" +
                        "                            <li><a href='/forward_con/gomyorder'>我的订单</a></li>\n" +
                        "                            <li><a href='/forward_con/gomycourse' target='_blank'>我的课程</a></li>\n" +
                        "                            <li class='sysconfig'><a href='/forward_con/gosysconfig' target='_blank'><span class='mcount'>后台管理</span></a></li>\n" +
                        "                            <li><a href='/forward_con/gomessage' target='_blank'>通知<span class='mcount'>("+count+")</span></a></li>\n" +
                        "                            <li><a href='/forward_con/gosetting'>设置</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n" +
                        "                        </ul>\n" +
                        "                    </li>");

                    var ident = data.ident;
                    if(ident == 0 || ident == -2){
                        $("nav ul.navbar-right li.sysconfig").remove();
                    }
                }

                //让时间一直轮播
                var timeinter = window.setInterval(function(){
                    var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                    $("nav ul.navbar-right li.nav-time a i").text(now);
                },1000);
            },
            error: function(xhr, status){
                alert("后台环境异常导致无法正确获得登录信息，请刷新页面重试");
            }
        })
    };
    login_check();

    /**
     * 点击注销按钮的功能实现
     */
    var logoff_btn = function () {

        if (window.confirm("确认退出登录吗？")) {
            $.ajax({
                asynv: false,
                type: "post",
                url: "/login_con/login_logoff",
                dataType: "json",
                success: function (data) {
                    alert("注销成功");
                    login_check();
                },
                error: function (xhr, status) {
                    alert("后台环境异常导致无法正确退出登录，请刷新页面重试");
                }
            });
        }
    };
    $(document).on("click", ".navbar-right .nav-logoff a", logoff_btn);




    //----------收藏与取消收藏----------
    /**
     * 收藏
     */
    var course_collect = function(){

        var status = $(this).closest("p").data("status");
        if(status === "nologin"){
            window.alert("请您先登录");
            window.location = "/forward_con/gologin";
            return;
        }
        //取消收藏
        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);
        if(status === "collect"){
            $.ajax({
                async: true,
                type: "post",
                url: "/coursecollect_con/modusercollect",
                data: {cid: cid, mod: "uncollect"},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status === "mysqlerr"){
                        window.alert("后台数据库异常导致无法取消收藏，请稍后再试");
                    }
                    else{
                        $(".coursecontainer .container-top .collect").data("status", "uncollect").removeClass("col").find("span").text("收藏");
                    }
                },
                error: function(xhr, status) {
                    window.alert("后台环境异常导致无法取消收藏，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
        else{
            $('#myModal').modal();
        }
    };
    $(".coursecontainer .container-top .collect a").click(course_collect);
    /**
     * 收藏
     */
    var course_reacollect = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        var descript = $("#myModal input").val();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursecollect_con/modusercollect",
            data: {cid: cid, mod: "collect", descript: descript},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "mysqlerr"){
                    window.alert("后台数据库异常导致无法收藏课程，请稍后再试");
                }
                else{
                    window.alert("收藏成功");
                    $(".coursecontainer .container-top .collect").addClass("col").data("status", "collect").find("span").text("已收藏");
                }
            },
            error: function(xhr, status) {
                window.alert("后台环境异常导致无法取消收藏，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $("#myModal button#btn_submit").click(course_reacollect);


    //------------------加载头部数据-------------------------------
    /**
     * 获取课程数据以及讲师信息
     */
    var async_coursemain = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursebyid",
            data: {id: cid},
            dataType: "json",
            success: function(data){
                var imgsrc = data.imgsrc;
                $(".coursecontainer .container-top").css("background", "url("+imgsrc+") no-repeat center center").css("background-size", "100% 100%");
                var name = data.name;
                $(".coursecontainer .container-top .title").text(name);
                var jcount = data.jcount;
                $(".coursecontainer .container-top .info li:nth-child(2) span").text(jcount);
                var total = data.total;
                $(".coursecontainer .container-top .info li:nth-child(1) span").text(total+"天");
                var price = data.price;
                $(".coursecontainer .container-top .sale .price").text("￥"+price);
                var nickname = data.nickname;
                $("#tutorinfo .info-main li .main h5").text(nickname);
                var uimgsrc = data.uimgsrc;
                $("#tutorinfo .info-main li:nth-child(1)").css("background", "url("+uimgsrc+") no-repeat center center").css("background-size", "100% 100%");
                var info = data.info;
                $("#tutorinfo .info-main li p").text(info);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_coursemain();

    /**
     * 加载课程已订人数
     * 获取用户订购信息
     */
    var async_courseorder = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/getorderinfo",
            data: {cid: cid},
            dataType: "json",
            success: function(data){
                var count = data.count;
                $(".coursecontainer .container-top .info li:nth-child(3) span").text(count);
                var state = data.state;
                //已加入购物车
                if(state == "cart"){
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().css("width", "14%").append("已加入购物车&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state","cart");
                }
                else if(state == "buy"){
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().append("已订购&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state","ok");
                }
                else{
                    $(".coursecontainer .container-top .sale div .buynow").data("state", state);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_courseorder();

    /**
     * 点击订购按钮事件
     */
    var cli_buynow = function(){

        var state = $(this).data("state");

        if(state === "invalid"){
            window.alert("订购前需要先登录哟！");
        }
        //订购
        else if(state === "valid"){
            var cid = -1;
            var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) cid = unescape(r[2]);
        }
    };
    $(".coursecontainer .container-top .sale div .buynow").click(cli_buynow);

    /**
     * 加入购物车事件
     */
    var cli_addcart = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/addcart",
            data: {cid: cid},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.alert("加入购物车前请先登录哟");
                }
                else if(status == "mysqlerr"){
                    window.alert("后台数据库异常导致无法加入购物车，请稍后在试");
                }
                else{
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().css("width", "14%").append("已加入购物车&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state","cart");
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法加入购物车，请刷新页面重试");
                window.console.log(xhr);
            }
        })
    };
    $(".coursecontainer .container-top .sale div .addcart").click(cli_addcart);



    //------------------------------导航栏的设置------------------------
    /**
     * 获取课程用户评论数据
     */
    var async_coursecommand = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/getcoursecommand",
            data: {cid: cid, startpos: 0},
            dataType: "json",
            success: function(data){
                var count = parseInt(data.count);
                if(count == 0){
                    $("#usercommand #allcommand .com-main").append("<p class=\"none\">空空如也</p>");
                    $("#usercommand .pageshow").remove();
                    return ;
                }
                else{
                    $("#usercommand .msg span").text(count);

                    //设置分页的点击，每页显示十条数据
                    var page = Math.floor(count/10) + (count-10*(Math.floor(count/10))>0?1:0);
                    while(page--){
                        if(page == 0) {
                            $("#usercommand .pageshow ul").prepend("<li class='active'><a href='javascript:void(0);' data-cur='1' data-val='\"+(page+1)+\"'>"+(page+1)+"</a></li>");
                        }
                        else{
                            $("#usercommand .pageshow ul").prepend("<li><a href='javascript:void(0);' data-cur='1' data-val='\"+(page+1)+\"'>"+(page+1)+"</a></li>");
                        }
                    }
                    $("#usercommand .pageshow ul").prepend("<li class='disabled'><a class='prev' data-cur='1' href=\"javascript:void(0);\">上一页</a></a></li>");
                    $("#usercommand .pageshow ul").prepend("<li class='disabled fade'><a class='first' data-cur='1' href=\"javascript:void(0);\">首页</a></li>");
                    $("#usercommand .pageshow ul").append("<li><a class='next' data-cur='1' href=\"javascript:void(0);\">下一页</a></a></li>");
                    $("#usercommand .pageshow ul").append("<li><a class='last' data-cur='1' href=\"javascript:void(0);\">尾页</a></li>");

                    $.each(data, function(index, item){
                       if(!(index == "count")){
                           var ctime = item.ctime;
                           var id = item.id;
                           var info = item.info;
                           var repid = item.repid;
                           var username = item.username;
                           var uimgsrc = item.uimgsrc;
                           var score = parseInt(item.score);

                           var $ul = $("<ul class='clearfix'></ul>");
                           var $imgli = $("<li class='pull-left'></li>");
                           var $mainli = $("<li class='pull-left'></li>");
                           var $showdiv = $("<div class='main-show "+id+"'></div>");
                           var $topp = $("<p class='main-top'>"+username+"&nbsp;&nbsp;&nbsp;&nbsp;</p>");
                           while(score-- != 0){
                               $topp.append("<span class='glyphicon glyphicon-star'></span>");
                           }
                           var $infop = $("<p class='main-info'>"+info+"</p>");
                           var $ctimep = $("<p class='ctime'>时间 : "+ctime+"</p>");
                           $("#allcommand .com-main").append($ul.append($imgli).append($mainli.append($showdiv.append($topp).append($infop)
                               .append($ctimep))));

                           /**
                            * 异步获取用户评价对应的家教老师回复
                            */
                           $.ajax({
                               async: false,
                               type: "post",
                               url: "/coursetreply_con/gettreply",
                               data: {cid: cid, cmid: id},
                               dataType: "json",
                               success: function(data){
                                   var info = data.info;
                                   console.log(info);
                                   if(info != "null"){
                                        var $div = $("<div class='main-reply '><p class='reply'>[讲师回复]<span> : "+info+"</span></p></div>");
                                        $(".com-main li .main-show."+id).append($div);
                                   }
                               },
                               error: function(xhr, status){
                                   window.alert("后台环境异常导致无法获取评价回复数据，请刷新页面重试");
                                   window.console.log(xhr);
                               }
                           });
                       }
                    });
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户评论数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_coursecommand();

    /**
     * 获取导师指定的神评
     */
    var async_coursegodcommand = function(){
        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/getcommandgod",
            data: {cid: cid},
            dataType: "json",
            success: function(data){
                var count = parseInt(data.count);
                if(count == 0){
                    $("#usercommand .header ul").empty();
                    return ;
                }
                else{
                    $.each(data, function(index, item){
                        if(!(index == "count")){
                            var id = item.id;
                            var info = item.info;
                            var username = item.username;
                            var uimgsrc = item.uimgsrc;
                            var score = parseInt(item.score);
                            if(index == "1"){
                                $("#usercommand .header ul").append("<li class=\"pull-left\">\n" +
                                    "                                <div class=\"hot-bg\">\n" +
                                    "                                    <p>热评推荐</p>\n" +
                                    "                                </div>\n" +
                                    "                                <div class=\"hot-show\">\n" +
                                    "                                    <div class=\"face\">\n" +
                                    "                                    </div>\n" +
                                    "                                    <h5>"+username+"</h5>\n" +
                                    "                                    <p class=\"star\"></p>\n" +
                                    "                                    <div class=\"info\">\n" +
                                    "                                        <p>"+info+"</p>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>\n" +
                                    "                            </li>");
                            }
                            else {
                                $("#usercommand .header ul").append("<li class=\"pull-left\">\n" +
                                    "                                <div class=\"hot-show\">\n" +
                                    "                                    <div class=\"face\">\n" +
                                    "                                    </div>\n" +
                                    "                                    <h5>"+username+"</h5>\n" +
                                    "                                    <p class=\"star\"></p>\n" +
                                    "                                    <div class=\"info\">\n" +
                                    "                                        <p>"+info+"</p>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>\n" +
                                    "                            </li>");
                            }

                            while(score-- != 0){
                                $("#usercommand .header ul li p.star").append("<span class='glyphicon glyphicon-star'></span>");
                            }

                            /**
                             * 异步获取用户评价对应的家教老师回复
                             */
                            $.ajax({
                                async: false,
                                type: "post",
                                url: "/coursetreply_con/gettreply",
                                data: {cid: cid, cmid: id},
                                dataType: "json",
                                success: function(data){
                                    var info = data.info;
                                    if(info != "null"){
                                        var $div = $("<div class='main-reply'><p class='reply'>[讲师回复]<span> : "+info+"</span></p></div>");
                                        $("#allcommand .com-main li .main-show").append($div);
                                    }
                                },
                                error: function(xhr, status){
                                    window.alert("后台环境异常导致无法获取神评数据，请刷新页面重试");
                                    window.console.log(xhr);
                                }
                            });
                        }
                    });
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取神评数据  ，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_coursegodcommand();

    /**
     * 判断当前用户是否已经评论过，如果评论过则按钮为我的评价，如果已经评论过，则为发表评价
     */
    var async_selmycourse = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/selmycommand",
            data: {cid: cid},
            dataType: "json",
            success: function(data){
                var count = parseInt(data.count);
                if(count == 0){
                    $("#usercommand p.msg a.sub").text("发表评价").data("status","none");
                    $("#usercommand #mycommand .com-main").append("<p class=\"command\" contenteditable=\"true\">说点什么...</p>\n" +
                        "                                <p class=\"info\">&nbsp;</p>\n" +
                        "                                <button type=\"button\" class=\"subcommand btn btn-danger\">发表</button>");
                }
                else if(count == 1){
                    $("#usercommand p.msg a.sub").text("我的评价").data("status","valid");
                    $.each(data, function(index, item){
                       if(index != "count"){
                           $.each(data, function(index, item){
                               if(!(index == "count")){
                                   var ctime = item.ctime;
                                   var id = item.id;
                                   var info = item.info;
                                   var repid = item.repid;
                                   var username = item.username;
                                   var uimgsrc = item.uimgsrc;
                                   var score = parseInt(item.score);

                                   var $ul = $("<ul class='clearfix'></ul>");
                                   var $imgli = $("<li class='pull-left'></li>");
                                   var $mainli = $("<li class='pull-left'></li>");
                                   var $showdiv = $("<div class='main-show "+id+"'></div>");
                                   var $topp = $("<p class='main-top'>"+username+"&nbsp;&nbsp;&nbsp;&nbsp;</p>");
                                   while(score-- != 0){
                                       $topp.append("<span class='glyphicon glyphicon-star'></span>");
                                   }
                                   var $infop = $("<p class='main-info'>"+info+"</p>");
                                   var $ctimep = $("<p class='ctime'>时间 : "+ctime+"</p>");
                                   $("#mycommand .com-main").append($ul.append($imgli).append($mainli.append($showdiv.append($topp).append($infop)
                                       .append($ctimep))));

                                   /**
                                    * 异步获取用户评价对应的家教老师回复
                                    */
                                   $.ajax({
                                       async: false,
                                       type: "post",
                                       url: "/coursetreply_con/gettreply",
                                       data: {cid: cid, cmid: id},
                                       dataType: "json",
                                       success: function(data){
                                           var info = data.info;
                                           if(info != "null"){
                                               var $div = $("<div class='main-reply '><p class='reply'>[讲师回复]<span> : "+info+"</span></p></div>");
                                               $("#mycommand .com-main li .main-show."+id).append($div);
                                           }
                                       },
                                       error: function(xhr, status){
                                           window.alert("后台环境异常导致无法获取评价回复数据，请刷新页面重试");
                                           window.console.log(xhr);
                                       }
                                   });
                               }
                           });
                       }
                    });
                }
                else{
                    $("#usercommand p.msg a.sub").text("发表评价").data("status","invalid");
                    $("#usercommand #mycommand").append("<p class='none'>您好，发表评价前请先<a href=\"/forward_con/gologin\" target=\"_blank\">登录</a>！</p>");
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户评论数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_selmycourse();

    /**
     * 点击我的评论按钮进行相关操作
     */
    var cli_openmycommand = function(){

        $("#usercommand .msg a:nth-child(1)").animate({
            opacity: 0
        },200).delay(200).css("display","none");
        $("#usercommand .msg a.ret").css("display", "inline-block").css("right", 0).animate({
            opacity: 1,
            left: 0
        },200);
    };
    $("#usercommand .msg a.sub").click(cli_openmycommand);

    /**
     * 点击返回关闭我的评论按钮
     */
    var cli_closemycommand = function(){
        $("#usercommand .msg a:nth-child(1)").css("display", "inline-block").animate({
            opacity: 1
        },200).delay(200);
        $("#usercommand .msg a.ret").animate({
            opacity: 0,
            right: 0
        },200).delay(200).css("display", "none");
    };
    $("#usercommand .msg a.ret").click(cli_closemycommand);

    /**
     * 我的评价编辑框失去焦点事件
     */
    var blur_editmycommand = function(){

        var command = $(this).text().trim();
        if(command === ""){
            $(this).text("说点什么...");
        }
    };
    $(document).on("blur", "#usercommand #mycommand p.command", blur_editmycommand);

    /**
     * 点击发表我的评价
     */
    var sub_editmycommand = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        var command = $(this).closest("div").find("p.command").text().trim();
        console.log(command === "" || command === "说点什么...");
        console.log(command);
        if(command === "" || command === "说点什么..."){
            $(this).closest("div").find("p.info").text("先说点什么吧");
            return ;
        }
        else{
            $(this).closest("div").find("p.info").html("&nbsp;");
            $.ajax({
                async: true,
                type: "post",
                url: "/coursecommand_con/submycommand",
                data: {cid: cid, command: command},
                success: function(data){
                    var status = data.status;
                    if(status === "mysqlerr"){
                        window.alert("后台数据库异常导致无法发表评价，请稍后再试");
                    }
                    else if(status === "invalid"){
                        window.location = "/forward_con/gologin";
                    }
                    else{
                        window.alert("发表成功");
                        window.location.reload();
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法发表评价，请刷新页面重试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(document).on("click", "#usercommand #mycommand button.subcommand", sub_editmycommand);



    //------------------------------章节目录的设置-------------------------------------
    /**
     * 异步获取章节目录数据
     */
    var async_coursechapter = function(){

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursechapter_con/getcoursechapter",
            data: {cid: cid},
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count == 0){
                    return;
                }
                $.each(data, function(index, item){
                   if(index != 0){
                       var title = item.title;
                       var descript = item.descript;
                       var $div = $("<div class='chapter'><span class='layui-icon'>&#xe651;</span></div>");
                       var $title = $("<h4>第"+index+"章&nbsp;&nbsp;"+title+"</h4>");
                       var $desc = $("<p>"+descript+"</p>");
                       $("#coursechapter").append($div.append($title).append($desc));
                   }
                });
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取章节目录数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_coursechapter();










    //-----------------侧边栏的点击事件------------------------
    /**
     * 点击回到顶部
     */
    var cli_gotop = function(){
        $(window).scrollTop(0);
        $(this).css("display", "none");
    };
    $(".sliderbar .gotop").click(cli_gotop);

    /**
     * 页面加载时判断是否需要显示回到顶部
     */
    var async_showgotop = function(){

        if($(window).scrollTop() >= 100){
            $(".sliderbar .gotop").css("display", "block");
        }
        else{
            $(".sliderbar .gotop").css("display", "none");
        }
    };
    async_showgotop();

    /**
     * 页面滚动时进行判断是否需要显示/隐藏gotop
     */
    $(window).scroll(async_showgotop);
    $(window).trigger("scroll");
});