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
    var async_getcomcount = function () {

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
    var async_getanscount = function () {

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
     * 异步获取背景图片的imgsrc
     */
    var async_getImgsrc = function () {
        $.ajax({
            async: true,
            type: "post",
            url: "/commonimgsrc_con/getAll.json",
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
        if (scrollTop >= 225) {
            $(".personal").css("position", "fixed").css("top", "76px");
        }
        else {
            $(".personal").css("position", "absolute").css("top", "300px");
        }
    };
    $(window).scroll(window_scroll);
    $(window).trigger("scroll");


    /**
     * 校验字段
     */
    var str_isnull = function (descript) {
        if (descript.trim() == "" || descript.trim() == "在这里写你的问题...") {
            return true;
        }
        if (descript.indexOf("在这里写你的问题...") == 0) {
            return true;
        }
        return false;
    };


    //------------------------------发表讨论-----------------------------------------

    /**
     * 外部点击发表讨论的一级按钮
     * @returns {boolean}
     */
    var submit_writeDescript = function () {

        var descript = $(this).closest("form").find("p.descript").text();
        //没有输入问题
        if (str_isnull(descript)) {
            $(this).closest("form").find(".errinfo").css("display", "block").find("p").text("请输入你的问题");
            return false;
        }
        //没有登录
        else if (!logstatus) {
            $(this).closest("form").find(".errinfo").css("display", "block").find("p").text("请先登录哦");
            return false;
        }
        $(this).closest("form").find(".errinfo").css("display", "none");
    };
    $(".pubdiscuss form a.btn-primary").click(submit_writeDescript);


    /**
     * modal点击发表讨论触发事件
     */
    var submit_sendDescript = function () {

        var title = $(this).closest(".modal").find(".modal-body input.title").val();
        var imgsrc = $(this).closest(".modal").find(".modal-body select").val();
        var descript = $(".pubdiscuss p.descript").text();

        if (title.trim() == "") {
            $(this).closest(".modal").find(".modal-body p.terr").css("display", "block").text("请输入标题");
        }
        else {
            $(this).closest(".modal").find(".modal-body p.terr").css("display", "none");
            $.ajax({
                async: true,
                type: "post",
                url: "/bbscard_con/publishCard.json",
                data: {
                    title: title,
                    imgsrc: imgsrc,
                    descript: descript
                },
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if (status == "texist") {
                        $("#pubModal .modal-body p.terr").css("display", "block").text("标题已被使用");
                    }
                    else if (status == "valid") {
                        layer.msg("发表成功", {icon: 6});
                        $(".pubdiscuss p.descript").text("在这里写你的问题...");
                        $("#pubModal .modal-body input").val("");
                        async_getImgsrc();
                        $("#pubModal .modal-footer button:nth-child(2)").trigger("click");
                        async_getpubcount();
                    }
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法发表讨论，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $("#pubModal .modal-footer button:nth-child(1)").click(submit_sendDescript);


    /**
     * 点击进行更改背景图片
     */
    var modal_changeimgsrc = function () {

        var imgsrc = $(this).val();
        $("#pubModal .modal-body img").attr("src", imgsrc);
    };
    $("#pubModal .modal-body select").change(modal_changeimgsrc);


    //--------------------------搜索问题-----------------------------------
    /**
     * 点击搜索问题
     */
    var submit_searchcard = function () {

        var keyword = $(this).find("p").text();
        console.log(keyword);
        if (str_isnull(keyword)) {
            keyword = "";
        }
        window.location = "/forward_con/showcardsearch?keyword=" + keyword;
    };
    $(".finddiscuss form").submit(submit_searchcard);


    /**
     * 获取热门帖子数据
     */
    function async_loadHotCard() {

        $.ajax({
            async: true,
            type: "post",
            url: "/bbscard_con/loadhotcard.json",
            dataType: "json",
            success: function (data) {
                var count = data.count;
                if (count == 0) {
                    $(".hotblog ul").remove();
                    $(".hotblog div").remove();
                    $(".hotblog").append("<p style='color: grey' class='text-center'>没有找到对应的结果</p>");
                    return;
                }
                $.each(data.list, function (index, item) {
                    var title = item.title;
                    var id = item.id;
                    var bimgsrc = item.bimgsrc;
                    var crttime = item.crttime;
                    var descript = item.descript;
                    var imgsrc = item.imgsrc;
                    var nickname = item.nickname;
                    $("#blog ul").append("<li>\n" +
                        "            <div class=\"blog-left\">\n" +
                        "                <p ><a href=\"javascript:;\" data-id='" + id + "' class=\"showlink\">" + title + "</a></p>\n" +
                        "                <p style=\"margin-top: 20px\">" + descript + "</p>\n" +
                        "                <p style=\"margin-top: 80px\"><img src=\"" + imgsrc + "\" style=\"width: 25px;height: 25px;border-radius: 50%;\" />" + nickname + "<img src=\"http://img.php.cn/upload/course/000/000/004/58170fbda3f34844.png\" style=\"margin-left: 20px\"/>" + crttime + "</p>\n" +
                        "            </div>\n" +
                        "            <div class=\"blog-right\"><img src=\"" + bimgsrc + "\"/></div>\n" +
                        "        </li>");
                });
            },
            error: function (xhr, status) {
                console.log(xhr);
            }
        });
    };
    async_loadHotCard();


    /**
     * 点击进入对应的帖子详情
     */
    function click_showcarddetail() {

        var cardId = $(this).data("id");
        window.location = "/forward_con/showcarddetail?cardId=" + cardId;
    };
    $(document).on("click", ".showlink", click_showcarddetail);

});