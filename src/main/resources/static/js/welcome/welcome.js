$(function(){

    /**
     * 实现全屏滚动
     */
    $(".div-cls-fullpage").fullpage({
        sectionsColor:['white', 'white', 'white', 'white','white','white','white'],
        anchors: ['page1','page2','page3','page4','page5','page6','page7'],
        menu: ".div-cls-menu ul",
        afterLoad: function(anchroLink, index){
            //家长的烦恼
            if(index == 2){
                $(".div-cls-parentpage h3").delay(200).animate({
                    minHeight: "25px"
                },100);
                $(".div-cls-parentpage img:nth-child(2)").delay(500).animate({
                    left: "170px"
                },200);
                $(".div-cls-parentpage img:nth-child(3)").delay(500).animate({
                    left: "350px"
                },200);
            }
            //补习班选择的烦恼
            else if(index == 3){
                $(".div-cls-crampage p:nth-child(2)").delay(200).animate({
                    opacity: 1,
                    top: 0
                },200);
                $(".div-cls-crampage .pull-left li").delay(400).animate({
                    opacity: 1
                },300);
                $(".div-cls-crampage p:nth-child(3)").delay(700).animate({
                    opacity: 1,
                    top: "-470px"
                },200);
                $(".div-cls-crampage .pull-right li").delay(1000).animate({
                    opacity: 1
                },300);
            }
            //解决方案第一张
            else if(index == 4){
                $(".div-cls-mainpage h3").delay(200).animate({
                    opacity: 1,
                    top: "90px"
                },200);
            }
            //严格的家教审核
            else if(index == 5) {
                $(".div-cls-foruser h3.foruser-top").delay(200).animate({
                    top: "100px",
                    opacity: 1
                },200);
                $(".div-cls-foruser ul").delay(400).animate({
                    opacity: 1
                },100);
                $(".div-cls-foruser div.timeline-left").delay(400).animate({
                    left: 0
                },200);
                $(".div-cls-foruser div.timeline-right").delay(600).animate({
                    left: "-448px"
                },200);
                $(".div-cls-foruser div.timeline-left2").delay(800).animate({
                    left: 0
                },200);
                $(".div-cls-foruser div.timeline-right2").delay(1000).animate({
                    left: "-528px"
                },200);
            }
            //一体化对接
            else if(index == 6){
                $(".div-cls-step h3").delay(200).animate({
                    top: "100px",
                    opacity: 1
                },200);
                $(".div-cls-step div").delay(500).animate({
                    height: "667px"
                },400);
            }
            //孩子们的欢乐
            else if(index == 7){
                $(".div-cls-end h3").delay(200).animate({
                    minHeight: "25px"
                },100);
                $(".div-cls-end img:nth-child(2)").delay(500).animate({
                    left: "170px"
                },200);
                $(".div-cls-end img:nth-child(3)").delay(500).animate({
                    left: "550px"
                },200);
                $(".div-cls-end a").delay(800).animate({
                    opacity: 1,
                    height: "40px"
                },200);
            }
        }
    });

    /**
     * 页面加载时，显示第一页的动画
     */
    var pageshow_one = function(){
        $(".div-cls-childpage h3").delay(500).animate({
            opacity: 1
        },200);
        $(".div-cls-childpage img").delay(500).animate({
            right: 0
        },300);
        $(".div-cls-childpage img:nth-child(3)").animate({
            top: "-60px"
        },200);
    };
    pageshow_one();

    //获取系统时间-----------------
    Date.prototype.format = function(format) {
        var o ={
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format))format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o){if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));}
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
    var logoff_btn = function(){

        if(window.confirm("确认退出登录吗？")){
            $.ajax({
                asynv: false,
                type: "post",
                url: "/login_con/login_logoff",
                dataType: "json",
                success: function(data){
                    console.log(data);
                    alert("登录成功");
                    login_check();
                },
                error: function(xhr, status){
                    alert("后台环境异常导致无法正确退出登录，请刷新页面重试");
                }
            });
        }
    };
    $(document).on("click", ".navbar-right .nav-logoff a", logoff_btn);

});