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
     * 获取用户的评论总数
     */
    var async_getanscount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/getmyanswercount",
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
     * 获取用户的回答总数
     */
    var async_getcomcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswercommand_con/getmycommandcount",
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
                $.each(data.list, function (index, item) {
                    var title = item.title;
                    var imgsrc = item.imgsrc;
                    if (img == "") {
                        img = imgsrc;
                    }
                    $("#pubModal .modal-body select").append("<option value='" + imgsrc + "'>" + title + "</option>");
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
    var str_geturlparam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };


    //-----------------------------------------------------
    /**
     * 获取对应的问题数据
     */
    var async_getcardinfo = function () {

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscard_con/getcardbyid",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function (data) {
                $("html head title").text(data.title+"----论坛中心----勤成家教网");
                $(".cardheader .title").text(data.title);
                $(".cardheader .descript").text(data.descript);
                $(".cardheader span.ptime").text(data.crttime);
                $(".cardheader .userinfo img.userface").attr("src", data.imgsrc);
                $(".cardheader .userinfo p.usernick").text(data.nickname);
                $(".cardheader .userface img.userface").data("uname", data.username);
                $(".cardheader .combtn .comcount").text(data.comcount);
                $("body > div.carddatashow.clearfix > p > span").text(data.comcount);
                $(".cardheader .header-right .viscount").text(data.viscount);
                $(".cardheader .header-right .colcount").text(data.colcount);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取问题数据，请稍后再试");
                console.log(xhr);
            }
        });
    };
    async_getcardinfo();


    /**
     * 对应的问题访问量加1
     */
    var async_addcardviscount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscard_con/addviscount",
            data: {cardid: str_geturlparam("cardId")},
            dataType: "json",
            success: function(data){
            },
            error: function(xhr, status){
                console.log(xhr);
            }
        });
    };
    async_addcardviscount();


    /**
     * 获取对应的问题的回答数据
     */
    var async_getcardanswerinfo = function () {

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/getcardanswerbycardid",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "none") return;
                $.each(data.list, function (index, item) {
                    $(".cardmainlist").append("<div class=\"cardmain\" data-id='" + item.id + "'>\n" +
                        "        <!--回帖用户个人信息展示-->\n" +
                        "        <div class=\"mainhead clearfix\">\n" +
                        "            <img class=\"pull-left userface\" src=\"" + item.imgsrc + "\" data-uname='" + item.username + "'/>\n" +
                        "            <p class=\"pull-left usernick\">" + item.nickname + "</p>\n" +
                        "        </div>\n" +
                        "        <!--获取点赞人数信息-->\n" +
                        "        <a class=\"gcountinfo\"><span class=\"gcount\">" + item.gcount + "</span>个人赞同了该回答</a>\n" +
                        "        <!--回帖详细数据-->\n" +
                        "        <div class=\"ansmain\">\n" +
                        "            <p class=\"answer\">" + item.answer +
                        "            </p>\n" +
                        "            <p class=\"pubtimeinfo\">编辑于<span class=\"pubtime\">" + item.crttime + "</span></p>\n" +
                        "        </div>\n" +
                        "        <!--回帖相关操作-->\n" +
                        "        <div class=\"cardfooter clearfix\">\n" +
                        "            <div class=\"pull-left btndiv\">\n" +
                        "                <a class=\"starlink tempstar\" data-score='1'>\n" +
                        "                    <span class=\"glyphicon glyphicon-thumbs-up\"></span>\n" +
                        "                    <span class=\"goodcount\">" + item.gcount + "</span>\n" +
                        "                </a>\n" +
                        "                <a class=\"unstarlink tempstar\" data-score='0'>\n" +
                        "                    <span class=\"glyphicon glyphicon-thumbs-down\"></span>\n" +
                        "                    <span class=\"goodcount\">" + item.bcount + "</span>\n" +
                        "                </a>\n" +
                        "                <a class=\"showcommand btn btn-link\" data-status='off' data-aid='" + item.id + "'>\n" +
                        "                    <span class=\"glyphicon glyphicon-comment\"></span>\n" +
                        "                    <span class=\"comcount\">" + item.comcount + "</span>条评论\n" +
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
                        "                <p class=\"pull-left\">全部<span class=\"comcount\">" + item.comcount + "</span>个回答</p>\n" +
                        "                <button class=\"btn btn-link pull-right\">已按时间排序</button>\n" +
                        "            </div>\n" +
                        "            <!--评论列表详情数据-->\n" +
                        "            <div class=\"commandmain\"></div>\n" +
                        "            <!--输入评论form表单框-->\n" +
                        "            <div class=\"mycommandinfo clearfix\">\n" +
                        "                <input type=\"text\" class=\"pull-left col-lg-10 col-sm-10 col-xs-10\" name=\"mycommand\" placeholder=\"在这里输入你的评论\" />\n" +
                        "                <button class=\"btn subcommand pull-left\" data-cardid='"+cardId+"'>发表评论</button>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>");

                    //判断当前登录用户是否对该评论进行过评分
                    if (!logstatus) {
                        $(".tempstar").removeClass("tempstar");
                    }
                    else {
                        $.ajax({
                            async: false,
                            type: "post",
                            url: "/bbscardanswerstar_con/checkuserstar",
                            data: {
                                "aid": item.id
                            },
                            dataType: "json",
                            success: function (data) {
                                var status = data.status;
                                if (status == "star") {
                                    $(".tempstar.starlink").addClass("stared");
                                }
                                else if (status == "unstar") {
                                    $(".tempstar.unstarlink").addClass("stared");
                                }
                                $(".tempstar").data("status", status).removeClass("tempstar");
                            },
                            error: function (xhr, status) {
                                console.log(xhr);
                            }
                        });
                    }
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取问题数据，请稍后再试");
                console.log(xhr);
            }
        });
    };
    async_getcardanswerinfo();


    /**
     * 点击查看指定的答案的评论数据
     */
    var state = false;
    var click_showanswercommand = function () {

        var status = $(this).data("status");
        var aid = $(this).data("aid");
        if (status == "off") {
            $(this).data("status", "on");
            $(this).find("span.glyphicon-arrow-down").removeClass("glyphicon-arrow-down")
                .addClass("glyphicon-arrow-up");
            $(this).closest("div.cardmain").find("div.commandlist").css("display", "block");

            if (!state) {
                //获取五条评论数据
                state = true;
                var $this = $(this);
                $.ajax({
                    async: true,
                    type: "post",
                    url: "/bbscardanswercommand_con/getcommandlistbyaid",
                    data: {
                        aid: aid,
                        startpos: 0
                    },
                    dataType: "json",
                    success: function (data) {
                        var status = data.status;
                        if (status == "none") {
                            $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class='none'>当前暂无评论，快来抢沙发吧</div>");
                        }
                        else {
                            var count = 0;
                            $.each(data.list, function (index, item) {
                                count++;
                                var id = item.id;
                                var comtime = item.comtime;
                                var command = item.descript;
                                var floor = item.floor;
                                var cardid = item.cardid;
                                var imgsrc = item.imgsrc;
                                var nickname = item.nickname;
                                var repfloor = item.repfloor;
                                $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class=\"commandmainhead clearfix\">\n" +
                                    "    <img class=\"pull-left comuserface\" src=\"" + imgsrc + "\" />\n" +
                                    "    <p class=\"pull-left comusernick\">" + nickname + "" + ((repfloor != "null" && repfloor != '' && repfloor != undefined) ? "<span>@</span>" + repfloor + "楼" : "") + "</p>" +
                                    "    <p class=\"pull-right comfloor\">" + floor + "楼</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"commandmaininfo\">\n" +
                                    "    <p class=\"info\">" + command + "</p>\n" +
                                    "    <p class=\"ptimeinfo\">评论于<span class=\"ptime\">" + comtime + "</span></p>\n" +
                                    "</div>\n" +
                                    "<div class=\"btngroup\">\n" +
                                    "    <a href=\"javascript:;\" class=\"recommand\" data-repfloor='"+floor+"' data-cardid='"+cardid+"' data-aid='"+id+"'>\n" +
                                    "        <span class=\"glyphicon glyphicon-share-alt\"></span>回复\n" +
                                    "    </a>\n" +
                                    "    <a href=\"javascript:;\" class=\"report btn btn-link\">\n" +
                                    "        <span class=\"glyphicon glyphicon-bell\"></span>举报\n" +
                                    "    </a>\n" +
                                    "</div>");
                            });
                            if (count < 5) {
                                $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class='loadmore'><a href='javascript:;' data-status='none'>--我是有底线的--</a></div>");
                            }
                            else {
                                $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class='loadmore'><a href='javascript:;' data-startpos='1' data-id='" + aid + "'>加载更多</a></div>");
                            }
                        }
                    },
                    error: function (xhr, status) {
                        console.log(xhr);
                    }
                });
            }
        }
        else {
            $(this).closest("div.cardmain").find("div.commandlist").css("display", "none");
            $(this).data("status", "off");
            $(this).find("span.glyphicon-arrow-up").removeClass("glyphicon-arrow-up")
                .addClass("glyphicon-arrow-down");
        }
    };
    $(document).on("click", ".cardmainlist div.cardfooter a.showcommand", click_showanswercommand);


    /**
     * 点击加载更多评论数据
     */
    var click_loadmorecommand = function () {

        var status = $(this).data("status");
        if (status == "none") {
            return;
        }
        var startpos = parseInt($(this).data("startpos"));
        var aid = parseInt($(this).data("id"));
        var $this = $(this);
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswercommand_con/getcommandlistbyaid",
            data: {
                aid: aid,
                startpos: startpos
            },
            dataType: "json",
            success: function (data) {
                var count = 0;
                if(data.status != "none") {
                    $.each(data.list, function (index, item) {
                        count++;
                        var id = item.id;
                        var comtime = item.comtime;
                        var command = item.descript;
                        var floor = item.floor;
                        var imgsrc = item.imgsrc;
                        var nickname = item.nickname;
                        var repfloor = item.repfloor;
                        $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class=\"commandmainhead clearfix\">\n" +
                            "    <img class=\"pull-left comuserface\" src=\"" + imgsrc + "\" />\n" +
                            "    <p class=\"pull-left comusernick\">" + nickname + "" + (repfloor != "null" ? "<span>@</span>" + repfloor + "楼" : "") + "</p>" +
                            "    <p class=\"pull-right comfloor\">" + floor + "楼</p>\n" +
                            "</div>\n" +
                            "<div class=\"commandmaininfo\">\n" +
                            "    <p class=\"info\">" + command + "</p>\n" +
                            "    <p class=\"ptimeinfo\">评论于<span class=\"ptime\">" + comtime + "</span></p>\n" +
                            "</div>\n" +
                            "<div class=\"btngroup\">\n" +
                            "    <a href=\"javascript:;\" class=\"recommand\">\n" +
                            "        <span class=\"glyphicon glyphicon-share-alt\"></span>回复\n" +
                            "    </a>\n" +
                            "    <a href=\"javascript:;\" class=\"report btn btn-link\">\n" +
                            "        <span class=\"glyphicon glyphicon-bell\"></span>举报\n" +
                            "    </a>\n" +
                            "</div>");
                    });
                }
                if (count < 5) {
                    $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class='loadmore'><a href='javascript:;' data-status='none'>--我是有底线的--</a></div>");
                }
                else {
                    $this.closest("div.cardmain").find(".commandlist .commandmain").append("<div class='loadmore'><a href='javascript:;' data-startpos='"+(startpos+1)+"' data-id='" + aid + "'>加载更多</a></div>");
                }
                $this.closest("div.loadmore").remove();
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    $(document).on("click", ".cardmainlist .commandmain .loadmore a", click_loadmorecommand);


    /**
     * 点击回复指定评论
     */
    var click_openRepCommand = function(){

        var repfloor = $(this).data("repfloor");
        $(this).closest("div.commandlist").find(".mycommandinfo input").val("@"+repfloor+"楼:").focus();
        $(this).closest("div.commandlist").find(".mycommandinfo button").data("repfloor", repfloor);
    };
    $(document).on("click", ".cardmainlist .commandmain a.recommand", click_openRepCommand);


    /**
     * 点击发表评论
     */
    var click_publishCommand = function(){

        if(!logstatus){
            layer.msg("请先登录");
            return ;
        }

        var answer = $(this).closest("div.mycommandinfo").find("input").val();
        var cardid = $(this).data("cardid");
        var aid = $(this).closest("div.cardmain").data("id");
        answer = answer.trim();
        var repfloor = $(this).data("repfloor");
        //进行回复
        if(answer.indexOf("@"+repfloor+"楼:") == 0){
            answer = answer.replace("@"+repfloor+"楼:", "");
            if(answer.trim() == ""){
                layer.msg("回复内容不能为空");
                return ;
            }
            $.ajax({
                async: true,
                type: "post",
                url: "/bbscardanswercommand_con/publishcommand",
                data: {
                    answer: answer.trim(),
                    cardid: cardid,
                    aid: aid,
                    repfloor: repfloor
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        layer.msg("回复成功", {icon: 6});
                        $(".commandlist .mycommandinfo input").val("");
                        return ;
                    }
                }
            });
        }
        //进行评论
        else{
            console.log(answer);
            if(answer.trim() == ""){
                layer.msg("回复内容不能为空");
                return ;
            }
            $.ajax({
                async: true,
                type: "post",
                url: "/bbscardanswercommand_con/publishcommand",
                data: {
                    answer: answer.trim(),
                    cardid: cardid,
                    aid: aid
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        layer.msg("评论成功", {icon: 6});
                        $(".commandlist .mycommandinfo input").val("");
                        return ;
                    }
                }
            });
        }
    };
    $(document).on("click", ".commandlist .mycommandinfo button", click_publishCommand);


    /**
     * 当前用户是否收藏了问题
     */
    var async_checkUserCollect = function () {

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardcollect_con/checkcollectstatus",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "col") {
                    $(".cardheader .header-left .modbtn .colbtn").text("已收藏");
                }
                else {
                    $(".cardheader .header-left .modbtn .colbtn").text("收藏问题");
                }
                $(".cardheader .header-left .modbtn .colbtn").data("status", status);
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    async_checkUserCollect();


    /**
     * 点击收藏问题
     */
    var click_collectcard = function () {

        var cardId = str_geturlparam("cardId");
        var status = $(this).data("status");
        if (status == "col") {
            $.ajax({
                async: true,
                type: "post",
                url: "/bbscardcollect_con/uncollectcard",
                data: {
                    cardId: cardId
                },
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if (status == "uncol") {
                        $(".cardheader .header-left .modbtn .colbtn").text("收藏问题").data("status", status);
                    }
                },
                error: function (xhr, status) {
                    console.log(xhr);
                }
            });
            return;
        }
        if (status == "none") {
            layer.msg("请先登录");
            return;
        }
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardcollect_con/collectcard",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "col") {
                    $(".cardheader .header-left .modbtn .colbtn").text("已收藏").data("status", status);
                }
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    $(".cardheader .header-left .modbtn .colbtn").click(click_collectcard);


    /**
     * 当前用户是否编写了回答
     */
    var async_checkUserCommand = function () {

        var cardId = str_geturlparam("cardid");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/checkusercommand",
            data: {
                cardId: cardId
            },
            dataType: "json",
            success: function (data) {
                $(".cardheader .header-left .writebtn").data("status", data.status);
                if (status == "invalid") {
                    $("#writeAnswer").remove();
                }
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    async_checkUserCommand();


    /**
     * 点击回答
     */
    var click_openAnswerModal = function () {

        var status = $(this).data("status");
        if (status == "none") {
            layer.msg("请先登录");
            return false;
        }
        if (status == "ed") {
            layer.msg("您已回答了");
            return false;
        }
    };
    $(".cardheader .header-left .writebtn").click(click_openAnswerModal);


    /**
     * 提交回答
     */
    var click_submitAnswerModal = function () {

        if (!logstatus) {
            layer.msg("请先登录");
            return;
        }
        var answer = $("#writeAnswer .modal-body p.answer").text();
        var cardId = str_geturlparam("cardId");
        $.ajax({
            async: true,
            type: "post",
            url: "/bbscardanswer_con/addanswer",
            data: {
                "answer": answer,
                "cardId": cardId
            },
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "valid") {
                    layer.msg("发布成功", {icon: 6});
                    window.history.go(0);
                }
                else {
                    layer.msg("非法操作哦");
                    window.history.go(0);
                }
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    $("#writeAnswer .modal-footer .publish").click(click_submitAnswerModal);


    /**
     * 点赞与踩的实现
     */
    var click_starAndUnstar = function () {

        if (!logstatus) {
            layer.msg("请先登录");
            return;
        }
        var status = $(this).data("status");

        if (status == "invalid") {
            layer.msg("请先登录");
            return;
        }
        //以评论过
        if (status != "none") {
            return;
        }
        //进行评分
        var score = $(this).data("score");
        var aid = $(this).closest("div.cardmain").data("id");
        var $this = $(this);
        $.ajax({
            async: false,
            type: "post",
            url: "/bbscardanswerstar_con/adduserstar",
            data: {
                "aid": aid,
                "score": score
            },
            dataType: "json",
            success: function (data) {
                if (data.status == "valid") {
                    $this.addClass("stared");
                    $this.find("div.btndiv").find("a").data("status", "star");
                    var count = parseInt($this.find("span.goodcount").text()) + 1;
                    $this.find("span.goodcount").text(count);
                }
                return;
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    $(document).on("click", ".cardmain .cardfooter .btndiv .starlink", click_starAndUnstar);
    $(document).on("click", ".cardmain .cardfooter .btndiv .unstarlink", click_starAndUnstar);

});