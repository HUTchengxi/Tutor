$(function(){

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



    /**
     * 周榜/日榜/总榜的点击事件
     */
    var rank_time = function(){

        var hasshow = $(this).closest("li").is("cli");
        //当前正显示该点击的内容
        if(hasshow){
            return ;
        }
        var ranktype = $(this).closest("div").data("type");
        var rankmark = $(this).data("mark");
        $(this).closest("div").find("li.cli").removeClass("cli");
        $(this).closest("li").addClass("cli");
        var $this = $(this);

        //最勤打卡榜
        if(ranktype == "sign"){
            if(rankmark == "day") {
                $(this).closest("div").find("table thead tr td:nth-child(3)").text("时间");
            }
            else{
                console.log(1);
                $(this).closest("div").find("table thead tr td:nth-child(3)").text("打卡数");
            }
        }

        //异步加载数据
        $.ajax({
            async: true,
            type: "post",
            url: "/rank_con/rank_select",
            data: {type: ranktype, mark: rankmark, startpos: 0},
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count == 0){
                    $this.closest("div").find("table tbody").text("").append("<tr>\n" +
                        "                                            <td colspan=\"3\" style=\"color: grey;\">暂无排名</td>\n" +
                        "                                        </tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>");
                }
                else{
                    $this.closest("div").find("table tbody").text("");
                    var count = 0;
                    $.each(data, function(index, item){
                        count++;
                        var nickname = item.nickname;
                        var stime = item.stime;
                        if(index == 1) {
                            $this.closest("div").find("table tbody").append("<tr>\n" +
                                "                                        <td style='color: red;'>1</td>\n" +
                                "                                        <td style=\"\">"+nickname+"</td></td>\n" +
                                "                                        <td style=\"\">"+stime+"</td>\n" +
                                "                                    </tr>");
                       }
                       else if(index == 2) {
                            $this.closest("div").find("table tbody").append("<tr>\n" +
                                "                                        <td style='color: #00bc9b;'>2</td>\n" +
                                "                                        <td style=\"\">"+nickname+"</td></td>\n" +
                                "                                        <td style=\"\">"+stime+"</td>\n" +
                                "                                    </tr>");
                        }
                        else if(index == 3) {
                            $this.closest("div").find("table tbody").append("<tr>\n" +
                                "                                        <td style='color: #a4b2ba;'>3</td>\n" +
                                "                                        <td style=\"\">"+nickname+"</td></td>\n" +
                                "                                        <td style=\"\">"+stime+"</td>\n" +
                                "                                    </tr>");
                        }
                        else{
                            $this.closest("div").find("table tbody").append("<tr>\n" +
                                "                                        <td style='color: #d4d4d4;'>4</td>\n" +
                                "                                        <td style=\"\">"+nickname+"</td></td>\n" +
                                "                                        <td style=\"\">"+stime+"</td>\n" +
                                "                                    </tr>");
                        }
                    });
                    console.log(count);

                }
            },
            error: function(xhr, status){
                $this.closest("div").find("table tbody").text("").append("<tr>\n" +
                    "                                            <td colspan=\"3\" style=\"color: red;\">数据加载异常</td>\n" +
                    "                                        </tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>");
                alert("后台服务器异常导致无法获取排行榜信息，请稍后重试");
            }
        })
    };
    $(".rank ul li a").click(rank_time);

    /**
     * 查看全部排行链接
     */
    var rank_more = function(){

        var ranktype = $(this).closest("div.rank").data("type");
        window.open("/forward_con/rank_more?type=" + ranktype);
    };
    $(".rank footer a").click(rank_more);










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