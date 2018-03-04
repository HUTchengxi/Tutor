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
                    $("nav ul.navbar-right").append("<li class='nav-time'><a>现在是：<i style='color: #00b6ff;'>" + now + "</i></a></li>" +
                        "                   <li><a style=\"color: black;\"><p>" + hello + "：<span class=\"span-cls-nick\" style=\"color: red;\">" + nickname + "</span></p></a></li>\n" +
                        "                    <li class=\"dropdown\">\n" +
                        "                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">个人中心<i class=\"caret\"></i></a>\n" +
                        "                        <ul class=\"dropdown-menu\">\n" +
                        "                            <li><a>基本信息</a></li>\n" +
                        "                            <li><a>我的订单</a></li>\n" +
                        "                            <li><a>私信</a></li>\n" +
                        "                            <li><a>设置</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n" +
                        "                        </ul>\n" +
                        "                    </li>");
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





    //--------------------------顶部类别点击事件的实现----------------------------------
    /**
     * 默认异步加载小学家教/不限类别/最新发布
     */
    var async_defaultcm = function () {

        $(".listshow ul.main").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: 1, ctype: "all", sort: "new", startpos: 0},
            dataType: "json",
            success: function (data) {
                $("div.pageshow").css("display", "none");
                var status = data.status;
                if (status === "valid") {
                    $("nav.sortshow").css("display", "none");
                    $("div.listshow ul.main").css("display", "none");
                    $("div.listshow").append("<div class='none'><p>课程制作中，敬请期待！</p></div>");
                    return;
                }
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
                if (count < 3) {
                    $(".pageshow").css("display", "none");
                }
                else {
                    $(".pageshow").css("display", "-webkit-box");
                }
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
    var async_defaultct = function () {

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcoursetype",
            data: {stype: 1},
            dataType: "json",
            success: function (data) {
                $(".mainshow header ul").empty().append("<li class='pull-left cli'><a href='javascript:void(0);' data-ctype='all'>不限</a>");
                var status = data.status;
                if (status === "valid") {
                    $(".mainshow header").css("display", "none");
                    $("nav.sortshow").css("display", "none");
                    $("div.listshow ul.main").css("display", "none");
                    $("div.pageshow").css("display", "none");
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
    async_defaultct();

    /**
     * 主类别按钮点击事件：小学/初中/高中/其他兴趣
     */
    var coursecli_stype = function () {

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
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: "all", sort: "new", startpos: 0},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var status = data.status;
                if (status === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty().css("display", "none");
                    $("div.pageshow").css("display", "none");
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
                if (count < 3) {
                    $(".pageshow").css("display", "none");
                }
                else {
                    $(".pageshow").css("display", "-webkit-box");
                }
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
        //异步获取指定科目类别的课程数据
        $(".listshow ul.main").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: sort, startpos: 0},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var status = data.status;
                if (status === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul").empty().css("display","none");
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
                if (count < 3) {
                    $(".pageshow").css("display", "none");
                }
                else {
                    $(".pageshow").css("display", "-webkit-box");
                }
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
        //异步获取指定科目类别的课程数据
        $(".listshow ul.main").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getcourselist",
            data: {stype: stype, ctype: ctype, sort: sort, startpos: 0},
            dataType: "json",
            success: function (data) {
                $("div.listshow div.none").remove();
                var status = data.status;
                if (status === "valid") {
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul.main").empty().css("display","none");
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
                if (count < 3) {
                    $(".pageshow").css("display", "none");
                }
                else {
                    $(".pageshow").css("display", "-webkit-box");
                }
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
        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/coursesearch",
            data: {keyword: keyword},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "valid"){
                    $("nav.sortshow").css("display","none");
                    $("div.listshow ul.main").empty();
                    $("div.pageshow").css("display", "none");
                    $("div.mainshow ul.main").empty().css("display","none");
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
                    if (count < 3) {
                        $(".pageshow").css("display", "none");
                    }
                    else {
                        $(".pageshow").css("display", "-webkit-box");
                    }
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
    };
    $(document).on("click", ".listshow .main li", course_cli);
});