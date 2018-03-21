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
    var ident = -1;
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
                    ident = data.ident;
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












    //-----------------------------------------------课程记录的设置----------------------------------------
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
     * 异步获取我的课程记录
     */
    var async_getlog = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/courselog_con/getlog",
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
                    var id = item.id;
                    var logtime = item.logtime;
                    var ctype = item.ctype;
                    var cname = item.cname;
                    $(".infobox .logdiv table tbody").append("<tr>" +
                        "<td>"+logtime+"</td>" +
                        "<td>"+ctype+"</td>" +
                        "<td><a href='javascript:void(0);' data-gid='"+id+"' title='"+cname+"'>"+cname+"</a></td>" +
                        "<td><a href='javascript:void(0);' data-did='"+id+"'>删除</a></td></tr>");
                });
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取登录记录，请稍后重试");
                console.log(xhr);
            }
        });
    };
    async_getlog();

    /**
     * 删除我的课程记录
     */
    var del_courselog = function(){

        var did = $(this).data("did");
        if(window.confirm("确认删除该记录？")){
            $.ajax({
                async: false,
                type: "post",
                url: "/courselog_con/dellog",
                data: {id: did},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status === "invalid"){
                        window.location = data.url;
                    }
                    else if(status === "mysqlerr"){
                        window.alert("后台数据库异常导致无法删除课程记录，请刷新页面重试");
                        return ;
                    }
                    else{
                        alert("删除成功");
                        window.history.go(0);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法删除课程记录，请稍后重试");
                    console.log(xhr);
                }
            });
        }
    };
    $(document).on("click", ".historydiv table tr td:nth-child(4) a", del_courselog);








    //我的评论
    //课程表的设计
    //用户的手机号码验证码
    //勤成榜
    //七嘴八舌











    //----------------------------------------------------我的收藏的设置--------------------------------------------------
    /**
     * 异步加载我的课程收藏数据
     */
    var async_coursecollect = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecollect_con/getmycollect",
            data: {startpos: 0},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status === "invalid"){
                    window.location = data.url;
                }
                //没有收藏课程的情况
                else if(status === "valid"){
                    $(".collectdiv ul").remove();
                    $(".collectdiv").append("<div class='none'><p>暂未收藏课程</p></div>");
                }
                else{
                    var year = "";
                    var day = "";
                    var count = 0;
                    $.each(data, function(index, item){
                        count++;
                        var cyear = item.cyear;
                        var cid = item.cid;
                        var cimgsrc = item.cimgsrc;
                        var descript = item.descript;
                        var cday = item.cday;
                        var cname = item.cname;
                        var $li = $("<li class='layui-timeline-item'></li>");
                        var $div = $("<div class='layui-timeline-content layui-text'></div>");
                        var $i, $h3, $h4;
                        if(cyear === year){
                            if(cday !== day){
                                $h4 = $("<h4>"+cday+"</h4>");
                                $h3 = $("<h3 class='layui-timeline-title'>&nbsp;</h3>");
                                $i = $("<i class='layui-icon layui-timeline-axis subtitle'>&#xe63f;</i>");
                            }
                            else{
                                $h4 = $("<h4></h4>");
                                $h3 = $("<h3 class='layui-timeline-title'></h3>");
                                $i = $("<i></i>");
                            }
                        }
                        else{
                            $h3 = $("<h3 class='layui-timeline-title'>"+cyear+"</h3>");
                            $h4 = $("<h4 class='layui-timeline-title'>"+cday+"</h4>");
                            $i = $("<i class='layui-icon layui-timeline-axis'>&#xe63f;</i>");
                        }
                        var $alink = $("<a href='javascript:void(0);' data-cid='"+cid+"'><img src='"+cimgsrc+"' /></a>");
                        var $p = $("<p>收藏笔记：<span>"+descript+"</span></p>");

                        $(".collectdiv.box ul").append($li.append($i).append($div.append($h3).append($h4).append("<h5>"+cname+"</h5>").append($alink).append("<a href='javascript:void(0);' data-cid='"+cid+"'>取消收藏</a>").append($p)));
                        year = cyear;
                        day = cday;
                    });
                    if(count >= 3){
                        $(".collectdiv.box ul").append("<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis getmore more' title='点击查看下一页' data-cur='1'>&#xe61a;</i></li>");
                    }
                    else{
                        $(".collectdiv.box ul").append("<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis'>&#xe63f;</i></li>");
                    }
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取我的收藏数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_coursecollect();

    /**
     * 触摸图片显示取消收藏按钮
     */
    var hover_showuncol = function(){
        $(this).parent("a").next("a").animate({
            width: "19%"
        },200);
    };
    $(document).on("mouseenter",".collectdiv ul li img", hover_showuncol);

    /**
     * 鼠标移开隐藏取消收藏按钮
     */
    var leave_hideuncol = function(){
        $(".collectdiv ul li a:nth-child(5)").delay(200).css("width","0");
    };
    $(document).on("mouseleave",".collectdiv ul li img", leave_hideuncol);
    $(document).on("mouseleave",".collectdiv ul li a:nth-child(5)", leave_hideuncol);

    /**
     * 鼠标移到取消收藏按钮上显示
     */
    var hover_showuncol2 = function(){
        $(this).css("width", "19%");
    };
    $(document).on("mouseenter",".collectdiv ul li a:nth-child(5)", hover_showuncol2);

    /**
     * 点击取消收藏按钮取消收藏
     */
    var cli_uncol = function(){

        var cname = $(this).closest("div").find("h5").text();
        if(window.confirm("确定取消收藏课程\""+cname+"\"吗？")){
            var cid = $(this).data("cid");
            var $this = $(this);
            $.ajax({
                async: true,
                type: "post",
                url: "/coursecollect_con/modusercollect",
                data: {cid: cid, mod: "uncollect"},
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status === "mysqlerr"){
                        window.alert("后台数据库异常导致无法取消收藏，请稍后再试");
                    }
                    else if(status === "valid"){
                        window.alert("您已成功取消课程\""+cname+"\"的收藏");
                        window.history.go(0);
                    }
                },
                error: function(xhr, status){
                    window.alert("后台环境异常导致无法取消收藏，请稍后再试");
                    window.console.log(xhr);
                }
            });
        }
    };
    $(document).on("click", ".collectdiv ul li a:nth-child(5)", cli_uncol);







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
        //课程记录
        if(clitype === "history"){
            $(".infobox .historydiv").css("display","block").animate({
                opacity: 1
            },50);
        }
        //我的评论
        else if(clitype === "command"){
            $(".infobox .commanddiv").css("display","block").animate({
                opacity: 1
            },50);
        }
        //我的收藏
        else if(clitype === "collect"){
            $(".infobox .collectdiv").css("display","block").animate({
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