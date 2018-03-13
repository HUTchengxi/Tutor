$(function(){

    /**
     * 判断当前用户是否登录
     */
    var login_check = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/login_con/login_statuscheck",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "nologin"){
                    $("#menu-top-menu li.mine .sub-menu").append("<li><a href='/forward_con/gologin'><span class='glyphicon glyphicon-log-in'></span> 登录</a></li>"+
                        "                    <li><a href='/forward_con/goregister'><span class='glyphicon glyphicon-user'></span> 注册</a></li>\n");
                }
                else{
                    $("#menu-top-menu li.mine .sub-menu").append("<li><a>我的主页</a></li>\n" +
                        "                            <li><a>私信&nbsp;<span class='msg' style='color: red;'>(1)</span></a></li>\n" +
                        "                            <li><a>发表文章</a></li>\n" +
                        "                            <li><a>我的收藏</a></li>\n" +
                        "                            <li class='nav-logoff'><a href='#' style=\"color: red;\">注销</a></li>\n");
                }
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
                    alert("注销成功");
                    login_check();
                },
                error: function(xhr, status){
                    alert("后台环境异常导致无法正确退出登录，请刷新页面重试");
                }
            });
        }
    };
    $(document).on("click", ".mine .nav-logoff a", logoff_btn);

    /**
     * fadeOut显示小贴士数据
     */
    var intro_show = function(){

        $(".widget .support-widget .intro").animate({
            opacity: 1
        },1000);
    };
    intro_show();
});