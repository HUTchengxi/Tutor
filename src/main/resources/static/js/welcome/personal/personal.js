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
});