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
                    window.location = "/forward_con/welcome";
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
                        "                   <li><a style=\"color: black;\"><p>" + hello + "：<span class=\"span-cls-nick\" style=\"color: red;\">" + nickname + "</span></p></a></li>\n</li>");
                    //让时间一直轮播
                    var timeinter = window.setInterval(function () {
                        var now = new Date().format("yyyy-MM-dd hh:mm:ss");
                        $("nav ul.navbar-right li.nav-time a i").text(now);
                    }, 1000);
                }
            },
            error: function(xhr, status){
                alert("后台环境异常导致无法正确获得登录信息，请刷新页面重试");
            }
        });
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
     * 异步加载获取头像路径
     */
    var async_userface = function(){

        $.ajax({
            async: true,
            type: "get",
            url: "/usermain_con/getimgsrc",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = "/404_not_found";
                }
                else{
                    var imgsrc = data.imgsrc;
                    $(".logo").css("background","url("+imgsrc+") no-repeat center center")
                        .css("background-size","100% 100%").data("imgsrc",imgsrc).data("oldimgsrc",imgsrc);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户头像，请刷新页面重试");
                console.log(status);
            }
        });
    };
    async_userface();




    //-------------------------------------------------个人头像的基本设置-----------------------------------------------
    /**
     * 单击头像进行放大
     */
    var big_userface = function(){

        var imgsrc = $(".logo").data("imgsrc");
        var height = $(document).height();
        $(".zhezhao").css("height",height+"px").css("display","block");
        $(".showbig .main img").attr("src", imgsrc);
        $(".showbig").css("display","block").animate({
            opacity: 1
        },200);
    };
    $(".logo").click(big_userface);

    /**
     * 关闭头像放大框
     */
    var close_bigface = function(){

        $(".showbig").animate({
            opacity: 0
        },200);
        $(".showbig").delay(200).css("display","none");
        $(".zhezhao").delay(200).css("display","none");
    };
    $(".showbig .main a").click(close_bigface);

    /**
     * 点击更换头像显示更换头像框
     */
    var mod_userface = function(event){

        event.stopPropagation();
        var imgsrc = $(".logo").data("imgsrc");
        $(".modface .show").css("background","url("+imgsrc+") no-repeat center center")
            .css("background-size","100% 100%");
        var height = $(document).height();
        $(".zhezhao").css("height",height+"px").css("display","block");
        $(".modface").css("display","block").animate({
            opacity: 1
        },200);
    };
    $(".logo a").click(mod_userface);

    /**
     * 关闭头像更改框
     */
    var close_modface = function(){

        $(".modface").animate({
            opacity: 0
        },200);
        $(".modface").delay(200).css("display","none");
        $(".zhezhao").delay(200).css("display","none");
        $(".logo").data("imgsrc",$(".logo").data("oldimgsrc"));
    };
    $(".modface .sublink a.reset").click(close_modface);
    $(".modface .main a.close").click(close_modface);
    $(".zhezhao").click(close_modface);

    /**
     * 点击换一换进行本地图片随机换
     */
    var rand_modface = function(){

        var randNum = Math.ceil(Math.random()*8);
        var randImgsrc = "/images/default/" + randNum + ".jpg";
        $(".modface .show").data("imgsrc",randImgsrc);
        $(".modface .show").css("background","url("+randImgsrc+") no-repeat center center")
            .css("background-size","100% 100%");
        $(".logo").data("imgsrc",randImgsrc);
    };
    $(".modface .modlink a.rand").click(rand_modface);

    /**
     * 提交我的头像的更换
     */
    var sub_modface = function(){

        var imgsrc = $(".logo").data("imgsrc");
        var oldimgsrc = $(".logo").data("oldimgsrc");
        if(imgsrc === oldimgsrc){
            window.alert("没有做任何修改");
        }
        //手动上传的提交
        else if(imgsrc === "hand"){
            var imgfile = $(".modface .modlink input").get(0).files[0];
            var oimgsrc = $(".logo").data("oldimgsrc");
            var formData = new FormData();
            formData.append("imgfile", imgfile);
            formData.append("oimgsrc",oimgsrc);
            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/modimgfile",
                data: formData,
                dataType: "json",
                contentType: false,
                processData: false,
                success: function(data){
                    var status = data.status;
                    if(status === "invalid"){
                        window.location.href = "/forward_con/welcome";
                    }
                    else if(status === "mysqlerr"){
                        alert("后台数据库出了点问题，请稍后再试");
                    }
                    else{
                        alert("更换成功");
                        var imgsrc = data.imgsrc;
                        $(".modface .close").trigger("click");
                        $(".logo").css("background","url("+imgsrc+") no-repeat center center")
                            .css("background-size","100% 100%").data("imgsrc", imgsrc).data("oldimgsrc",imgsrc);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致头像修改失败，请稍后重试");
                    console.log(xhr);
                }
            });
        }
        else{
            //我帮你换的提交
            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/modimgsrc",
                data: {imgsrc: imgsrc},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status === "invalid"){
                        window.location.href = "/forward_con/welcome";
                    }
                    else if(status === "mysqlerr"){
                        alert("后台数据库出了点问题，请稍后再试");
                    }
                    else{
                        alert("更换成功");
                        var imgsrc = data.imgsrc;
                        $(".modface .close").trigger("click");
                        $(".logo").css("background","url("+imgsrc+") no-repeat center center")
                            .css("background-size","100% 100%").data("imgsrc", imgsrc).data("oldimgsrc",imgsrc);
                    }
                },
                error: function(xhr, status){
                    alert("后台环境异常导致头像修改失败，请稍后重试");
                    console.log(xhr);
                }
            });
        }
    };
    $(".modface .sublink a.submit").click(sub_modface);


    /**
     * 手动选择头像
     */
    var hand_selface = function(){

        $(".modface .modlink li input").trigger("click");
    };
    $(".modface .modlink li a.hand").click(hand_selface);

    /**
     * 选择头像
     */
    var input_selface = function(){

        var imgfile = this.files[0];

        //没有使用头像的情况下则使用原来的个人头像
        if(imgfile === undefined){
            var imgsrc = $(".logo").data("imgsrc");
            $(".modface .show").css("background","url("+imgsrc+") no-repeat center center")
                .css("background-size","100% 100%").data("imgsrc", $(".logo").data("oldimgsrc"));
            return false;
        }
        var reader = new FileReader();
        reader.onload = function(e){
            $(".modface .show").css("background","url("+e.target.result+") no-repeat center center")
                .css("background-size","100% 100%").data("imgsrc","hand");
            $(".logo").data("imgsrc","hand");
        };
        reader.readAsDataURL(imgfile);
    };
    $(".modface .modlink li input").change(input_selface);




    //--------------------------------------------------个人信息的设置-------------------------------------------------
    /**
     * 异步加载个人信息
     */
    var async_userinfo = function(){

        $.ajax({
            async: true,
            type: "get",
            url: "/usermain_con/getuserinfo",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = "/404_not_found";
                }
                else{
                    var username = data.username;
                    var nickname = data.nickname;
                    var sex = data.sex;
                    var age = data.age;
                    var info = data.info;
                    $(".maininfo .username").val(username).data("oval",username);
                    $(".maininfo .nickname").val(nickname).data("oval",nickname);
                    $(" .maininfo .sex").data("oval",sex);
                    sex === "男"? $(".maininfo .sexlabel .male").trigger("click"): $(".maininfo .sexlabel .female").trigger("click");
                    $(".infobox .maininfo .sex").val(sex);
                    $(".maininfo .age").val(age).data("oval",age);
                    $(".maininfo .info").text(info).data("oval",info);

                    //性别编辑按钮的值的设置
                    if(sex === "男"){
                        $("#modinfo .maininfo .sexlabel .male").trigger("click");
                    }
                    else{
                        $("#modinfo .maininfo .sexlabel .female").trigger("click");
                    }
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户个人信息，请刷新页面重试");
                console.log(xhr);
            }
        });
    };
    async_userinfo();

    /**
     * 关闭个人信息设置的编辑框
     */
    var close_userinfo = function(){

        $(".modinfo").animate({
            opacity: 0
        },200);
        $(".modinfo").delay(200).css("display","none");
        $(".zhezhao").delay(200).css("display","none");

        //回复原始值
        $(".maininfo .username").val($(".maininfo .username").data("oval"));
        $(".maininfo .nickname").val($(".maininfo .nickname").data("oval"));
        $(".maininfo .sex").data("oval")==="男"? $(".maininfo .sexlabel .male").trigger("click"):
            $(".maininfo .sexlabel .female").trigger("click");
        $(".maininfo .age").val($(".maininfo .age").data("oval"));
        $(".maininfo .info").val($(".maininfo .info").data("oval"));
    };
    $("#modinfo .main a.close").click(close_userinfo);
    $("#modinfo .main .sublink .reset").click(close_userinfo);
    $(".zhezhao").click(close_userinfo);

    /**
     * 打开个人信息设置的编辑框
     */
    var open_userinfo = function(){

        var height = $(document).height();
        $(".zhezhao").css("height",height+"px").css("display","block");
        $("#modinfo").css("display","block").animate({
            opacity: 1
        },200);
    };
    $(".infobox .infodiv header a").click(open_userinfo);

    /**
     * 提交个人信息编辑
     */
    var sub_modinfo = function(){

        var username = $(".modinfo .maininfo .username").val();
        var nickname = $(".modinfo .maininfo .nickname").val();
        var sex = $(".modinfo .maininfo .sex:checked").val();
        var age = $(".modinfo .maininfo .age").val();
        var info = $(".modinfo .maininfo .info").text();

        var formData = new FormData();
        formData.append("username", username);
        formData.append("nickname", nickname);
        formData.append("sex", sex);
        formData.append("age", age);
        formData.append("info", info);

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/moduserinfo",
            data: formData,
            dataType: "json",
            contentType: false,
            processData: false,
            success: function(data){
                var status = data.status;
                if(status === "mysqlerr"){
                    window.alert("后台数据库异常导致无法修改个人信息，请稍后再试");
                }
                else if(status === "invalid"){
                    window.location = data.url;
                }
                else{
                    window.alert("更改成功");
                    window.history.go(0);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法修改个人信息，可以尝试刷新页面或者稍后重试");
                console.log(xhr);
            }
        })
    };
    $("#modinfo .main .sublink .submit").click(sub_modinfo);








    //-----------------------------------------------账号绑定的设置-----------------------
    /**
     * 异步获取账号绑定的设置
     */
    var async_getbind = function(){

        //获取电子邮箱和手机号码的绑定数据
        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/getbindinfo",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else{
                    var tel = data.tel;
                    var ema = data.ema;

                    //手机未绑定
                    if(tel == "" || tel == null || tel == "null"){
                        $(".binddiv .mainbind .tel-phone p:nth-child(2)").append("<span class='ok'>(暂未绑定)</span>");
                        $(".binddiv .mainbind .tel-phone p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-bind\" data-type=\"phone\">立刻绑定</a>");
                    }
                    else{
                        $(".binddiv .mainbind .tel-phone p:nth-child(2)").append("<span>(已绑定<i>"+tel+"</i>)</span>");
                        $(".binddiv .mainbind .tel-phone p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-unbind\" " +
                            "data-type=\"phone\">解绑</a>");
                    }

                    //电子邮箱未绑定
                    if(ema == "" || ema == "null" || ema == null){
                        $(".binddiv .mainbind .email p:nth-child(2)").append("<span class='ok'>(暂未绑定)</span>");
                        $(".binddiv .mainbind .email p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-bind\" data-type=\"email\">立刻绑定</a>");
                    }
                    else{
                        $(".binddiv .mainbind .email p:nth-child(2)").append("<span>(已绑定<i>"+ema+"</i>)</span>");
                        $(".binddiv .mainbind .email p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-unbind\" " +
                            "data-type=\"email\">解绑</a>");
                    }
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取绑定数据，请刷新页面重试");
                window.console.log(xhr);
            }
        })
    };
    async_getbind();

    /**
     * 异步获取我的密保问题数据
     */
    var async_getsecretinfo = function(){

        $.ajax({
            async: false,
            type: "post",
            url: "/usersecret_con/getsecretinfo",
            dataType: "json",
            success: function(data){
                var status = data.status;
                $(".binddiv .mainbind .mb-phone p:nth-child(3) a").remove();
                $(".alert-secretinfo .secret-one input").data("orig", "");
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else if(status == "valid"){
                    $(".binddiv .mainbind .mb-phone p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-modmb\">立即设置</a>");
                }
                else{
                    $(".binddiv .mainbind .mb-phone p:nth-child(3)").append("<a href=\"javacript:void(0)\" class=\"pull-right btn-modmb\">点击修改</a>");
                    //弹出框注入数据
                    var count = 2;
                    $.each(data, function(index, item){
                       var question = item.question;
                       var answer = item.answer;
                       $(".alert-secretinfo div:nth-child("+(count++)+") input").data("orig", question).val(question);
                       $(".alert-secretinfo div:nth-child("+(count++)+") input").data("orig", answer).val(answer);
                    });
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取密保数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getsecretinfo();

    /**
     * 点击显示密保设置弹出框
     */
    var cli_openmodmb = function(){

        $(".zhezhao").css("display", "block").css("height", $(document).height()+"px");
        $(".alert-secretinfo").animate({
            top: "25%",
            opacity: 1
        },200);
    };
    $(document).on("click", ".binddiv .mainbind .mb-phone p .btn-modmb", cli_openmodmb);

    /**
     * 点击关闭密保设置弹出框
     */
    var cli_closemodmb = function(){

        $(".alert-secretinfo .secret-one").each(function(){
           var orig = $(this).find("input").data("orig");
           $(this).find("input").val(orig);
        });

        $(".zhezhao").css("display", "none");
        $(".alert-secretinfo").animate({
            opacity: 0,
            top: "-40%"
        }, 200);
    };
    $(".alert-secretinfo .close").click(cli_closemodmb);
    $(".alert-secretinfo .btn-reset").click(cli_closemodmb);
    $(".zhezhao").click(cli_closemodmb);

    /**
     * 判空函数
     */
    var str_isnull = function(val){
        if(val == null || val.trim() == ""){
            return true;
        }
        return false;
    };

    /**
     * 点击提交密保的修改事件
     */
    var cli_submodmb = function(){

        //判断：问题为空，答案不为空; 问题不为空，答案为空
        var cnt = 0;
        var question = null;
        var answer = null;
        $(".alert-secretinfo .secret-one").each(function(){
            if(cnt == 0){
                question = $(this).find("input").val();
                cnt++;
            }
            else{
                cnt--;
                answer = $(this).find("input").val();
                if(str_isnull(answer) && !str_isnull(question)){
                    $(this).find(".err-info").css("display", "block").text("请填写答案");
                    return ;
                }
                if(!str_isnull(answer) && str_isnull(question)){
                    $(this).find(".err-info").css("display", "block").text("请填写问题");
                    return ;
                }
            }
        });
        //首先删除所有的数据
        $.ajax({
            async: false,
            type: "post",
            url: "/usersecret_con/delusersecret",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                    return ;
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法更新密保数据，请稍后再试");
                window.console.log(xhr);
                return ;
            }
        });


        //然后进行遍历提交密保数据
        $(".alert-secretinfo .secret-one").each(function(){
            //直接进行判断是否为空就行了
            if(cnt == 0){
                question = $(this).find("input").val();
                cnt++;
            }
            else {
                cnt--;
                if(!str_isnull(question)){
                    answer = $(this).find("input").val();
                    $.ajax({
                        async: false,
                        type: "post",
                        url: "/usersecret_con/addusersecret",
                        data: {question: question, answer: answer},
                        dataType: "json",
                        success: function (data) {
                            var status = data.status;
                            if(status == "invalid"){
                                window.location = "/forward_con/welcome";
                            }
                            else if(status == "mysqlerr"){
                                windwo.alert("后台数据库异常导致密保数据更新异常");
                                window.console.log(xhr);
                                return ;
                            }
                        },
                        error: function (xhr, status) {
                            window.alert("后台环境异常导致无法修改密保数据，请稍后再试");
                            window.console.log(xhr);
                        }
                    });
                }
                question = null;
                answer = null;
            }
        });
        window.alert("密保设置成功");
        async_getsecretinfo();
        $(".zhezhao").trigger("click");
    };
    $(".alert-secretinfo .btn-submit").click(cli_submodmb);

    /**
     * 解绑的点击事件
     */
    var cli_unbind = function(){

        var type = $(this).data("type");
        var val = $(this).closest("div").find("p:nth-child(2) span i").text();

        var info = "";
        if(type == "email"){
            info = "           确定要接触邮箱";
        }
        else{
            info = "           确定要接触手机号码";
        }
        info += val+"的绑定吗？";
        if(window.confirm(info)){
            $(".zhezhao").css("display", "block").css("height", $(document).height());
            $(".alert-unbind").data("type", type).animate({
                top: "25%",
                opacity: 1
            },200);
        }
    };
    $(document).on("click", ".binddiv .mainbind p a.btn-unbind", cli_unbind);

    /**
     * 立即绑定的点击事件
     */
    var showtype = "";
    var cli_bind = function(){
        var type = $(this).data("type");
        showtype = type;
        $(".alert-bind").animate({
            top: "25%",
            opacity: 1
        },200);
        $(".zhezhao").css("display", "block").css("height", $(document).height());
        //绑定邮箱
        if(type == "email"){
            $(".alert-bind label span.info").text("电子邮箱");
        }
        else{
            $(".alert-bind label span.info").text("手机号码");
        }
    };
    $(document).on("click", ".binddiv .mainbind p a.btn-bind", cli_bind);

    /**
     * 点击立即绑定
     */
    var cli_subbind = function(){

        var email = $(".alert-bind .valiinfo").val();
        var valicode = $(".alert-bind .valicode").val();

        if(valicode == null || valicode.trim() == ""){
            $(".alert-bind .err-vali").css("display", "block").text("验证码不能为空");
        }
        else{
            //进行绑定
            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/userbind",
                data: {type: showtype, email: email, valicode: valicode},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "invalid"){
                        window.location = "/forward_con/welcome";
                    }
                    else if(status == "codeerr"){
                        $(".alert-bind .err-vali").css("display", "block").text("验证码不正确");
                    }
                    else{
                        $(".alert-bind .err-vali").css("display", "none");
                        window.alert("您已成功绑定");
                        window.history.go(0);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法进行绑定，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(".alert-bind .btn-unbind").click(cli_subbind);

    /**
     * 关闭立即绑定悬浮框事件
     */
    var cli_closebind = function(){

        showtype = "";
        $(".alert-bind").animate({
            top: "-40%",
            opacity: 0
        },200);
        $(".zhezhao").css("display", "none");
    };
    $(".alert-bind a.alink-unbind").click(cli_closebind);
    $(".zhezhao").click(cli_closebind);
    $(".alert-bind .btn-reset").click(cli_closebind);

    /**
     * 点击发送绑定验证码
     */
    var cli_sendbindcode = function(){

        if($(this).data("status") == "ing"){
            return ;
        }

        var email = "";
        var phone = "";
        if(showtype == "email"){
            email = $(".alert-bind .valiinfo").val();
            if(email == null || email.trim() == ""){
                $(".alert-bind .err-email").css("display", "block").text("请填写邮箱");
                return ;
            }
        }
        else if(showtype == "phone"){
            phone = $(".alert-bind .valiinfo").val();
            if(phone == null || phone.trim() == ""){
                $(".alert-bind .err-email").css("display", "block").text("请填写手机号码");
                return ;
            }
            else if(phone.length < 11){
                $(".alert-bind .err-email").css("display", "block").text("手机号码格式不正确");
                return ;
            }
        }
        else{
            window.location = "/forward_con/welcome";
            return ;
        }

        $(".alert-bind .err-email").css("display", "none");
        //发送验证码
        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/sendbindcode",
            data: {type: showtype, email: email, phone: phone},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else if(status == "exist"){
                    if(showtype == "email") {
                        $(".alert-bind .err-email").css("display", "block").text("邮箱已被绑定");
                    }
                    else{
                        $(".alert-bind .err-email").css("display", "block").text("手机已被绑定");
                    }
                }
                else{
                    window.alert("发送成功");
                    var interval;
                    var time = 60;
                    //邮件验证码发送成功的冷却事件
                    $(".alert-bind .div-valicode .resend").data("status", "ing");
                    var interval = window.setInterval(function(){
                        if(time == 0){
                            $(".alert-bind .div-valicode .resend").data("status", "ed").text("获取验证码");
                            window.clearInterval(interval);
                            return;
                        }
                        $(".alert-bind .div-valicode .resend").text(""+time+"s后可重发");
                        time--;
                    }, 1000);
                }
            },
            error: function(xhr, status) {
                window.alert("后台环境异常导致无法进行绑定，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".alert-bind .resend").click(cli_sendbindcode);

    /**
     * 解绑弹出框的关闭事件：
     *  close按钮点击/取消绑定按钮得点击/遮罩层的点击
     */
    var cli_closeunbind = function(){

        $(".zhezhao").css("display", "none");
        $(".alert-unbind").animate({
            top: "-40%",
            opacity: 0
        },200).delay(500);
    };
    $(".alert-unbind a.alink-unbind").click(cli_closeunbind);
    $(".alert-unbind .div-unbind .btn-reset").click(cli_closeunbind);
    $(".zhezhao").click(cli_closeunbind);

    /**
     * 点击获取解除绑定的验证码
     */
    var cli_getvalicode = function(){

        var type = $(this).closest("div.alert-unbind").data("type");

        if(type === "email"){
            var email = $(".binddiv .mainbind .email p:nth-child(2) span i").text();

            var status = $(this).data("status");
            if(status == "ing"){
                return ;
            }

            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/forget_sendmail",
                data: {email: email},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "invalid"){
                        $(".err-email").css("display", "block").text("邮箱不存在");
                        return ;
                    }
                    else{
                        $(".err-email").css("display", "none");
                        window.alert("发送成功，请查看您的邮箱");
                        var interval;
                        var time = 60;
                        //邮件验证码发送成功的冷却事件
                        $(".alert-unbind .div-valicode .resend").data("status", "ing");
                        var interval = window.setInterval(function(){
                            if(time == 0){
                                $(".alert-unbind .div-valicode .resend").data("status", "ed").text("获取验证码");
                                window.clearInterval(interval);
                                return;
                            }
                            $(".alert-unbind .div-valicode .resend").text(""+time+"s后可重发");
                            time--;
                        }, 1000);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法发送邮箱短码，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
        else{
            var phone = $(".binddiv .mainbind .phone p:nth-child(2) span i").text();

            var status = $(this).data("status");
            if(status == "ing"){
                return ;
            }

            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/sendunbindphone",
                data: {phone: phone},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "invalid"){
                        $(".err-email").css("display", "block").text("手机不存在");
                        return ;
                    }
                    else{
                        $(".err-email").css("display", "none");
                        window.alert("发送成功");
                        var interval;
                        var time = 60;
                        //邮件验证码发送成功的冷却事件
                        $(".alert-unbind .div-valicode .resend").data("status", "ing");
                        var interval = window.setInterval(function(){
                            if(time == 0){
                                $(".alert-unbind .div-valicode .resend").data("status", "ed").text("获取验证码");
                                window.clearInterval(interval);
                                return;
                            }
                            $(".alert-unbind .div-valicode .resend").text(""+time+"s后可重发");
                            time--;
                        }, 1000);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法发送邮箱短码，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(".alert-unbind .div-valicode .resend").click(cli_getvalicode);

    /**
     * 解除绑定的提交按钮
     */
    var cli_subunbind = function(){

        var type = $(".alert-unbind").data("type");
        var valicode = $(".alert-unbind .div-valicode .valicode").val();

        if(valicode == ""){
            if(type == "email") {
                $(".err-email").css("display", "block").text("请填写邮箱");
            }
            else{
                $(".err-email").css("display", "block").text("请填写手机号码");
            }
        }
        else{
            //解除绑定
            $.ajax({
                async: true,
                type: "post",
                url: "/usermain_con/unbind_valicode",
                data: {type: type, valicode: valicode},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "invalid"){
                        window.location = "/forward_con/welcome";
                    }
                    else if(status == "mysqlerr"){
                        window.alert("后台数据库异常导致无法进行解除绑定，请稍后重试");
                    }
                    else if(status == "codeerr"){
                        $(".err-email").css("display", "block").text("验证码不正确");
                    }
                    else{
                        window.alert("您已成功解除绑定");
                        window.history.go(0);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法进行解除绑定，请稍后重试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(".alert-unbind .div-unbind .btn-unbind").click(cli_subunbind);






    //-----------------------------------------------登录记录的设置------------------------
    /**
     * 关闭提示按钮的功能实现
     */
    var close_msg = function(){
        $(this).closest("p").animate({
            height: 0
        },200);
    };
    $(".infobox .logdiv p.msg a").click(close_msg);

    /**
     * 异步获取登录记录
     */
    var async_getlog = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/userlog_con/getuserlog",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = data.url;
                }
                else if(status === "ok"){
                    return ;
                }
                $.each(data, function(index, item){
                   var logtime = item.logtime;
                   var logip = item.logip;
                   var logsystem = item.logsystem;
                   var logcity = item.logcity;
                   $(".infobox .logdiv table tbody").append("<tr>" +
                       "<td>"+logtime+"</td>" +
                       "<td>"+logcity+"</td>" +
                       "<td>"+logip+"</td>" +
                       "<td>"+logsystem+"</td></tr>");
                });
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取登录记录，请稍后重试");
                console.log(xhr);
            }
        });
    };
    async_getlog();




    //------------------------------------签到打卡的设置-----------------------------
    /**
     * 打卡日期选择
     */
    $.datetimepicker.setLocale('zh');

    $('#datetimepicker3').datetimepicker({
        inline:true
    });

    /**
     * 打开签到打卡的编辑框
     */
    var open_usersign = function(){

        var status = $(this).data("status");
        if(status == "ed"){
            $(".signed").css("opacity",0).css("top","63%").animate({
                opacity: 1,
                top: "60%"
            }).delay(1000).animate({
                opacity: 0
            }).css("top", "63%");
            return ;
        }

        var height = $(document).height();
        $(".zhezhao").css("height",height+"px").css("display","block");
        $("#usersign").css("display","block").animate({
            opacity: 1
        },200);
    };
    $(".infobox .signdiv header a").click(open_usersign);

    /**
     * 关闭签到打卡的编辑框
     */
    var close_usersign = function(){

        $(".modinfo").animate({
            opacity: 0
        },200);
        $(".modinfo").delay(200).css("display","none");
        $(".zhezhao").delay(200).css("display","none");
    };
    $("#usersign .main a.close").click(close_usersign);
    $(".zhezhao").click(close_usersign);

    /**
     * 异步获取当月打卡数据
     */
    var asyc_getusersign = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/usersign_con/getmysign",
            dataType: "json",
            success: function(data){
                var date = data.date;
                //进行签到标记
                $(".signdiv .signmain table tbody tr td").each(function(){
                   var tdate = ","+$(this).data("date")+",";
                   if(date.indexOf(tdate) != -1){
                       $(this).css("background-color", "#ff8000").css("color", "white")
                           .css("box-shadow", "none");
                   }
                });
                //判断今天是否需要签到
                var now = new Date();
                var ndate = ","+now.getDate()+",";
                if(date.indexOf(ndate) != -1){
                    $(".signdiv header a").data("status","ed").text("今日已签到");
                    $("#usersign").remove();
                }
                else{
                    $(".signdiv header a").data("status","none");
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取签到数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    window.setTimeout(asyc_getusersign,200);

    /**
     * 点击打卡事件的实现
     */
    var sub_usersign = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/usersign_con/addusersign",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = "/forward_con/gologin";
                }
                else if(status === "mysqlerr"){
                    window.alert("后台数据库异常导致无法进行签到，请稍后重试");
                }
                else{
                    window.alert("签到成功");
                    asyc_getusersign();
                }
                $("#usersign .main a.close").trigger("click");
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法进行签到，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    $("#usersign .sublink li a").click(sub_usersign);




    //-------------------------------------------------侧边栏的点击事件-----------------------------------------------
    /**
     * 左侧导航栏按钮的点击事件
     */
    var clibtn_bind = function(){

        if($(this).closest("li").hasClass("active")){
            return ;
        }
        $(".top-nav nav.stroke li.active a").addClass("hvr-underline-from-left");
        $(this).removeClass("hvr-underline-from-left");
        $(".top-nav nav.stroke li").removeClass("active");
        $(this).closest("li").addClass("active");
        var clitype = $(this).data("type");

        $(".banner-body-right .box").animate({
            opacity: 0
        },50).delay(100).css("display","none");
        //基本信息
        if(clitype === "info"){
            $(".infobox .infodiv").css("display","block").animate({
                opacity: 1
            },50);
        }
        //账号绑定
        else if(clitype === "bind"){
            $(".infobox .binddiv").css("display","block").animate({
                opacity: 1,
                position: "absolute",
                top: 0
            },50);
        }
        //登录记录
        else if(clitype === "log"){
            $(".infobox .logdiv").css("display","block").animate({
                opacity: 1,
                position: "absolute",
                top: 0
            },50);
        }
        //签到打卡
        else if(clitype === "sign"){
            $(".infobox .signdiv").css("display","block").animate({
                opacity: 1,
                position: "absolute",
                top: 0
            },50);
        }
    };
    $(".top-nav nav.stroke a").click(clibtn_bind);





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