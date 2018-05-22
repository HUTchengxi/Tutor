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
            url: "/bbscard_con/getmycardcount.json",
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
            url: "/bbscardcollect_con/getmycollectcount.json",
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
     * 获取用户的评论总数
     */
    var async_getcomcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswercommand_con/getmycommandcount.json",
            dataType: "json",
            success: function (data) {
                var count = data.count;
                $(".personal .comcount").text(count);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户的信息，请稍后再试");
                window.console.log(xhr);
            }
        });
    };

    /**
     * 获取用户的评论总数
     */
    var async_getanscount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/getmyanswercount.json",
            dataType: "json",
            success: function (data) {
                var count = data.count;
                $(".personal .anscount").text(count);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户的信息，请稍后再试");
                window.console.log(xhr);
            }
        });
    };


    /**
     * 判断当前用户是否登录
     */
    var logstatus = false;
    var login_check = function () {

        $("nav ul.navbar-right").text("");
        $.ajax({
            async: false,
            type: "post",
            url: "/login_con/login_statuscheck",
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status === "nologin") {
                    $(".personal").empty();
                    $("#writeAnswer").remove();
                    $("nav ul.navbar-right").append("<li><a style='color: red;'>您好，请先登录</a></li>\n" +
                        "                    <li><a href='/forward_con/gologin'><span class='glyphicon glyphicon-log-in'></span> 登录</a></li>" +
                        "                    <li><a href='/forward_con/goregister'><span class='glyphicon glyphicon-user'></span> 注册</a></li>\n");
                }
                else {
                    logstatus = true;
                    async_getuserinfo();
                    async_getpubcount();
                    async_getcolcount();
                    async_getcomcount();
                    async_getanscount();
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
                        "                            <li><a href='/personalpage_con/personal' target='_blank'>基本信息</a></li>\n" +
                        "                            <li><a href='/personalpage_con/gomyorder'>我的订单</a></li>\n" +
                        "                            <li><a href='/personalpage_con/gomycourse' target='_blank'>我的课程</a></li>\n" +
                        "                            <li class='sysconfig'><a href='/tutorpage_con/gosysconfig' target='_blank'><span class='mcount'>后台管理</span></a></li>\n" +
                        "                            <li><a href='/personalpage_con/gomessage' target='_blank'>通知<span class='mcount'>(" + count + ")</span></a></li>\n" +
                        "                            <li><a href='/personalpage_con/websocketpage?username=chengxi' target='_blank'>联系管理员</a></li>\n" +
                        "                            <li><a href='/personalpage_con/gofeedback'>反馈</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n" +
                        "                        </ul>\n" +
                        "                    </li>");

                    var ident = data.ident;
                    if(ident != 1){
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
     * 获取url的请求参数
     */
    var str_geturlparam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };


    /**
     * 校验当前字段是否为空
     */
    var str_isnull = function(str){
        return (str==null || str==undefined || str=="" || str=="null" || str=="undefined");
    };


    /**
     * 获取浏览器的page参数，打开相应的数据层
     */
    var async_loadpageparam = function(){

        var page = str_geturlparam("page");
        if(page != 2 && page != 1 && page != 3 && page != 4){
            page = 1;
        }
        $("header.checklist li:nth-child("+page+") a").click();
    };
    async_loadpageparam();

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
     * 异步获取我的原创数据
     */
    var async_loadmypublishinfo = function(){

        $.ajax({
            type: "post",
            url: "/bbscard_con/getmycardinfo.json",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "none"){
                    $("#pubshow").append("<div class=\"none\">还没有发表过问题哦，点击去<a href=\"/forward_con/gabble\">发表</a></div>");
                }
                else{
                    console.log(data.list);
                    //TODO: 一次性加载了所有的数据，后续加入分页
                    $.each(data.list, function(index, item){
                        $("#pubshow").append("<div class=\"cardheader clearfix\">\n" +
                            "            <div class=\"pull-left header-left\">\n" +
                            "                <h4 class=\"title\">"+item.title+"</h4>\n" +
                            "                <p class=\"descript\">"+item.descript+"</p>\n" +
                            "                <p class=\"ptimeinfo\">发起于<span class=\"ptime\">"+item.crttime+"</span></p>\n" +
                            "                <div class=\"modbtn pull-left\">\n" +
                            "                    <button class=\"combtn btn btn-link\">\n" +
                            "                        <span class=\"glyphicon glyphicon-comment\"></span>\n" +
                            "                        <span class=\"comcount\">"+item.comcount+"</span>条回答\n" +
                            "                    </button>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            <div class=\"pull-left header-right\">\n" +
                            "                <div class=\"pull-left\">\n" +
                            "                    <p>被浏览</p>\n" +
                            "                    <p class=\"viscount\">"+item.viscount+"</p>\n" +
                            "                </div>\n" +
                            "                <div class=\"pull-left\">\n" +
                            "                    <p>被收藏</p>\n" +
                            "                    <p class=\"colcount\">"+item.colcount+"</p>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            <div class=\"loadmore\">\n" +
                            "                <button class=\"btn btn-link loadmore\" data-cid=\""+item.id+"\">\n" +
                            "                    进入问题\n" +
                            "                </button>\n" +
                            "            </div>\n" +
                            "        </div>");
                    });
                }
            }
        });
    };
    async_loadmypublishinfo();


    /**
     * 异步获取我的回答数据
     */
    var async_loadmyanswerinfo = function(){

        $.ajax({
            type: "post",
            url: "/bbscardanswer_con/getmyanswerinfo.json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "none"){
                    $("#ansshow .cardmainlist").remove();
                    $("#ansshow").append("<div class=\"none\">还没有写过回答哦</div>");
                }
                else{
                    //TODO: 一次性加载了所有的数据，后续加入分页
                    $.each(data.list, function(index, item){
                        $("#ansshow .cardmainlist").append("<div class=\"cardmain\">\n" +
                            "                <div class=\"mainhead clearfix\">\n" +
                            "                    <img class=\"pull-left userface\" src=\""+item.imgsrc+"\" data-uname=\"chengxi\"/>\n" +
                            "                    <p class=\"pull-left usernick\">"+item.nickname+"</p>\n" +
                            "                </div>\n" +
                            "                <!--获取点赞人数信息-->\n" +
                            "                <a class=\"gcountinfo\"><span class=\"gcount\">"+item.gcount+"</span>个人赞同了该回答</a>\n" +
                            "                <!--回帖详细数据-->\n" +
                            "                <div class=\"ansmain\">\n" +
                            "                    <p class=\"answer\">"+item.answer+" </p>\n" +
                            "                    <p class=\"pubtimeinfo\">编辑于<span class=\"pubtime\">"+item.crttime+"</span></p>\n" +
                            "                </div>\n" +
                            "                <!--回帖相关操作-->\n" +
                            "                <div class=\"cardfooter clearfix\">\n" +
                            "                    <div class=\"pull-left btndiv\">\n" +
                            "                        <a class=\"starlink templink\" data-score=\"1\">\n" +
                            "                            <span class=\"glyphicon glyphicon-thumbs-up\"></span>\n" +
                            "                            <span class=\"goodcount\">"+item.gcount+"</span>\n" +
                            "                        </a>\n" +
                            "                        <a class=\"unstarlink templink\" data-score=\"0\">\n" +
                            "                            <span class=\"glyphicon glyphicon-thumbs-down\"></span>\n" +
                            "                            <span class=\"goodcount\">"+item.bcount+"</span>\n" +
                            "                        </a>\n" +
                            "                        <a class=\"showcommand btn btn-link\" data-status=\"off\" data-aid=\"3\">\n" +
                            "                            <span class=\"glyphicon glyphicon-comment\"></span>\n" +
                            "                            <span class=\"comcount\">"+item.comcount+"</span>条评论\n" +
                            "                        </a>\n" +
                            "                    </div>\n" +
                            "                    <div class=\"pull-right loadmore\">\n" +
                            "                        <button class=\"btn btn-link\" data-cid='"+item.cid+"'>进入帖子</button>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>");

                        $.ajax({
                            async: false,
                            type: "post",
                            url: "/bbscardanswerstar_con/checkuserstar.json",
                            data: {
                                "aid": item.aid
                            },
                            dataType: "json",
                            success: function (data) {
                                var status = data.status;
                                if (status == "star") {
                                    $(".templink.starlink").addClass("stared");
                                }
                                else if (status == "unstar") {
                                    $(".templink.unstarlink").addClass("stared");
                                }
                                $(".templink").data("status", status).removeClass("templink");
                            },
                            error: function (xhr, status) {
                                console.log(xhr);
                            }
                        });
                    });
                }
            }
        });
    };
    async_loadmyanswerinfo();


    /**
     * 异步获取我的收藏帖子数据
     */
    var async_loadmycollectinfo = function(){

        $.ajax({
            type: "post",
            url: "/bbscardcollect_con/getmycollectinfo.json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "none"){
                    $("#colshow").append("<div class=\"none\">还没有收藏过问题哦</div>");
                }
                else{
                    //TODO: 一次性加载了所有的数据，后续加入分页
                    $.each(data.list, function(index, item){
                        $("#colshow").append("<div class=\"cardheader clearfix\">\n" +
                            "            <div class=\"pull-left header-left\">\n" +
                            "                <h4 class=\"title\">"+item.title+"</h4>\n" +
                            "                <p class=\"descript\">"+item.descript+"</p>\n" +
                            "                <p class=\"ptimeinfo\">发起于<span class=\"ptime\">"+item.crtime+"</span></p>\n" +
                            "                <div class=\"modbtn pull-left\">\n" +
                            "                    <button class=\"combtn btn btn-link\">\n" +
                            "                        <span class=\"glyphicon glyphicon-comment\"></span>\n" +
                            "                        <span class=\"comcount\">"+item.comcount+"</span>条回答\n" +
                            "                    </button>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            <div class=\"pull-left header-right\">\n" +
                            "                <div class=\"pull-left\">\n" +
                            "                    <p>被浏览</p>\n" +
                            "                    <p class=\"viscount\">"+item.viscount+"</p>\n" +
                            "                </div>\n" +
                            "                <div class=\"pull-left\">\n" +
                            "                    <p>被收藏</p>\n" +
                            "                    <p class=\"colcount\">"+item.colcount+"</p>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            <div class=\"loadmore\">\n" +
                            "                <a class=\"pcolinfo\" href='javascript:;'>收藏于<span class=\"coltime\">"+item.coltime+"</span></a>\n" +
                            "                <a class=\"btn btn-link btn-uncol\" data-cid='"+item.id+"' href='javascript:;'>取消收藏</a>\n" +
                            "                <button class=\"btn btn-link\" data-cid='"+item.id+"'>进入问题</button>\n" +
                            "            </div>\n" +
                            "        </div>");
                    });
                }
            }
        });
    };
    async_loadmycollectinfo();


    /**
     * 异步获取我的评论数据
     */
    var async_loadmycommandinfo = function(){

        $.ajax({
            type: "post",
            url: "/bbscardanswercommand_con/getmycommandinfo.json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "none"){
                    $("#comshow").append("<div class=\"none\">还没有评论过问题哦</div>");
                }
                else{
                    //TODO: 一次性加载了所有的数据，后续加入分页
                    $.each(data.list, function(index, item){
                        $("#comshow").append("<div class=\"commandmain\">\n" +
                            "            <div class=\"commandmainhead clearfix\">\n" +
                            "                <p class=\"ptimeinfo pull-left\">您于<span class=\"ptime\">"+item.comtime+"</span> 评论帖子<a href=\"/forward_con/showcarddetail?cardId="+item.cid+"\">"+item.title+"</a></p>\n" +
                            "                <p class=\"pull-right comfloor\">"+item.floor+"楼</p>\n" +
                            "            </div>\n" +
                            "            <div class=\"commandmaininfo\">\n" +
                            "                <p class=\"info\">"+(!str_isnull(item.repfloor) ? "<span>@"+item.repfloor+"楼：</span>" : "")+"ing论大搜到年底</p>\n" +
                            "            </div>\n" +
                            "        </div>");
                    });
                }
            }
        });
    };
    async_loadmycommandinfo();


    /**
     * 点击进入指定原创问题
     */
    var click_gomycardbyid = function(){

        var cid = $(this).data("cid");
        window.location = "/forward_con/showcarddetail?cardId="+cid;
    };
    $(document).on("click", "#pubshow .loadmore button", click_gomycardbyid);
    $(document).on("click", "#colshow .loadmore button", click_gomycardbyid);
    $(document).on("click", "#ansshow .loadmore button", click_gomycardbyid);


    /**
     * 点击取消收藏
     */
    var click_uncollectcard = function(){

        var cid = $(this).data("cid");
        var $this = $(this);
        if(window.confirm("确定取消收藏吗？")){
            $.ajax({
                type: "post",
                url: "/bbscardcollect_con/uncollectcard.json",
                data: {
                    cardId: cid
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "uncol"){
                        $this.closest("div.cardheader").remove();
                    }
                }
            });
        }
    };
    $(document).on("click", "#colshow .loadmore a.btn-uncol", click_uncollectcard);
});