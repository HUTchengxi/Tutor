;(function(){

    /**
     * 在用户名输入框失去焦点时进行校验事件
     */
    var login_user_blur = function(){

        var username = $("#username").val();
        if(username.trim() == ""){
            $("#p_id_err").data("status",1).text("用户名不能为空").css("display","block");
            return ;
        }
        var pid = $("#p_id_err").data("status");
        if(pid == 2)
            return ;
        $("#p_id_err").data("status",0).text("").css("display","none");
    };
    $("#username").blur(login_user_blur);

    /**
     * 在密码输入框失去焦点时进行校验事件
     */
    var login_pass_blur = function(){

        //如果用户名为空就不需要校验密码了
        var pid = $("#p_id_err").data("status");
        if(pid == 1)
            return ;
        var password = $("#password").val();
        if(password.trim() == ""){
            $("#p_id_err").data("status",2).text("密码不能为空").css("display","block");
            return ;
        }
        $("#p_id_err").data("status",0).text("").css("display","none");
    };
    $("#password").blur(login_pass_blur);

    /**
     * 在进行登陆点击时进行校验一次，然后登陆
     */
    var login_all_check = function(){

        $("#username").trigger("blur");
        $("#password").trigger("blur");
        var pid = $("#p_id_err").data("status");
        if(pid != 0)
            return;
        //进行异步登陆校验
        var username = $("#username").val();
        var password = $("#password").val();
        var remember = $("#remember").data("status");
        $.ajax({
            async: true,
            type: "post",
            url: "/login_con/login",
            data: {"username": username, "password": password, "remember": remember},
            dataType: "json",
            success: function(data){
                var status = data.status;
                var url = data.url;
                if(status == "nouser" || status == "passerr"){
                    $("#p_id_err").data("status",3).text("账号或密码错误").css("display","block");
                }
                else{
                    window.alert("登陆成功");
                    var logcity = "未知地区";
                    var ip = "000.000.000.000";

                    //获取电脑的操作系统
                    var logsystem = "位置的操作系统";
                    var sUserAgent = navigator.userAgent;
                    logsystem = (navigator.platform == "Win32") || (navigator.platform == "Windows")? "Windows" :
                        ((navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel"))? Mac :
                            (navigator.platform == "X11")? "Unix" :
                                (String(navigator.platform).indexOf("Linux") > -1)? "Linux" : "未知的操作系统";

                    //获取ip地址
                    ip = returnCitySN["cip"];

                    //获取登录地区
                    $.ajax({
                        async: false,
                        type: "get",
                        url: "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js",
                        dataType: "jsonp",
                        success: function(data){
                            logcity = unescape(data.province + data.city);
                        },
                        error: function(xhr, status){
                            alert("网络异常导致无法进行相关记录");
                            console.log("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js error");
                        }
                    });

                    //保存登录记录
                    $.ajax({
                        async: true,
                        type: "post",
                        url: "/userlog_con/loginlog",
                        data: {"logcity": logcity, "ip": ip, "logsystem": logsystem},
                        dataType: "json",
                        success: function(data){
                            console.log(data.status);
                        },
                        error: function(xhr, status){
                            console.log("/usermain_con/loginlog error");
                        }
                    })
                    window.location.href = url;
                }
            },
            error: function(xhr, status){
                window.alert("服务器环境异常，请稍后登陆");
                console.log(xhr);
            }
        });
    };
    $(".button_class_submit").click(login_all_check);
}());
