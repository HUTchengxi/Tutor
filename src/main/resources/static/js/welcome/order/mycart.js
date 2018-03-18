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


    //-----------------------获取购物车数据-------------------
    /**
     * 异步加载用户购物车数据
     */
    var async_mycart = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/getmycart",
            data: {startpos: 0},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else if(status === "valid"){
                    $(".cartmain .main").empty();
                    $(".cartmain .main").append("<p class=\"none\"><i class=\"layui-icon\">&#xe69c;</i>空空如也</p>");
                }
                else{
                    var count = 0;
                    var total = 0;
                    $.each(data, function(index, item){
                        var imgsrc = item.imgsrc;
                        var name = item.name;
                        var price = parseInt(item.price);
                        var id = item.id;
                        var cid = item.cid;
                        total += price;
                        $(".cartmain .main ul.main-ul").append("<li class=\"main-info\">\n" +
                            "                        <div class=\"main-top show\">\n" +
                            "                            <ul class=\"clearfix\">\n" +
                            "                                <li class=\"pull-left\" data-id='"+id+"'><span class=\"layui-icon\">&#xe618;</span></li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <div class=\"cart-course\">\n" +
                            "                                        <a href=\"http://localhost:8080/forward_con/showcourse?id="+cid+"\" target='_blank'><img src=\""+imgsrc+"\" /></a>\n" +
                            "                                        <a href=\"http://localhost:8080/forward_con/showcourse?id="+cid+"\" target='_blank'><p>"+name+"</p></a>\n" +
                            "                                    </div>\n" +
                            "                                </li>\n" +
                            "                                <li class=\"pull-left\" data-price='"+price+"'>￥"+price+"</li>\n" +
                            "                                <li class=\"pull-left\">\n" +
                            "                                    <a href=\"javascript:void(0);\" data-did='"+id+"'>删除</a>\n" +
                            "                                </li>\n" +
                            "                            </ul>\n" +
                            "                        </div>\n" +
                            "                    </li>");
                        if(++count == 1){
                            $(".cartmain .show").addClass("first");
                        }
                    });
                    $(".main-top ul.top li:nth-child(1)").data("total",total).data("status","none");
                    $(".cartmain .main ul.main-ul").append("<li>\n" +
                        "                        <div class=\"main-top footer clearfix\">\n" +
                        "                            <a class=\"pull-right\" href=\"\" data-sid=''>去结算</a>\n" +
                        "                            <div class=\"price pull-right\">\n" +
                        "                            <p>总计金额: </p>\n" +
                        "                        <p class=\"money\">￥0.00</p>\n" +
                        "                        </div>\n" +
                        "                        </div>\n" +
                        "                    </li>");
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取购物车数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_mycart();

    /**
     * 获取购物车物品数量
     */
    var async_mycartcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/getmycartcount",
            data: {startpos: 0},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else {
                    $(".total").text(data.count);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取购物车总数数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_mycartcount();

    /**
     * 删除购物车物品
     */
    var del_mycart = function(){

        var did = $(this).data("did");

        if(confirm("确定要删除该物品吗?")) {
            $.ajax({
                async: true,
                type: "post",
                url: "/courseorder_con/delmycart",
                data: {id: did},
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    if(status === "valid"){
                        window.alert("删除成功");
                        window.history.go(0);
                    }
                    else{
                        window.location = "/forward_con/gologin";
                    }
                },
                error: function (xhr, status) {
                    window.alert("后台环境异常导致无法删除购物车数据，请稍后再试");
                    console.log(xhr);
                }
            });
        }
    };
    $(document).on("click", ".cartmain .show ul li:nth-child(4) a", del_mycart);

    /**
     * 全选指定物品
     */
    var sel_mycart = function(){

        var status = $(this).data("status");
        if(status === "none"){
            $(this).find("span.layui-icon").addClass("cli");
            $(".show ul li:nth-child(1) span").addClass("cli");
            $(this).data("status","all").find("span.word").text("取消全选");
            price_mycart();
        }
        else{
            $(".show ul li:nth-child(1) span").removeClass("cli");
            $(this).find("span.layui-icon").removeClass("cli");
            $(this).data("status","none").find("span.word").text("全选");
            price_mycart();
        }
    };
    $(".top li:nth-child(1)").click(sel_mycart);

    /**
     * 自定义选择物品
     */
    var hsel_mycart = function(){

        //取消全选状态
        $(".top li:nth-child(1) span.layui-icon").removeClass("cli");
        $(".top li:nth-child(1)").data("status","none").find("span.word").text("全选");
        if($(this).find("span").hasClass("cli")){
            $(this).find("span").removeClass("cli");
        }
        else{
            $(this).find("span").addClass("cli");
        }
        price_mycart();

        //全选状态判断
        var total = 0;
        $(".show ul li:nth-child(1)").each(function(){
            if($(this).find("span.layui-icon").hasClass("cli")){
                total += parseInt($(this).closest("ul").find("li:nth-child(3)").data("price"));
            }
        });
        //全选
        if(total == $(".cartmain .main .main-top .top li:nth-child(1)").data("total")){
            $(".top li:nth-child(1)").find("span.layui-icon").addClass("cli");
            $(".top li:nth-child(1)").data("status","all").find("span.word").text("取消全选");
        }
        //非全选
        else{
            $(".top li:nth-child(1)").find("span.layui-icon").removeClass("cli");
            $(".top li:nth-child(1)").data("status","none").find("span.word").text("全选");
        }
    };
    $(document).on("click", ".main-info ul li:nth-child(1)", hsel_mycart);

    /**
     * 计算需支付金额
     */
    var price_mycart = function(){
        var total = 0.00;
        var sid = "";
        var count = 0;
        $(".show ul li:nth-child(1)").each(function(){
            if($(this).find("span.layui-icon").hasClass("cli")){
                total += parseInt($(this).closest("ul").find("li:nth-child(3)").data("price"));
                sid += $(this).data("id") + " ";
            count++;
            }
        });
        $(".cur").text(count);
        $(".cartmain .footer .price .money").text("￥"+total.toFixed(2));
        $(".cartmain .footer a").data("sid",sid);
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