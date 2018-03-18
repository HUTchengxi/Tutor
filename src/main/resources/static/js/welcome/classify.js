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





    //--------------------------顶部类别点击事件的实现----------------------------------
    /**
     * 默认异步加载小学家教/不限类别/最新发布
     */
    var glo_stype = "";
    var glo_ctype = "";
    var glo_status = "";
    var glo_keyword = "";
    var glo_sort = "";
    var async_defaultcm = function () {

        $(".listshow ul.main").empty();
        var stype = 1;
        var ctype = "all";
        var status = 0;
        var keyword = "";
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: "new", startpos: 0, status: status, keyword: keyword},
            dataType: "json",
            success: function (data) {
                $("div.pageshow").css("display", "none");
                var status = data.status;
                if (status === "valid") {
                    $("nav.sortshow").css("display", "none");
                    $("div.listshow ul.main").css("display", "none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
                $.each(data, function (index, item) {
                    var imgsrc = item.imgsrc;
                    var id = item.id;
                    var descript = item.descript;
                    var jcount = item.jcount;
                    var name = item.name;
                    var price = item.price;
                    var uimgsrc = item.uimgsrc;
                    var username = item.nickname;

                    var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                    var $div = $("<div class='course'></div>");
                    var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                    var $bodydiv = $("<div class='coursebody'></div>");
                    var $ptitle = $("<p class='title'>" + name + "</p>");
                    var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                    var $desp = $("<p class='descript'>" + descript + "</p>");
                    var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                    var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                    var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                    $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))))
                });

                //获取课程分页数据
                get_coursecount(stype, ctype, status, keyword);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_defaultcm();

    /**
     * 默认异步小学所有学科类别
     */
    var async_defaultct = function (stype) {

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursetype",
            data: {stype: stype},
            dataType: "json",
            success: function (data) {
                $(".mainshow header ul").empty().append("<li class='pull-left cli'><a href='javascript:void(0);' data-ctype='all'>不限</a>");
                var status = data.status;
                if (status === "valid") {
                    $(".mainshow header").css("display", "none");
                    $("nav.sortshow").css("display", "none");
                    $("div.listshow ul.main").css("display", "none");
                    $("div.pageshow").css("display", "none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
                $(".mainshow header ul").css("display", "block");
                $.each(data, function (index, item) {
                    $(".mainshow header ul").append("<li class='pull-left'><a href='javascript:void(0);' data-ctype='" + item + "'>" + item + "</a>")
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取学科列表，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_defaultct(1);

    /**
     * 主类别按钮点击事件：小学/初中/高中/其他兴趣
     */
    var coursecli_stype = function () {

        $("header .searchbox input.btn").data("status", "0");

        if($(this).closest("li").hasClass("cli")){
            return ;
        }
        var stype = $(this).data("stype");
        $(".searchmain ul li.cli").removeClass("cli");
        $(this).closest("li").addClass("cli");
        $(".mainshow header ul").empty();

        //异步获取所有课程类别
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursetype",
            data: {stype: stype},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status === "valid") {
                    return;
                }
                $(".sortshow ul").css("display","block");
                $(".mainshow header ul").append("<li class='pull-left cli'><a href='javascript:void(0);' data-ctype='all'>不限</a>");
                $.each(data, function (index, item) {
                    $(".mainshow header ul").append("<li class='pull-left'><a href='javascript:void(0);' data-ctype='" + item + "'>" + item + "</a></li>");
                });
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取学科列表，请刷新页面重试");
                window.console.log(xhr);
            }
        });

        //异步获取所有课程数据
        $(".listshow ul.main").empty();
        var ctype = "all";
        var status = 0;
        var keyword = "";

        var sort = $(".sortshow ul li.cli a").data("sort");
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: sort, startpos: 0, status: status, keyword: keyword},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var stat = data.status;
                if (stat === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty().css("display", "none");
                    $("div.pageshow").css("display", "none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
                $("div.mainshow ul").css("display","block");
                $("nav.sortshow").css("display","block");
                var count = 0;
                $.each(data, function (index, item) {
                    count++;
                    var imgsrc = item.imgsrc;
                    var id = item.id;
                    var descript = item.descript;
                    var jcount = item.jcount;
                    var name = item.name;
                    var price = item.price;
                    var uimgsrc = item.uimgsrc;
                    var username = item.nickname;

                    var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                    var $div = $("<div class='course'></div>");
                    var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                    var $bodydiv = $("<div class='coursebody'></div>");
                    var $ptitle = $("<p class='title'>" + name + "</p>");
                    var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                    var $desp = $("<p class='descript'>" + descript + "</p>");
                    var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                    var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                    var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                    $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))));
                });
                get_coursecount(stype, ctype, status, keyword);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    $("div.searchmain ul li a").click(coursecli_stype);

    /**
     * 科目类别按钮点击事件：语文/数学等等
     */
    var coursecli_ctype = function(){

        if($(this).closest("li").hasClass("cli")){
            return ;
        }
        $(".mainshow header ul li.cli").removeClass("cli");
        $(this).closest("li").addClass("cli");
        var ctype = $(this).data("ctype");
        var stype = $(".searchmain ul li.cli a").data("stype");
        var sort = $("nav.sortshow ul li.cli a").data("sort");
        if(stype == undefined){
            stype = -1;
        }

        var status = $("header .searchbox input.btn").data("status");
        var keyword = $(".searchbox input:nth-child(1)").val();

        //异步获取指定科目类别的课程数据
        $(".listshow ul.main").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: sort, startpos: 0, status: status, keyword: keyword},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var stat = data.status;
                if (stat === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul").empty().css("display","none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
                $("div.mainshow ul").css("display","block");
                $("nav.sortshow").css("display","block");
                var count = 0;
                $.each(data, function (index, item) {
                    count++;
                    var imgsrc = item.imgsrc;
                    var id = item.id;
                    var descript = item.descript;
                    var jcount = item.jcount;
                    var name = item.name;
                    var price = item.price;
                    var uimgsrc = item.uimgsrc;
                    var username = item.nickname;

                    var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                    var $div = $("<div class='course'></div>");
                    var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                    var $bodydiv = $("<div class='coursebody'></div>");
                    var $ptitle = $("<p class='title'>" + name + "</p>");
                    var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                    var $desp = $("<p class='descript'>" + descript + "</p>");
                    var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                    var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                    var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                    $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))))
                });
                get_coursecount(stype, ctype, status, keyword);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    $(document).on("click",".mainshow header ul li a",coursecli_ctype);

    /**
     * 排序方式按钮点击事件：new/hot/more
     */
    var coursecli_sort = function(){

        if($(this).closest("li").hasClass("cli")){
            return ;
        }
        $(".sortshow ul li.cli").removeClass("cli");
        $(this).closest("li").addClass("cli");
        var sort = $(this).data("sort");
        var stype = $(".searchmain ul li.cli a").data("stype");
        var ctype = $(".mainshow header ul li.cli a").data("ctype");

        if(stype == undefined){
            stype = -1;
        }

        var status = $("header .searchbox input.btn").data("status");
        var keyword = $(".searchbox input:nth-child(1)").val();

        //异步获取指定科目类别的课程数据
        $(".listshow ul.main").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: sort, startpos: 0, status: status, keyword: keyword},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var stat = data.status;
                if (stat === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul.main").empty().css("display","none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
                $("div.mainshow ul").css("display","block");
                $("nav.sortshow").css("display","block");
                var count = 0;
                $.each(data, function (index, item) {
                    count++;
                    var imgsrc = item.imgsrc;
                    var id = item.id;
                    var descript = item.descript;
                    var jcount = item.jcount;
                    var name = item.name;
                    var price = item.price;
                    var uimgsrc = item.uimgsrc;
                    var username = item.nickname;

                    var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                    var $div = $("<div class='course'></div>");
                    var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                    var $bodydiv = $("<div class='coursebody'></div>");
                    var $ptitle = $("<p class='title'>" + name + "</p>");
                    var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                    var $desp = $("<p class='descript'>" + descript + "</p>");
                    var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                    var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                    var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                    $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))));
                });

                get_coursecount(stype, ctype, status, keyword);
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    $(document).on("click","nav.sortshow ul li a", coursecli_sort);

    /**
     * 搜索功能的实现
     */
    var coursesearch_keyup = function(){

        var keyword = $(this).val().trim();
        $("header .searchshow ul").empty();
        $("header .searchshow").css("height", 0).css("display","none");
        if(keyword === ""){
            $(this).closest("div").find("i.del").css("visibility","hidden");
            return ;
        }
        $(this).closest("div").find("i.del").css("visibility","visible");
        // $("header .searchshow").css("display","block").animate({
        //     height: $("header .searchshow ul").css("height")
        // },200);
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/coursesearch",
            data: {keyword: keyword},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "valid"){
                    $("header .searchshow ul").append("<li class='none'>抱歉，没有找到相关课程</li>");
                }
                else{
                    var count = 0;
                    $.each(data, function(index, item){
                        count++;
                        if(count <= 5){
                            var name = item.name;
                            var descript = item.descript;
                            var id = item.id;
                            $("header .searchshow ul").append("<li data-id=" + id + " title=" + descript + ">" + name + "</li>");
                        }
                    });
                    if(count > 5){
                        $("header .searchshow ul").append("<li class='smore' data-id='all'>查看全部<span>"+count+"</span>个课程</li>");
                    }
                }
                $("header .searchshow").css("display","block").animate({
                    height: $("header .searchshow ul").css("height")
                },200);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致搜索功能无法使用，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    $(".searchbox input").keyup(coursesearch_keyup);

    /**
     * 搜索输入框失去焦点时关闭提示框
     */
    var coursesearch_blur = function(){
        $("header .searchshow").css("display","none");
        $(this).closest("div").find("i.del").delay(10).css("visibility","hidden");
    };
    $(".searchbox input").blur(coursesearch_blur);

    /**
     * 搜索输入框获得焦点事件：模拟智能判断；ul长度超过两个li或者长度不为30px则显示
     */
    var coursesearch_focus = function(){

        var keyword = $(this).val().trim();
        if(!(keyword == "")){
            $(this).closest("div").find("i.del").css("visibility","visible");
        }
        $("header .searchshow").css("display","block").css("visibility","hidden");
        var height = parseInt($("header .searchshow ul").css("height"));
        if((height >0 && height <= 30) || height >= 60){
            $("header .searchshow").css("display","block").css("visibility","visible").animate({
                height: $("header .searchshow ul").css("height")
            },200);
        }
        else{
            $("header .searchshow").css("display","none").css("visibility","visible");
        }
    };
    $(".searchbox input").focus(coursesearch_focus);

    /**
     * 点击清空搜索框的值的事件
     */
    var coursesearch_clear = function(){

        $(".searchbox input:nth-child(1)").val("");
        $("header .searchshow ul").empty();
        $("header .searchshow").css("display","none").css("height","0");
    };
    $(".searchbox i.del").mousedown(coursesearch_clear);

    /**
     * 点击指定搜索到的结果课程界面li
     */
    var coursesearch_go = function(){

        var id = $(this).data("id");
        //查看搜索到的全部课程
        if(id == "all"){
            $(".searchbox input:nth-child(2)").trigger("click");
            return ;
        }
        var keyword = $(this).text();
        $(".searchbox input:nth-child(1)").val(keyword);
        $(this).closest("ul").empty();
        $(".mainshow .listshow ul.main").empty();
        $(".pageshow").css("display","none");
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursebyid",
            data: {id: id},
            dataType: "json",
            success: function(data){
                $("header .searchbox input.btn").data("status", "1");
                var stype = data.stype;
                $(".cli").removeClass("cli");
                $(".searchmain ul li a").each(function(){
                    if($(this).data("stype") == stype){
                        $(this).closest("li").addClass("cli");
                    }
                });
                var ctype = data.ctype;
                $(".mainshow header ul li a").each(function(){
                    if($(this).text() == ctype){
                        $(this).closest("li").addClass("cli");
                    }
                });
                $(".sortshow ul li:nth-child(1)").addClass("cli");

                var imgsrc = data.imgsrc;
                var id = data.id;
                var descript = data.descript;
                var jcount = data.jcount;
                var name = data.name;
                var price = data.price;
                var uimgsrc = data.uimgsrc;
                var username = data.nickname;

                var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                var $div = $("<div class='course'></div>");
                var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                var $bodydiv = $("<div class='coursebody'></div>");
                var $ptitle = $("<p class='title'>" + name + "</p>");
                var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                var $desp = $("<p class='descript'>" + descript + "</p>");
                var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))))
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取搜索的课程数据，请稍后重试");
                window.console.log(xhr);
            }
        });
    };
    $(document).on("mousedown","header .searchshow ul li" , coursesearch_go);

    /**
     * 点击搜索按钮搜索查看全部课程结果
     */
    var coursesearch_goall = function(){

        var keyword = $(".searchbox input:nth-child(1)").val();
        $("header .searchshow").css("display","none");
        $(this).closest("div").find("i.del").delay(10).css("visibility","hidden");
        $(".mainshow ul.main").empty();

        async_defaultct();

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/coursesearch",
            data: {keyword: keyword},
            dataType: "json",
            success: function(data){
                $("header .searchbox input.btn").data("status", "1");
                var status = data.status;
                if(status === "valid"){
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul.main").empty().css("display","none");
                    $("div.listshow div.none").remove();
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                }
                else{
                    $(".searchmain ul li.cli").removeClass("cli");
                    $(".mainshow header ul li.cli").removeClass("cli");
                    $(".mainshow header ul li:nth-child(1)").addClass("cli");
                    $("div.mainshow ul").css("display","block");
                    $("nav.sortshow").css("display","block");
                    var count = 0;
                    $.each(data, function (index, item) {
                        count++;
                        var imgsrc = item.imgsrc;
                        var id = item.id;
                        var descript = item.descript;
                        var jcount = item.jcount;
                        var name = item.name;
                        var price = item.price;
                        var uimgsrc = item.uimgsrc;
                        var username = item.nickname;

                        var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                        var $div = $("<div class='course'></div>");
                        var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                        var $bodydiv = $("<div class='coursebody'></div>");
                        var $ptitle = $("<p class='title'>" + name + "</p>");
                        var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                        var $desp = $("<p class='descript'>" + descript + "</p>");
                        var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                        var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                        var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                        $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))));
                    });

                    var stype = "-1";
                    var ctype = "all";
                    var status = 1;
                    get_coursecount(stype, ctype, status, keyword);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致搜索功能无法使用，请刷新页面重试");
                window.console.log(xhr);
            }
        });
        return false;
    };
    $(".searchbox input:nth-child(2)").click(coursesearch_goall);


    /**
     * 进入指定课程事件
     */
    var course_cli = function(){

        var id = $(this).data("id");
        window.location = "/forward_con/showcourse?id="+id+"";
        $("header .searchbox input.btn").data("status", "0");
    };
    $(document).on("click", ".listshow .main li", course_cli);

    /**
     * 获取当前选择的数据的总条数
     */
    var get_coursecount = function(stype, ctype, status, keyword){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursecount",
            data: {stype: stype, ctype: ctype, status: status, keyword: keyword},
            dataType: "json",
            success: function(data){
                glo_status = status;
                glo_ctype = ctype;
                glo_keyword = keyword;
                glo_stype = stype;
                glo_sort = $(".sortshow ul li.cli a").data("sort");
                $(".pageshow .err-info").css("display", "none");
                var total = data.total;
                var count = 8;
                if(total/count <= 1){
                    $(".pageshow").css("display", "none");
                    return ;
                }
                else{
                    $(".pageshow").css("display", "-webkit-box");
                    //进行分页按钮的规整
                    var page = Math.ceil(total/count);
                    if(page <= 3){
                        $(".pgmoren").css("display", "none");
                        $(".pgend").css("display", "none");
                        $(".layui-box span:nth-child(1)").text("共"+total+"条");
                        $(".layui-box.layui-laypage a").data("current", "1");
                        $(".layui-box.layui-laypage a.layui-laypage-prev").addClass("layui-disabled");
                        $(".pageshow a.btn-cli").removeClass("btn-cli");
                        $(".layui-box.layui-laypage a.pgone").addClass("btn-cli");
                        $(".layui-box.layui-laypage a.layui-laypage-next").data("page", page);
                        if(page == 2){
                            $(".pgthree").css("display", "none");
                        }
                    }
                    else{
                        if(page > 4) {
                            $(".pgmoren").css("display", "inline-block");
                        }
                        else{
                            $(".pgmoren").css("display", "none");
                        }
                        $(".pgend").css("display", "inline-block").text(page).data("page", page);
                        $(".layui-box span:nth-child(1)").text("共"+total+"条");
                        $(".layui-box.layui-laypage a").data("current", "1");
                        $(".layui-box.layui-laypage a.layui-laypage-prev").addClass("layui-disabled");
                        $(".layui-box.layui-laypage a.pgone").addClass("btn-cli");
                        $(".layui-box.layui-laypage a.layui-laypage-next").data("page", page);
                    }
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法进行课程分页，请稍后重试");
                window.console.log(xhr);
                $(".pageshow").empty();
            }
        })
    };

    /**
     * 点击查看上一页事件
     */
    var pageshow_prev = function(){

        if($(this).hasClass("layui-disabled")){
            return ;
        }
        else{
            $(".pageshow .err-info").css("display", "none");
            $(".layui-laypage-next").removeClass("layui-disabled");
            var current = $(this).data("current");
            var count = 8;
            var startpos = count * (current-2);
            getcourse_byparam(startpos);
            var page = $(".pgend").data("page");
            if(page == undefined){
                page = $(".pgthree").data("page");
                if(page == undefined){
                    page = $(".pgtwo").data("page");
                }
            }
            $(this).data("current", --current);
            $(".layui-laypage-next").data("current", current);
            if(1 == current){
                $(this).addClass("layui-disabled");
            }

            $(".pageshow a.btn-cli").removeClass("btn-cli");
            if(current+1 == page){
                if(page > 3) {
                    $(".pgthree").addClass("btn-cli");
                }
                else{
                    $(".pgtwo").addClass("btn-cli");
                }
            }
            else{
                $(".pgmoren").css("display", "inline-block");
                if(current > 3){
                    $(".pgmorep").css("display", "inline-block");
                    $(".pgone").text(""+(current-1)).data("page", ""+(current-2));
                    $(".pgtwo").text(""+(current)).data("page", ""+(current-1)).addClass("btn-cli");
                    $(".pgthree").text(""+(current+1)).data("page", ""+(current));
                }
                else{
                    $(".pgmorep").css("display", "none");
                    $(".pgone").text("1").data("page", "1");
                    $(".pgtwo").text("2").data("page", "2");
                    $(".pgthree").text("3").data("page", "3");
                    $(".pageshow a:nth-child("+(current+3)+")").addClass("btn-cli");
                }

            }
        }
    };
    $(".pageshow .layui-laypage-prev").click(pageshow_prev);

    /**
     * 点击查看下一页事件
     */
    var pageshow_next = function(){

        if($(this).hasClass("layui-disabled")){
            return ;
        }
        else{
            $(".pageshow .err-info").css("display", "none");
            $(".layui-laypage-prev").removeClass("layui-disabled");
            var current = $(this).data("current");
            var count = 8;
            var startpos = count * current;
            getcourse_byparam(startpos);
            var page = $(this).data("page");
            $(this).data("current", ++current);
            $(".layui-laypage-prev").data("current", current);
            if(page == current){
                $(this).addClass("layui-disabled");
            }

            $(".pageshow a.btn-cli").removeClass("btn-cli");
            if(current <= 3 || page == 4){
                $(".pageshow .pgmorep").css("display", "none");
                $(".pageshow a:nth-child("+(current+3)+")").addClass("btn-cli");
            }
            else{
                if(page == current){
                    $(".pgend").addClass("btn-cli");
                    $(".pgmoren").css("display", "none");
                }
                else{
                    $(".pgmorep").css("display", "inline-block");
                    $(".pgthree").addClass("btn-cli").text(""+(current)).data("page", ""+(current));
                    $(".pgtwo").text(""+(current-1)).data("page", ""+(current-1));
                    $(".pgone").text(""+(current-2)).data("page", ""+(current-2));
                    if(page == current+1){
                        $(".pgmoren").css("display", "none");
                    }
                    else{
                        $(".pgmoren").css("display", "inline-block");
                    }
                }
            }
        }
    };
    $(".pageshow .layui-laypage-next").click(pageshow_next);

    /**
     * 点击进入指定页码
     */
    var pageshow_clilink = function(){

        if($(this).hasClass("btn-cli")){
            return ;
        }

        $(".pageshow .err-info").css("display", "none");
        $(".pageshow .pglink.btn-cli").removeClass("btn-cli");
        $(this).addClass("btn-cli");

        var page = $(this).data("page");
        var count = 8;
        var startpos = count * (page-1);
        getcourse_byparam(startpos);
        $(".layui-laypage-prev").data("current", page);
        $(".layui-laypage-next").data("current", page);
        var total = "";
        if($(".pgend").css("display") == "none"){
            if($(".pgthree").css("display") == "none"){
                total = $(".pgtwo").data("page");
            }
            else{
                total = $(".pgthree").data("page");
            }
        }
        else{
            total = $(".pgend").data("page");
        }
        if(page == 1){
            $(".layui-laypage-prev").addClass("layui-disabled");
            $(".layui-laypage-next").removeClass("layui-disabled");
        }
        else{
            $(".layui-laypage-prev").removeClass("layui-disabled");
            if(page == total){
                $(".layui-laypage-next").addClass("layui-disabled");
            }
            else{
                $(".layui-laypage-next").removeClass("layui-disabled");
            }
        }
    };
    $(".pageshow .pglink").click(pageshow_clilink);

    /**
     * 点击跳转到指定页面
     */
    var pageshow_gopage = function(){

        var page = $(this).closest("span").find("input").val();
        if(page.trim() == ""){
            $(".pageshow .err-info").text("请输入页码").css("display", "inline-block");
        }
        else{
            //获取总页数
            var pages = 0;
            if($(".pageshow .pgend").css("display") == "none"){
                if($(".pageshow .pgthree").css("display") == "none"){
                    pages = $(".pageshow .pgtwo").data("page");
                }
                else{
                    pages = $(".pageshow .pgthree").data("page");
                }
            }
            else{
                pages = $(".pageshow .pgend").data("page");
            }

            page = parseInt(page);
            pages = parseInt(pages);

            if(page <= 0 || page > pages){
                $(".pageshow .err-info").text("页码不存在").css("display", "inline-block");
            }

            else{
                $(".pageshow .err-info").text("").css("display", "none");
                var count = 8;
                var startpos = count * (page - 1);
                getcourse_byparam(startpos);
                $(".pageshow span input").val("");
                $(".layui-laypage-prev").data("current", page);
                $(".layui-laypage-next").data("current", page);

                //解决上下翻页
                if(page == 1){
                    $(".layui-laypage-prev").addClass("layui-disabled");
                    $(".layui-laypage-next").removeClass("layui-disabled");
                }
                else{
                    $(".layui-laypage-prev").removeClass("layui-disabled");
                    if(page == pages){
                        $(".layui-laypage-next").addClass("layui-disabled");
                    }
                    else{
                        $(".layui-laypage-next").removeClass("layui-disabled");
                    }
                }

                //解决两边的省略号的显示 pgmoren/pgmorep
                if(pages - page <= 1){
                    $(".pageshow .pgmoren").css("display", "none");
                }
                else{
                    $(".pageshow .pgmoren").css("display", "inline-block");
                }
                if(page <= 3){
                    $(".pageshow .pgmorep").css("display", "none");
                }
                else{
                    $(".pageshow .pgmorep").css("display", "inline-block");
                }

                //解决当前页码的标识
                $(".pageshow a.btn-cli").removeClass("btn-cli");
                if(page <= 3){
                    $(".pgone").text("1").data("page", "1");
                    $(".pgtwo").text("2").data("page", "2");
                    $(".pgthree").text("3").data("page", "3");
                    $(".pageshow a:nth-child("+(page+3)+")").addClass("btn-cli");
                }
                else{
                    $(".pgone").text(""+(page-2)).data("page", ""+(page-2));
                    $(".pgtwo").text(""+(page-1)).data("page", ""+(page-1));
                    $(".pgthree").text(""+(page)).data("page", ""+page).addClass("btn-cli");
                }
            }
        }
    };
    $(".pageshow span button").click(pageshow_gopage);

    /**
     * 根据指定参数获取课程数据列表
     */
    var getcourse_byparam = function(startpos){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: glo_stype, ctype: glo_ctype, sort: glo_sort, startpos: startpos, status: glo_status, keyword: glo_keyword},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                $("div.listshow ul.main").empty();
                $("div.mainshow ul").css("display","block");
                $("nav.sortshow").css("display","block");
                $.each(data, function (index, item) {
                    var imgsrc = item.imgsrc;
                    var id = item.id;
                    var descript = item.descript;
                    var jcount = item.jcount;
                    var name = item.name;
                    var price = item.price;
                    var uimgsrc = item.uimgsrc;
                    var username = item.nickname;

                    var $li = $("<li class='pull-left' data-id='"+id+"'></li>");
                    var $div = $("<div class='course'></div>");
                    var $alink = $("<a href='javascript:void(0);'><img src='" + imgsrc + "' /></a>");
                    var $bodydiv = $("<div class='coursebody'></div>");
                    var $ptitle = $("<p class='title'>" + name + "</p>");
                    var $ptype = $("<p class='type'>一对多&nbsp;&nbsp;&nbsp;&nbsp;<i class=\"layui-icon\">&#xe612;" + jcount + "</i></p>");
                    var $desp = $("<p class='descript'>" + descript + "</p>");
                    var $ul = $("<ul class='clearfix price'><li class='pull-left'><a href='javascript:void(0);'>￥" + price + "</a></li></ul>");
                    var $authordiv = $("<div class='author'><span>讲师：</span><a href='javascript:void(0);'><img src='" + uimgsrc + "' /></a><a href='javascript:void(0);'>" + username + "</a></div>");
                    var $footer = $("<footer class='listmore'><a href='javascript:void(0);'>查看详情<i class='layui-icon'>&#xe65b;</i></a>")

                    $(".listshow ul.main").append($li.append($div.append($alink).append($bodydiv.append($ptitle).append($ptype).append($desp).append($ul).append($authordiv).append($footer))));
                });

            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取课程数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };








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