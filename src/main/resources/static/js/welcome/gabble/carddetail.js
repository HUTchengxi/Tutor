$(function () {

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
    };


    /**
     * 获取用户的imgsrc和nickname
     */
    var async_getuserinfo = function () {

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/getuserinfo",
            dataType: "json",
            success: function (data) {
                //资料卡片更新
                $(".personal img").attr("src", data.imgsrc);
                $(".personal p.userinfo").text(data.nickname);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户数据，请稍后再试");
                console.log(xhr);
            }
        });
    };


    /**
     * 获取用户的原创总数
     */
    var async_getpubcount = function () {

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscard_con/getmycardcount",
            dataType: "json",
            success: function (data) {
                var count = data.count;
                $(".personal .pubcount").text(count);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户的信息，请稍后再试");
                window.console.log(xhr);
            }
        });
    };


    /**
     * 获取用户的收藏总数
     */
    var async_getcolcount = function () {

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardcollect_con/getmycollectcount",
            dataType: "json",
            success: function (data) {
                var count = data.count;
                $(".personal .colcount").text(count);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户的信息，请稍后再试");
                window.console.log(xhr);
            }
        });
    };


    /**
     * 异步获取背景图片的imgsrc
     */
    var async_getImgsrc = function () {
        $.ajax({
            async: true,
            type: "post",
            url: "/commonimgsrc_con/getAll",
            dataType: "json",
            success: function (data) {
                var img = "";
                $("#pubModal .modal-body select").empty();
                $.each(data, function (index, item) {
                    var title = item.title;
                    var imgsrc = item.imgsrc;
                    if(img == ""){
                        img = imgsrc;
                    }
                    $("#pubModal .modal-body select").append("<option value='" + imgsrc + "'>"+title+"</option>");
                    $("#pubModal .modal-body img").attr("src", img);
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取背景图片数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };


    /**
     * 判断当前用户是否登录
     */
    var logstatus = true;
    var login_check = function () {

        $("nav ul.navbar-right").text("");
        $.ajax({
            async: true,
            type: "post",
            url: "/login_con/login_statuscheck",
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status === "nologin") {
                    $(".personal").empty();
                    $("#pubModal").empty();
                    logstatus = false;
                    $("nav ul.navbar-right").append("<li><a style='color: red;'>您好，请先登录</a></li>\n" +
                        "                    <li><a href='/forward_con/gologin'><span class='glyphicon glyphicon-log-in'></span> 登录</a></li>" +
                        "                    <li><a href='/forward_con/goregister'><span class='glyphicon glyphicon-user'></span> 注册</a></li>\n");
                }
                else {
                    async_getuserinfo();
                    async_getpubcount();
                    async_getcolcount();
                    async_getImgsrc();
                    $(".personal").css("display", "block");
                    var hour = new Date().getHours();
                    var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                    var hello = null;
                    if (hour >= 6 && hour <= 12) {
                        hello = "早上好";
                    }
                    else if (hour >= 13 && hour < 18) {
                        hello = "下午好";
                    }
                    else {
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
                        success: function (data) {
                            var status = data.status;
                            if (status === "invalid") {
                                window.location = "/forward_con/welcome";
                            }
                            else if (status === "mysqlerr") {
                                window.alert("后台服务器异常导致无法获取通知数据，请刷新页面重试");
                            }
                            else {
                                count = data.count;
                            }
                        },
                        error: function (xhr, status) {
                            window.alert("后台环境异常导致");
                            window.console.log(xhr);
                        }
                    });

                    $("nav ul.navbar-right").append("<li class='nav-time'><a>现在是：<i style='color: #00b6ff;'>" + now + "</i></a></li>" +
                        "                   <li><a style=\"color: black;\"><p>" + hello + "：<span class=\"span-cls-nick\" style=\"color: red;\">" + nickname + "</span></p></a></li>\n" +
                        "                    <li class=\"dropdown\">\n" +
                        "                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">个人中心<i class=\"caret\"></i></a>\n" +
                        "                        <ul class=\"dropdown-menu\">\n" +
                        "                            <li><a href='/forward_con/personal' target='_blank'>基本信息</a></li>\n" +
                        "                            <li><a href='/forward_con/gomyorder'>我的订单</a></li>\n" +
                        "                            <li><a href='/forward_con/gomycourse' target='_blank'>我的课程</a></li>\n" +
                        "                            <li class='sysconfig'><a href='/forward_con/gosysconfig' target='_blank'><span class='mcount'>后台管理</span></a></li>\n" +
                        "                            <li><a href='/forward_con/gomessage' target='_blank'>通知<span class='mcount'>(" + count + ")</span></a></li>\n" +
                        "                            <li><a href='/forward_con/gosetting'>设置</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n" +
                        "                        </ul>\n" +
                        "                    </li>");

                    var ident = data.ident;
                    if (ident == 0 || ident == -2) {
                        $("nav ul.navbar-right li.sysconfig").remove();
                    }
                }

                //让时间一直轮播
                var timeinter = window.setInterval(function () {
                    var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                    $("nav ul.navbar-right li.nav-time a i").text(now);
                }, 1000);
            },
            error: function (xhr, status) {
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


    /**
     * 页面滚动到一定px固定资料卡片
     */
    var window_scroll = function () {

        var scrollTop = $(this).scrollTop();
        if (scrollTop >= 180) {
            $(".personal").css("position", "fixed").css("top", "76px");
        }
        else {
            $(".personal").css("position", "absolute").css("top", "255px");
        }
    };
    $(window).scroll(window_scroll);
    $(window).trigger("scroll");


    /**
     * 获取url的请求参数
     */
    var str_geturlparam = function(name){
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    };


    //-----------------------------------------------------
    /**
     * 获取对应的问题数据
     */
    var async_getcardinfo = function(){

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscard_con/getcardbyid",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function(data){
                $(".cardheader .title").text(data.title);
                $(".cardheader .descript").text(data.descript);
                $(".cardheader span.ptime").text(data.crtime);
                $(".cardheader .userinfo img.userface").attr("src", data.imgsrc);
                $(".cardheader .userinfo p.usernick").text(data.nickname);
                $(".cardheader .userface img.userface").data("uname", data.username);
                $(".cardheader .combtn .comcount").text(data.comcount);
                $("body > div.carddatashow.clearfix > p > span").text(data.comcount);
                $(".cardheader .header-right .viscount").text(data.viscount);
                $(".cardheader .header-right .colcount").text(data.colcount);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取问题数据，请稍后再试");
                console.log(xhr);
            }
        });
    };
    async_getcardinfo();

    /**
     * 获取对应的问题的回答数据
     */
    var async_getcardanswerinfo = function(){

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/getcardanswerbycardid",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function(data){
                console.log(data);
                $.each(data, function(index, item){
                    $(".cardmainlist").append("<div class=\"cardmain\">\n" +
                        "        <!--回帖用户个人信息展示-->\n" +
                        "        <div class=\"mainhead clearfix\">\n" +
                        "            <img class=\"pull-left userface\" src=\""+item.imgsrc+"\" data-uname='"+item.username+"'/>\n" +
                        "            <p class=\"pull-left usernick\">"+item.nickname+"</p>\n" +
                        "        </div>\n" +
                        "        <!--获取点赞人数信息-->\n" +
                        "        <a class=\"gcountinfo\"><span class=\"gcount\">"+item.gcount+"</span>个人赞同了该回答</a>\n" +
                        "        <!--回帖详细数据-->\n" +
                        "        <div class=\"ansmain\">\n" +
                        "            <p class=\"answer\">" +item.answer +
                        "            </p>\n" +
                        "            <p class=\"pubtimeinfo\">编辑于<span class=\"pubtime\">"+item.crttime+"</span></p>\n" +
                        "        </div>\n" +
                        "        <!--回帖相关操作-->\n" +
                        "        <div class=\"cardfooter clearfix\">\n" +
                        "            <div class=\"pull-left btndiv\">\n" +
                        "                <a class=\"starlink\">\n" +
                        "                    <span class=\"glyphicon glyphicon-thumbs-up\"></span>\n" +
                        "                    <span class=\"goodcount\">"+item.gcount+"</span>\n" +
                        "                </a>\n" +
                        "                <a class=\"unstarlink\">\n" +
                        "                    <span class=\"glyphicon glyphicon-thumbs-down\"></span>\n" +
                        "                    <span class=\"goodcount\">"+item.bcount+"</span>\n" +
                        "                </a>\n" +
                        "                <a class=\"showcommand btn btn-link\" data-status='off' data-aid='"+item.id+"'>\n" +
                        "                    <span class=\"glyphicon glyphicon-comment\"></span>\n" +
                        "                    <span class=\"comcount\">"+item.comcount+"</span>条评论\n" +
                        "                    <span class=\"glyphicon glyphicon-arrow-down\"></span>" +
                        "                </a>\n" +
                        "                <a class=\"report btn btn-link\">\n" +
                        "                    <span class=\"glyphicon glyphicon-bell\"></span>举报\n" +
                        "                </a>\n" +
                        "            </div>\n" +
                        "\n" +
                        "        </div>\n" +
                        "        <!--该回答评论列表-->\n" +
                        "        <div class=\"commandlist\">\n" +
                        "            <!--评论列表头部-->\n" +
                        "            <div class=\"listheader clearfix\">\n" +
                        "                <p class=\"pull-left\">全部<span class=\"comcount\">"+item.comcount+"</span>个回答</p>\n" +
                        "                <button class=\"btn btn-link pull-right\">已按时间排序</button>\n" +
                        "            </div>\n" +
                        "            <!--评论列表详情数据-->\n" +
                        "            <div class=\"commandmain\"></div>\n" +
                        "            <!--输入评论form表单框-->\n" +
                        "            <div class=\"mycommandinfo clearfix\">\n" +
                        "                <input type=\"text\" class=\"pull-left col-lg-10\" name=\"mycommand\" placeholder=\"在这里输入你的评论\" />\n" +
                        "                <button class=\"btn subcommand pull-left\">发表评论</button>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>");
                });
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取问题数据，请稍后再试");
                console.log(xhr);
            }
        });
    };
    async_getcardanswerinfo();


    /**
     * 点击查看指定的答案的评论数据
     */
    var click_showanswercommand = function(){

        var status = $(this).data("status");
        var aid = $(this).data("aid");
        if(status == "off"){
            $(this).data("status", "on");
            $(this).find("span.glyphicon-arrow-down").removeClass("glyphicon-arrow-down")
                .addClass("glyphicon-arrow-up");
            $(this).closest("div.cardmain").find("div.commandlist").css("display", "block");
        }
        else{
            $(this).closest("div.cardmain").find("div.commandlist").css("display", "none");
            $(this).data("status", "off");
            $(this).find("span.glyphicon-arrow-up").removeClass("glyphicon-arrow-up")
                .addClass("glyphicon-arrow-down");
        }
    };
    $(document).on("click", ".cardmainlist div.cardfooter a.showcommand", click_showanswercommand);

});