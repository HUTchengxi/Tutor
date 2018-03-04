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
                    $("nav ul.navbar-right").append("<li class='nav-time'><a>现在是：<i style='color: #00b6ff;'>"+now+"</i></a></li>" +
                        "                   <li><a style=\"color: black;\"><p>"+hello+"：<span class=\"span-cls-nick\" style=\"color: red;\">"+nickname+"</span></p></a></li>\n" +
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

        //异步加载数据
        $.ajax({
            async: true,
            type: "post",
            url: "/rank_con/rank_select",
            data: {type: ranktype, mark: rankmark},
            dataType: "json",
            success: function(data){
                console.log(data);
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
        window.location.href = "/forward_con/rank_more?type=" + ranktype;
    };
    $("footer a").click(rank_more);
});