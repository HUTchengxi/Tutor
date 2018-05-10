$(function () {

    var layer = layui.layer;

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
     * 获取url的请求参数
     */
    var str_geturlparam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };

    /**
     * 判断当前用户是否登录
     */
    var login_check = function () {

        $("nav ul.navbar-right").text("");
        $.ajax({
            async: true,
            type: "post",
            url: "/login_con/login_statuscheck",
            dataType: "json",
            success: function (data) {
                var status = data.status;
                $(".coursecontainer .container-top .collect a").data("status", status);
                if (status === "nologin") {
                    $("nav ul.navbar-right").append("<li><a style='color: red;'>您好，请先登录</a></li>\n" +
                        "                    <li><a href='/forward_con/gologin'><span class='glyphicon glyphicon-log-in'></span> 登录</a></li>" +
                        "                    <li><a href='/forward_con/goregister'><span class='glyphicon glyphicon-user'></span> 注册</a></li>\n");
                }
                else {
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
                                return;
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
                        "                            <li><a href='/personalpage_con/gomyorder'>我的订单</a></li>\n" +
                        "                            <li><a href='/personalpage_con/gomycourse' target='_blank'>我的课程</a></li>\n" +
                        "                            <li class='sysconfig'><a href='/tutorpage_con/gosysconfig' target='_blank'><span class='mcount'>后台管理</span></a></li>\n" +
                        "                            <li><a href='/personalpage_con/gomessage' target='_blank'>通知<span class='mcount'>(" + count + ")</span></a></li>\n" +
                        "                            <li><a href='/personalpage_con/gofeedback'>反馈</a></li>\n" +
                        "                            <li><a href='/personalpage_con/websocketpage?username=chengxi' target='_blank'>联系管理员</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n" +
                        "                        </ul>\n" +
                        "                    </li>");

                    var ident = data.ident;
                    if (ident != 1) {
                        $("nav ul.navbar-right li.sysconfig").remove();
                    }

                    //判斷当前用户是否已收藏
                    var cid = str_geturlparam("id");
                    $.ajax({
                        url: "/coursecollect_con/checkusercollect",
                        data: {
                            cid: cid
                        },
                        dataType: "json",
                        success: function(data){
                            var status = data.status;
                            $(".coursecontainer .container-top .collect a").data("status", status);
                            if(status == "collect"){
                                $(".coursecontainer .container-top .collect a span").text("已收藏");
                            }else{
                                $(".coursecontainer .container-top .collect a span").text("收藏");
                            }
                        }
                    });
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
                    layer.msg("注销成功", {icon: 6});
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
     * 取消收藏
     */
    var course_collect = function () {

        var status = $(this).data("status");
        if (status === "nologin") {
            layer.msg("请您先登录");
            return;
        }
        //取消收藏
        var cid = str_geturlparam("id");
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);
        if (status === "collect") {
            $.ajax({
                async: true,
                type: "post",
                url: "/coursecollect_con/modusercollect",
                data: {cid: cid, mod: "uncollect"},
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if (status === "mysqlerr") {
                        window.alert("后台数据库异常导致无法取消收藏，请稍后再试");
                    }
                    else {
                        layer.msg("已取消收藏", {icon: 6});
                        $(".coursecontainer .container-top .collect").removeClass("col").find("a").data("status", "uncollect").find("span").text("收藏");
                    }
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法取消收藏，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
        else {
            $('#myModal').modal();
        }
    };
    $(".coursecontainer .container-top .collect a").click(course_collect);

    /**
     * 收藏
     */
    var course_reacollect = function () {

        var cid = str_geturlparam("id");
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
            success: function (data) {
                var status = data.status;
                if (status === "mysqlerr") {
                    window.alert("后台数据库异常导致无法收藏课程，请稍后再试");
                }
                else {
                    layer.msg("收藏成功", {icon: 6});
                    $(".coursecontainer .container-top .collect").addClass("col").find("a").data("status", "collect").find("span").text("已收藏");
                }
            },
            error: function (xhr, status) {
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
    var async_coursemain = function () {

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
            success: function (data) {
                var imgsrc = data.imgsrc;
                $(".coursecontainer .container-top").css("background", "url(" + imgsrc + ") no-repeat center center").css("background-size", "100% 100%");
                var name = data.name;
                $(".coursecontainer .container-top .title").text(name);
                var jcount = data.jcount;
                $(".coursecontainer .container-top .info li:nth-child(2) span").text(jcount);
                var total = data.total;
                $(".coursecontainer .container-top .info li:nth-child(1) span").text(total + "天");
                var price = data.price;
                $(".coursecontainer .container-top .sale .price").text("￥" + price);
                var nickname = data.nickname;
                $("#tutorinfo .info-main li .main h5").text(nickname);
                var uimgsrc = data.uimgsrc;
                $("#tutorinfo .info-main li:nth-child(1)").css("background", "url(" + uimgsrc + ") no-repeat center center").css("background-size", "100% 100%");
                var info = data.info;
                $("#tutorinfo .info-main li p").text(info);
            },
            error: function (xhr, status) {
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
    var async_courseorder = function () {

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
            success: function (data) {
                var count = data.count;
                $(".coursecontainer .container-top .info li:nth-child(3) span").text(count);
                var state = data.state;
                //已加入购物车
                if (state == "cart") {
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().css("width", "14%").append("已加入购物车&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state", "cart");
                }
                else if (state == "buy") {
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().append("已订购&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state", "ok");
                }
                else {
                    $(".coursecontainer .container-top .sale div .buynow").data("state", state);
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_courseorder();

    /**
     * 点击订购按钮事件
     */
    var cli_buynow = function () {

        var state = $(this).data("state");

        if (state === "invalid") {
            layer.msg("订购前需要先登录哟！");
        }
        //订购
        else if (state === "valid") {
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
    var cli_addcart = function () {

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
            success: function (data) {
                var status = data.status;
                if (status == "invalid") {
                    layer.msg("加入购物车前请先登录哟");
                }
                else if (status == "mysqlerr") {
                    window.alert("后台数据库异常导致无法加入购物车，请稍后在试");
                }
                else {
                    layer.msg("已加入购物车", {icon: 6});
                    $(".coursecontainer .container-top .sale div .addcart").remove();
                    $(".coursecontainer .container-top .sale div .buynow").empty().css("width", "14%").append("已加入购物车&nbsp;&nbsp;<i class='layui-icon'>&#xe605;</i>").data("state", "cart");
                }
            },
            error: function (xhr, status) {
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
    var firasynccmdflag = true;
    var async_coursecommand = function (startpos) {

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/getcoursecommand",
            data: {cid: cid, startpos: startpos},
            dataType: "json",
            success: function (data) {
                var count = parseInt(data.count);
                if (count == 0) {
                    if (firasynccmdflag == true) {
                        $("#usercommand #allcommand .com-main").append("<p class=\"none\">空空如也</p>");
                        $("#usercommand .pageshow").remove();
                    }
                    else {
                        $("#usercommand #allcommand .com-main").append("<p class=\"none\">我是有底线的~~</p>");
                    }
                    $("#allcommand p.getmore a").remove();
                    return;
                }
                else {
                    firasynccmdflag = false;
                    $("#usercommand .msg span").text(count);
                    if (count < 5) {
                        $("#allcommand p.getmore a").remove();
                    }
                    else {
                        var ori = parseInt($("#allcommand p.getmore a").data("offset"));
                        $("#allcommand p.getmore a").data("offset", (count + ori));
                    }

                    //设置分页的点击，每页显示十条数据
                    var page = Math.floor(count / 10) + (count - 10 * (Math.floor(count / 10)) > 0 ? 1 : 0);
                    while (page--) {
                        if (page == 0) {
                            $("#usercommand .pageshow ul").prepend("<li class='active'><a href='javascript:void(0);' data-cur='1' data-val='\"+(page+1)+\"'>" + (page + 1) + "</a></li>");
                        }
                        else {
                            $("#usercommand .pageshow ul").prepend("<li><a href='javascript:void(0);' data-cur='1' data-val='\"+(page+1)+\"'>" + (page + 1) + "</a></li>");
                        }
                    }
                    $("#usercommand .pageshow ul").prepend("<li class='disabled'><a class='prev' data-cur='1' href=\"javascript:void(0);\">上一页</a></a></li>");
                    $("#usercommand .pageshow ul").prepend("<li class='disabled fade'><a class='first' data-cur='1' href=\"javascript:void(0);\">首页</a></li>");
                    $("#usercommand .pageshow ul").append("<li><a class='next' data-cur='1' href=\"javascript:void(0);\">下一页</a></a></li>");
                    $("#usercommand .pageshow ul").append("<li><a class='last' data-cur='1' href=\"javascript:void(0);\">尾页</a></li>");

                    $.each(data.list, function (index, item) {
                        if (!(index == "count")) {
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
                            var $showdiv = $("<div class='main-show " + id + "'></div>");
                            var $topp = $("<p class='main-top'>" + username + "&nbsp;&nbsp;&nbsp;&nbsp;</p>");
                            while (score-- != 0) {
                                $topp.append("<span class='glyphicon glyphicon-star'></span>");
                            }
                            var $infop = $("<p class='main-info'>" + info + "</p>");
                            var $ctimep = $("<p class='ctime'>时间 : " + ctime + "</p>");
                            $("#allcommand .com-main").append($ul.append($imgli).append($mainli.append($showdiv.append($topp).append($infop)
                                .append($ctimep))));

                            /**
                             * 异步获取点赞数据
                             */
                            $.ajax({
                                async: false,
                                type: "post",
                                url: "/commandstar_con/getmycommandstar",
                                data: {cmid: id},
                                dataType: "json",
                                success: function (data) {
                                    var status = data.status;
                                    var gcount = data.gcount;
                                    var bcount = data.bcount;
                                    //se已选定，uns未评分
                                    var $cscore = $("<p class=\"ctime cscore\">\n" +
                                        "                                                <span class=\"do-good\">\n" +
                                        "                                                    赞他\n" +
                                        "                                                    <a href=\"javascript:;\" data-status=\"1\" data-cmid='" + id + "' class=\"\">\n" +
                                        "                                                        <span class=\"glyphicon glyphicon glyphicon-thumbs-up\"></span><i>" + gcount + "</i>\n" +
                                        "                                                    </a>\n" +
                                        "                                                </span>\n" +
                                        "                                                <span class=\"do-bad\">\n" +
                                        "                                                    踩他\n" +
                                        "                                                    <a href=\"javascript:;\" data-status=\"0\" data-cmid='\"+id+\"' class=\"\">\n" +
                                        "                                                        <span class=\"glyphicon glyphicon-thumbs-down\"></span><i>" + bcount + "</i>\n" +
                                        "                                                    </a>\n" +
                                        "                                                </span>\n" +
                                        "                                            </p>");
                                    $(".com-main li .main-show." + id).append($cscore);
                                    if (status == "uns") {
                                        $(".do-good a").addClass("uns");
                                        $(".do-bad a").addClass("uns");
                                    }
                                    else {
                                        if (status == "1") {
                                            $(".do-good a").addClass("se");
                                        }
                                        else {
                                            $(".do-bad a").addClass("se");
                                        }
                                    }
                                },
                                error: function (xhr, status) {
                                    window.alert("后台环境异常导致无法获取点赞数据，请稍后再试");
                                    window.console.log(xhr);
                                }
                            });

                            /**
                             * 异步获取用户评价对应的家教老师回复
                             */
                            $.ajax({
                                async: false,
                                type: "post",
                                url: "/coursetreply_con/gettreply",
                                data: {cid: cid, cmid: id},
                                dataType: "json",
                                success: function (data) {
                                    var info = data.info;
                                    if (info != "null") {
                                        var $div = $("<div class='main-reply '><p class='reply'>[讲师回复]<span> : " + info + "</span></p></div>");
                                        $(".com-main li .main-show." + id).append($div);
                                    }
                                },
                                error: function (xhr, status) {
                                    window.alert("后台环境异常导致无法获取评价回复数据，请刷新页面重试");
                                    window.console.log(xhr);
                                }
                            });
                        }
                    });
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户评论数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_coursecommand(0);


    /**
     * 点击加载更多
     */
    var cli_getmorecommand = function () {

        var offset = $(this).data("offset");
        async_coursecommand(offset);
    };
    $("#allcommand .getmore a").click(cli_getmorecommand);


    /**
     * 实现点赞与踩的功能
     */
    var cli_subcommandstar = function () {

        if (!$(this).hasClass("uns")) {
            layer.msg("您已打过分了哟");
            return;
        }

        var score = $(this).data("status");
        var cmid = $(this).data("cmid");
        var $this = $(this);
        $.ajax({
            async: true,
            type: "post",
            url: "/commandstar_con/addmystar",
            data: {cmid: cmid, score: score},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "success") {
                    var count = parseInt($this.find("i").text());
                    $this.find("i").text(count + 1);
                    $this.addClass("se");
                    $(".main-show .cscore span a.uns").removeClass("uns");
                }
                else if (status == "stared") {
                    layer.msg("您已打过分了哦");
                    return;
                }
                else if (status == "invalid") {
                    layer.msg("请先登录");
                    return;
                }
                else {
                    window.alert("后台数据库异常导致无法进行点赞功能，请稍后再试");
                    return;
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法进行点赞功能，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(document).on("click", ".main-show .cscore span a", cli_subcommandstar);


    /**
     * 获取导师指定的神评
     */
    var async_coursegodcommand = function () {
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
            success: function (data) {
                var count = parseInt(data.count);
                if (count == 0) {
                    $("#usercommand .header ul").empty();
                    return;
                }
                else {
                    $.each(data.list, function (index, item) {
                        if (!(index == "count")) {
                            var id = item.id;
                            var info = item.info;
                            var username = item.username;
                            var uimgsrc = item.uimgsrc;
                            var score = parseInt(item.score);
                            if (index == "0") {
                                $("#usercommand .header ul").append("<li class=\"pull-left\">\n" +
                                    "                                <div class=\"hot-bg\">\n" +
                                    "                                    <p>热评推荐</p>\n" +
                                    "                                </div>\n" +
                                    "                                <div class=\"hot-show\">\n" +
                                    "                                    <div class=\"face\">\n" +
                                    "                                    </div>\n" +
                                    "                                    <h5>" + username + "</h5>\n" +
                                    "                                    <p class=\"star startemp\"></p>\n" +
                                    "                                    <div class=\"info\">\n" +
                                    "                                        <p>" + info + "</p>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>\n" +
                                    "                            </li>");
                            }
                            else {
                                $("#usercommand .header ul").append("<li class=\"pull-left\">\n" +
                                    "                                <div class=\"hot-show\">\n" +
                                    "                                    <div class=\"face\">\n" +
                                    "                                    </div>\n" +
                                    "                                    <h5>" + username + "</h5>\n" +
                                    "                                    <p class=\"star startemp\"></p>\n" +
                                    "                                    <div class=\"info\">\n" +
                                    "                                        <p>" + info + "</p>\n" +
                                    "                                    </div>\n" +
                                    "                                </div>\n" +
                                    "                            </li>");
                            }

                            while (score-- != 0) {
                                $("#usercommand .header ul li p.star.startemp").append("<span class='glyphicon glyphicon-star'></span>");
                            }
                            $("#usercommand .header ul li p.star.startemp").removeClass("startemp");

                            /**
                             * 异步获取用户评价对应的家教老师回复
                             */
                            $.ajax({
                                async: false,
                                type: "post",
                                url: "/coursetreply_con/gettreply",
                                data: {cid: cid, cmid: id},
                                dataType: "json",
                                success: function (data) {
                                    var info = data.info;
                                    if (info != "null") {
                                        var $div = $("<div class='main-reply'><p class='reply'>[讲师回复]<span> : " + info + "</span></p></div>");
                                        $("#allcommand .com-main li .main-show").append($div);
                                    }
                                },
                                error: function (xhr, status) {
                                    window.alert("后台环境异常导致无法获取神评数据，请刷新页面重试");
                                    window.console.log(xhr);
                                }
                            });
                        }
                    });
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取神评数据  ，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_coursegodcommand();

    /**
     * 判断当前用户是否已经评论过，如果评论过则按钮为我的评价，如果已经评论过，则为发表评价
     */
    var async_selmycourse = function () {

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
            success: function (data) {
                var count = parseInt(data.count);
                var status = data.status;
                if (count == 0) {
                    $("#usercommand p.msg a.sub").text("发表评价").data("status", "none");
                    $("#usercommand #mycommand .com-main").append("<p class=\"command\" contenteditable=\"true\">说点什么...</p>\n" +
                        "<p class=\"score\" data-score=\"0\">课程评分：\n" +
                        "                                    <span class=\"glyphicon glyphicon-star-empty s1\" data-score=\"1\"></span>\n" +
                        "                                    <span class=\"glyphicon glyphicon-star-empty s2\" data-score=\"2\"></span>\n" +
                        "                                    <span class=\"glyphicon glyphicon-star-empty s3\" data-score=\"3\"></span>\n" +
                        "                                    <span class=\"glyphicon glyphicon-star-empty s4\" data-score=\"4\"></span>\n" +
                        "                                    <span class=\"glyphicon glyphicon-star-empty s5\" data-score=\"5\"></span>\n" +
                        "                                </p>" +
                        "                                <p class=\"info\">&nbsp;</p>\n" +
                        "                                <button type=\"button\" class=\"subcommand btn btn-danger\">发表</button>");
                }
                else if (count == 1) {
                    $("#usercommand p.msg a.sub").text("我的评价").data("status", "valid");
                    $.each(data.list, function (index, item) {
                        if (index != "count") {
                            $.each(data, function (index, item) {
                                if (!(index == "count")) {
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
                                    var $showdiv = $("<div class='main-show " + id + "'></div>");
                                    var $topp = $("<p class='main-top'>" + username + "&nbsp;&nbsp;&nbsp;&nbsp;</p>");
                                    while (score-- != 0) {
                                        $topp.append("<span class='glyphicon glyphicon-star'></span>");
                                    }
                                    var $infop = $("<p class='main-info'>" + info + "</p>");
                                    var $ctimep = $("<p class='ctime'>时间 : " + ctime + "</p>");
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
                                        success: function (data) {
                                            var info = data.info;
                                            if (info != "null") {
                                                var $div = $("<div class='main-reply '><p class='reply'>[讲师回复]<span> : " + info + "</span></p></div>");
                                                $("#mycommand .com-main li .main-show." + id).append($div);
                                            }
                                        },
                                        error: function (xhr, status) {
                                            window.alert("后台环境异常导致无法获取评价回复数据，请刷新页面重试");
                                            window.console.log(xhr);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else if (status == "nobuy") {
                    $("#usercommand p.msg a.sub").text("发表评价").data("status", "invalid");
                    $("#usercommand #mycommand .com-main").append("<p class='none'>只有购买的用户才能进行评价哦！</p>");
                }
                else {
                    $("#usercommand p.msg a.sub").text("发表评价").data("status", "invalid");
                    $("#usercommand #mycommand .com-main").append("<p class='none'>您好，发表评价前请先<a href=\"/forward_con/gologin\" target=\"_blank\">登录</a>！</p>");
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取用户评论数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_selmycourse();

    /**
     * 点击我的评论按钮进行相关操作
     */
    var cli_openmycommand = function () {

        $("#usercommand .msg a:nth-child(1)").animate({
            opacity: 0
        }, 200).delay(200).css("display", "none");
        $("#usercommand .msg a.ret").css("display", "inline-block").css("right", 0).animate({
            opacity: 1,
            left: 0
        }, 200);
    };
    $("#usercommand .msg a.sub").click(cli_openmycommand);

    /**
     * 点击返回关闭我的评论按钮
     */
    var cli_closemycommand = function () {
        $("#usercommand .msg a:nth-child(1)").css("display", "inline-block").animate({
            opacity: 1
        }, 200).delay(200);
        $("#usercommand .msg a.ret").animate({
            opacity: 0,
            right: 0
        }, 200).delay(200).css("display", "none");
    };
    $("#usercommand .msg a.ret").click(cli_closemycommand);

    /**
     * 我的评价编辑框失去焦点事件
     */
    var blur_editmycommand = function () {

        var command = $(this).text().trim();
        if (command === "") {
            $(this).text("说点什么...");
        }
    };
    $(document).on("blur", "#usercommand #mycommand p.command", blur_editmycommand);

    /**
     * 进行课程评论星级
     */
    var mouseenter_coursescore = function () {

        var score = $(this).data("score");
        var temp = score;
        while (score != 0) {
            $("#mycommand .com-main .s" + (score--)).removeClass("glyphicon-star-empty").addClass("glyphicon-star");
        }
        while (temp != 5) {
            $("#mycommand .com-main .s" + (++temp)).addClass("glyphicon-star-empty").removeClass("glyphicon-star");
        }
    };
    $(document).on("mouseenter", "#mycommand .com-main .score span", mouseenter_coursescore);


    /**
     * 评价星级点击确定评分
     **/
    var cli_confirmscore = function () {

        var score = $(this).data("score");
        $(this).closest("p").data("score", score);
    };
    $(document).on("click", "#mycommand .com-main .score span", cli_confirmscore);

    /**
     * 鼠标离开评分span
     */
    var mouseleave_commandscore = function () {

        var score = $(this).closest("p").data("score");
        var temp = score;
        while (score != 0) {
            $("#mycommand .com-main .s" + (score--)).removeClass("glyphicon-star-empty").addClass("glyphicon-star");
        }
        while (temp != 5) {
            $("#mycommand .com-main .s" + (++temp)).addClass("glyphicon-star-empty").removeClass("glyphicon-star");
        }
    };
    $(document).on("mouseleave", "#mycommand .com-main .score span", mouseleave_commandscore);


    /**
     * 点击发表我的评价
     */
    var sub_editmycommand = function () {

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        var command = $(this).closest("div").find("p.command").text().trim();
        var score = $("#mycommand .com-main p.score").data("score");
        if (command === "" || command === "说点什么...") {
            $(this).closest("div").find("p.info").text("先说点什么吧");
            return;
        }
        else if (score == 0) {
            $(this).closest("div").find("p.info").text("多少给点分数呗");
            return;
        }
        else {
            $(this).closest("div").find("p.info").html("&nbsp;");
            $.ajax({
                async: true,
                type: "post",
                url: "/coursecommand_con/submycommand",
                data: {cid: cid, command: command, score: score},
                success: function (data) {
                    var status = data.status;
                    if (status === "mysqlerr") {
                        window.alert("后台数据库异常导致无法发表评价，请稍后再试");
                    }
                    else if (status === "invalid") {
                        window.location = "/forward_con/gologin";
                    }
                    else {
                        layer.msg("发表成功", {icon: 6});
                        window.location.reload();
                    }
                },
                error: function (xhr, status) {
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
    var async_coursechapter = function () {

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
            success: function (data) {
                var count = data.count;
                if (count == 0) {
                    return;
                }
                $.each(data.list, function (index, item) {
                    var title = item.title;
                    var descript = item.descript;
                    var index = parseInt(index) + 1;
                    var $div = $("<div class='chapter'><span class='layui-icon'>&#xe651;</span></div>");
                    var $title = $("<h4>第" + index + "章&nbsp;&nbsp;" + title + "</h4>");
                    var $desc = $("<p>" + descript + "</p>");
                    $("#coursechapter").append($div.append($title).append($desc));
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取章节目录数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_coursechapter();


    //------------------课程概述----------------------
    /**
     * 异步获取课程概述数据
     */
    var async_loadcoursesummary = function () {

        var cid = -1;
        var reg = new RegExp("(^|&)id=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) cid = unescape(r[2]);

        $.ajax({
            type: "post",
            url: "/coursemain_con/getcoursesummary",
            data: {
                cid: cid
            },
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "none") {
                    $(".descmain").remove();
                    $("#coursedesc").append("<p class='none'>暂无概述</p>");
                } else {
                    $.each(data, function (index, item) {

                        if (index == "nickname") {
                            $("#coursedesc .nickname").text(item);
                        } else if (index == "imgsrc") {
                            $("#coursedesc img").attr("src", item);
                        } else {
                            $.each(item, function (key, value) {
                                if (value.title == "适合人群") {
                                    $("#coursedesc .suite").text(value.descript);
                                } else if (value.title == "课程概述") {
                                    $("#coursedesc .desc").text(value.descript);
                                } else {
                                    $("#coursedesc .markinfo").text(value.descript);
                                }
                            });
                        }
                    });
                }
            }
        });
    };
    async_loadcoursesummary();


    /**
     * 点击查看全部评价，默认触发对应的tab点击
     */
    var link_gousercommand = function(){
        $("#link-gocommand").click();
    };
    $(".link-command").click(link_gousercommand);

    /**
     * 点击查看讲师信息，默认触发对应的tab点击
     */
    var link_gotutorinfo = function(){
        $("#link-goinfo").click();
    };
    $(".link-tutorinfo").click(link_gotutorinfo);


    //-----------------侧边栏的点击事件------------------------
    /**
     * 点击回到顶部
     */
    var cli_gotop = function () {
        $(window).scrollTop(0);
        $(this).css("display", "none");
    };
    $(".sliderbar .gotop").click(cli_gotop);

    /**
     * 页面加载时判断是否需要显示回到顶部
     */
    var async_showgotop = function () {

        if ($(window).scrollTop() >= 100) {
            $(".sliderbar .gotop").css("display", "block");
        }
        else {
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