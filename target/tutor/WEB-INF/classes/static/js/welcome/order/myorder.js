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

    /**
     * 点击将指定订单加入回收站
     */
    var cli_removeorder = function(){

        if(window.confirm("确定要删除该订单数据吗? ")){

            var oid = $(this).data("oid");
            $.ajax({
                async: true,
                type: "post",
                url: "/courseorder_con/setinrecycle",
                data: {oid: oid},
                dataType: "json",
                success: function(data){
                    var stauts = data.status;
                    if(status == "invalid"){
                        window.location = "/forward_con/welcome";
                    }
                    else if(status == "mysqlerr"){
                        window.alert("后台数据库异常导致无法进行回收站操作，请稍后再试");
                    }
                    else{
                        window.alert("操作成功");
                        async_getmyorder();
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法进行回收站操作，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(document).on("click", "#mytable tbody tr td button", cli_removeorder);


    /**
     * 异步获取用户的订单数据
     */
    var state = "ed";
    var async_getmyorder = function(state){


        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/getmyorder",
            data: {status: state, startpos: 0},
            dataType: "json",
            success: function(data){
                var status = data.status;
                $("#mytable tbody").empty();
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else if(status == "valid"){
                    $("#mytable tbody").append("<tr class=\"none\">\n" +
                        "                        <td colspan=\"4\">空空如也</td>\n" +
                        "                    </tr>");
                    return ;
                }
                else{
                    $.each(data, function(index, item){
                        var cid = item.cid;
                        var id = item.id;
                        var name = item.name;
                        var otime = item.otime;
                        var price = item.price;
                        $("#mytable tbody").append("<tr>\n" +
                            "                    <td><a href='/forward_con/showcourse?id="+cid+"' target='_blank'>"+name+"</a></td>\n" +
                            "                    <td>"+price+"</td>\n" +
                            "                    <td>"+otime+"</td>\n" +
                            "                    <td>\n" +
                            "                        <button id=\"bElim\" type=\"button\" class=\"btn btn-sm btn-default\" data-oid='"+id+"' style=\"display: block;\">\n" +
                            "                            <span class=\"glyphicon glyphicon-trash\"> </span>\n" +
                            "                        </button>\n" +
                            "                    </td>\n" +
                            "                </tr>");
                    });
                }
            },
            error: function(xhr, status) {
                window.alert("后台环境异常导致无法获取用户订单数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getmyorder(state);

    /**
     * 左侧已支付/未支付/已失效的点击事件
     */
    var cli_modordertype = function(){

        if($(this).closest("li").hasClass("cli")){
            return;
        }

        $(".ordermain .bar ul li").removeClass("cli");
        $(this).closest("li").addClass("cli");

        state = $(this).data("status");
        async_getmyorder(state);
    };
    $(".ordermain .bar ul li a").click(cli_modordertype);
});