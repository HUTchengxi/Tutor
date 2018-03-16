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
    }

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
                if (status === "nologin") {
                    window.location = "/forward_con/welcome";
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
                    $("nav ul.navbar-right").append("<li class='nav-time'><a>现在是：<i style='color: #00b6ff;'>" + now + "</i></a></li>" +
                        "                   <li><a style=\"color: black;\"><p>" + hello + "：<span class=\"span-cls-nick\" style=\"color: red;\">" + nickname + "</span></p></a></li>\n</li>");
                    //让时间一直轮播
                    var timeinter = window.setInterval(function () {
                        var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                        $("nav ul.navbar-right li.nav-time a i").text(now);
                    }, 1000);
                }
            },
            error: function (xhr, status) {
                alert("后台环境异常导致无法正确获得登录信息，请刷新页面重试");
            }
        });
    };
    login_check();

    /**
     * 获取发送通知的所有管理员
     */
    var async_getsuser = function () {
        $(".container .m-main .main-left .left-show").remove();
        $(".container .m-main .main-right .right-show").css("display", "none");
        $(".container .m-main .main-right .right-footer").css("display", "none");
        $.ajax({
            async: true,
            type: "post",
            url: "/usermessage_con/getmymessage",
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == "invalid") {
                    window.location = "/forward_con/welcome";
                }
                else if (status == "valid") {
                    return;
                }
                else {
                    var susered = [];
                    var count = 0;
                    $.each(data, function (index, item) {
                        var stime = item.stime;
                        var suser = item.suser;
                        var title = item.title;
                        var nocount = item.nocount;
                        var imgsrc = item.imgsrc;
                        if (count == 0) {
                            susered[count++] = item.suser;
                            $(".container .m-main .main-left").append("<div class=\"left-show\">\n" +
                                "                <img src=\"" + imgsrc + "\" />\n" +
                                "                <div class=\"show-info\">\n" +
                                "                    <h5>" + suser + "<span>(" + nocount + ")</span></h5>\n" +
                                "                    <p>" + title + "</p>\n" +
                                "                </div>\n" +
                                "            </div>");
                        }
                        else {
                            var flag = 0;
                            for (var i = 0; i < count; i++) {
                                if (susered[i] === item.suser) {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 0) {
                                susered[count++] = item.suser;
                                $(".container .m-main .main-left").append("<div class=\"left-show\">\n" +
                                    "                <img src=\"" + imgsrc + "\" />\n" +
                                    "                <div class=\"show-info\">\n" +
                                    "                    <h5>" + suser + "<span style='color: red'>(" + nocount + ")</span></h5>\n" +
                                    "                    <p>" + title + "</p>\n" +
                                    "                </div>\n" +
                                    "            </div>");
                            }
                        }
                    });
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取我的通知列表，请稍后重试");
                window.console.log(xhr);
            }
        });
    };
    async_getsuser();

    /**
     * 指定通知发送者的点击事件
     */
    var sarr = [];
    var count = 0;
    var cli_suser = function () {
        $("div.container .m-main .main-right .main-show ul li:nth-child(4)").css("display", "none");
        $(".container .m-main .main-right .right-footer .info-btn").removeClass("cli");
        $(".container .m-main .main-left .left-show").removeClass("cli");
        $(this).addClass("cli");
        var suser = $(this).find("h5").text();
        suser = suser.substring(0, suser.lastIndexOf("("));

        if (count == 0) {
            sarr[count++] = suser;
        }
        else {
            var flag = 0;
            for (var i = 0; i < count; i++) {
                if (sarr[i] == suser) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                sarr[count++] = suser;
            }
        }

        //异步获取指定通知管理员的所有通知数据
        $.ajax({
            async: true,
            type: "post",
            url: "/usermessage_con/getmessagebysuser",
            data: {suser: suser},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status === "invalid") {
                    window.location = "/forward_con/welcome";
                    return;
                }
                if (status === "valid") {
                    $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                    $("div.container .m-main .main-right footer").css("display", "block");
                    $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "none");
                    return;
                }
                $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "none");
                $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                $("div.container .m-main .main-right footer").css("display", "block");
                $.each(data, function (index, item) {
                    var id = item.id;
                    var imgsrc = item.imgsrc;
                    var descript = item.descript;
                    var stime = item.stime;
                    var status = item.status;
                    if (status == 1) {
                        $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left sta" + status + "\">\n" +
                            "                            <ul class=\"clearfix\">\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <a href=\"javascript:void(0)\">\n" +
                            "                                        <img src=\"" + imgsrc + "\" />\n" +
                            "                                    </a>\n" +
                            "                                </li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                            "                                </li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                            "                                </li>\n" +
                            "                                <li>\n" +
                            "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                            "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                            "                                    </a>\n" +
                            "                                </li>" +
                            "                            </ul>\n" +
                            "                        </div>");
                    }
                    else {
                        $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left unread sta" + status + "\">\n" +
                            "                            <ul class=\"clearfix\">\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <a href=\"javascript:void(0)\">\n" +
                            "                                        <img src=\"" + imgsrc + "\" />\n" +
                            "                                    </a>\n" +
                            "                                </li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                            "                                </li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                            "                                </li>\n" +
                            "                                <li>\n" +
                            "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                            "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                            "                                    </a>\n" +
                            "                                </li>" +
                            "                            </ul>\n" +
                            "                        </div>");
                    }
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取通知数据，请稍后重试");
                window.console.log(xhr);
            }
        });
    };
    $(document).on("click", ".container .m-main .main-left .left-show", cli_suser);

    /**
     * 只看已读ed/只看未读/un/删除操作de/删除选中mod
     */
    var cli_infobtn = function () {

        $("div.container .m-main .main-right .main-show ul li:nth-child(4)").css("display", "none");
        var status = $(this).data("status");
        var suser = $(".container .m-main .main-left .left-show.cli").find("h5").text();
        suser = suser.substring(0, suser.lastIndexOf("("));
        if (status != "de" && status != "mod") {
            $(".container .m-main .main-right .right-footer .info-btn").removeClass("cli");
            $(this).addClass("cli");
            $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "none");
            $.ajax({
                async: true,
                type: "post",
                url: "/usermessage_con/getmessagebystatus",
                data: {suser: suser, status: status},
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if (status === "invalid") {
                        window.location = "/forward_con/welcome";
                        return;
                    }
                    if (status === "valid") {
                        $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                        return;
                    }
                    $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                    $.each(data, function (index, item) {
                        var id = item.id;
                        var imgsrc = item.imgsrc;
                        var descript = item.descript;
                        var stime = item.stime;
                        var status = item.status;
                        if (status == 1) {
                            $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left sta" + status + "\">\n" +
                                "                            <ul class=\"clearfix\">\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <a href=\"javascript:void(0)\">\n" +
                                "                                        <img src=\"" + imgsrc + "\" />\n" +
                                "                                    </a>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                                "                                </li>\n" +
                                "                                <li>\n" +
                                "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                                "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                                "                                    </a>\n" +
                                "                                </li>" +
                                "                            </ul>\n" +
                                "                        </div>");
                        }
                        else {
                            $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left unread sta" + status + "\">\n" +
                                "                            <ul class=\"clearfix\">\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <a href=\"javascript:void(0)\">\n" +
                                "                                        <img src=\"" + imgsrc + "\" />\n" +
                                "                                    </a>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                                "                                </li> " +
                                "                                <li>\n" +
                                "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                                "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                                "                                    </a>\n" +
                                "                                </li>" +
                                "                            </ul>\n" +
                                "                        </div>");
                        }
                    });
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法获取通知数据，请稍后重试");
                    window.console.log(xhr);
                }
            });
        }
        else if(status == "de"){
            $("div.container .m-main .main-right .main-show ul li:nth-child(4)").css("display", "block");
            $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "inline-block");
            $(this).addClass("cli");
        }
    };
    $(".container .m-main .main-right .right-footer .info-btn").click(cli_infobtn);

    /**
     * 删除框选中事件
     */
    var cli_adddel = function(){

        if($(this).hasClass("cli")){
            $(this).removeClass("cli");
        }
        else{
            $(this).addClass("cli");
        }
    };
    $(document).on("click", "div.container .m-main .main-right .main-show ul li:nth-child(4) a", cli_adddel);

    /**
     * 点击删除选中按钮进行删除
     */
    var cli_delall = function(){

        $("div.container .m-main .main-right .main-show ul li:nth-child(4)").css("display", "block");
        if($("div.container .m-main .main-right .main-show ul li:nth-child(4) a").hasClass("cli") == false){
            window.alert("您未选中任何消息");
            return ;
        }
        if(window.confirm("确定删除所有选中的消息吗?")) {
            $("div.container .m-main .main-right .main-show ul li:nth-child(4) a.cli").each(function(){
                var did = $(this).data("did");
                $.ajax({
                    async: false,
                    type: "post",
                    url: "/usermessage_con/delmymessage",
                    data: {did: did},
                    dataType: "json",
                    success: function(data){
                        console.log(data);
                    },
                    error: function(xhr, status){
                        window.alert("后台环境异常导致无法删除选中消息，请稍后重试");
                        window.console.log(xhr);
                    }
                });
            });
            window.alert("删除成功");
            var suser = $(".container .m-main .main-left .left-show.cli").find("h5").text();
            suser = suser.substring(0, suser.lastIndexOf("("));

            //异步获取指定通知管理员的所有通知数据
            $.ajax({
                async: true,
                type: "post",
                url: "/usermessage_con/getmessagebysuser",
                data: {suser: suser},
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if (status === "invalid") {
                        window.location = "/forward_con/welcome";
                        return;
                    }
                    if (status === "valid") {
                        $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                        $("div.container .m-main .main-right footer").css("display", "block");
                        $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "none");
                        return;
                    }
                    $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").css("display", "none");
                    $("div.container .m-main .main-right .right-show").css("display", "block").empty();
                    $("div.container .m-main .main-right footer").css("display", "block");
                    $.each(data, function (index, item) {
                        var id = item.id;
                        var imgsrc = item.imgsrc;
                        var descript = item.descript;
                        var stime = item.stime;
                        var status = item.status;
                        if (status == 1) {
                            $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left sta" + status + "\">\n" +
                                "                            <ul class=\"clearfix\">\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <a href=\"javascript:void(0)\">\n" +
                                "                                        <img src=\"" + imgsrc + "\" />\n" +
                                "                                    </a>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                                "                                </li>\n" +
                                "                                <li>\n" +
                                "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                                "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                                "                                    </a>\n" +
                                "                                </li>" +
                                "                            </ul>\n" +
                                "                        </div>");
                        }
                        else {
                            $("div.container .m-main .main-right .right-show").append("<div class=\"main-show show-left unread sta" + status + "\">\n" +
                                "                            <ul class=\"clearfix\">\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <a href=\"javascript:void(0)\">\n" +
                                "                                        <img src=\"" + imgsrc + "\" />\n" +
                                "                                    </a>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <pre class=\"main-info\">" + descript + "</pre>\n" +
                                "                                </li>\n" +
                                "                                <li class=\"pull-left\">\n" +
                                "                                    <p class=\"main-time\">" + stime + "</p>\n" +
                                "                                </li>\n" +
                                "                                <li>\n" +
                                "                                    <a href=\"javascript:void(0)\" data-did='"+id+"'>\n" +
                                "                                        <span class=\"layui-icon\">&#xe610;</span>\n" +
                                "                                    </a>\n" +
                                "                                </li>" +
                                "                            </ul>\n" +
                                "                        </div>");
                        }
                    });
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法获取通知数据，请稍后重试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $("div.container .m-main .main-right footer input.info-btn:nth-child(4)").click(cli_delall);

    /**
     * 全部标记为已读按钮事件
     */
    var cli_alled = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/usermessage_con/setallstatus",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else{
                    window.alert("设置成功");
                    async_getsuser();
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法设置我的消息状态，请稍后重试");
                window.console.log(xhr);
            }
        });
    };
    $(".container header ul li:nth-child(2) a").click(cli_alled);

    /**
     * 页面离开时将看的所有私信设置为已读
     */
    $(window).bind('beforeunload', function (event) {

        for (var i = 0; i < count; i++) {
            var suser = sarr[i];
            //指定用户/指定管理员的对应的未读通知全部设置为已读
            $.ajax({
                async: true,
                type: "post",
                url: "/usermessage_con/setmessagestatus",
                data: {suser: suser},
                dataType: "json",
                success: function (data) {
                    console.log(data);
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法设置通知状态，请稍后重试");
                    window, console.log(xhr);
                }
            });
        }
    });


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
