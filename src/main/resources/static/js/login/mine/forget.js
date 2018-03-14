;(function () {

    /**
     * 找回密码的方式的选择
     */
    var cli_modchange = function(){

        if($(this).closest("li").hasClass("cli")){
            return;
        }
        $("nav ul li.cli").removeClass("cli");
        $(this).closest("li").addClass("cli");

        $(".container .mainshow .main").animate({
            height: 0
        },200);
        var cls = $(this).data("cls");
        var hg = $(this).data("hg");
        $(".container .mainshow ."+cls).animate({
            height: hg+"px"
        },200);

    };
    $("nav ul li a").click(cli_modchange);

    /**
     * 输入用户名之后点击找回密码的按钮事件
     */
    var cli_findpassbyuser = function(){

        var username = $(".usercheck input").val();
        if(username.trim() == "" || username == null){
            $(".usercheck p").css("display", "block").text("请填写用户名");
            return ;
        }
        $.ajax({
            async: true,
            type: "post",
            url: "/register_con/check_exist_user",
            data: {username: username},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                //用户名不存在
                if (status !== "invalid") {
                    $(".usercheck p").css("display", "block").text("用户名不存在");
                }
                else {
                    $(".usercheck p").css("display", "none");
                    $(".zhezhao").css("height", $(window).height()).css("display", "block");
                    $(".container").animate({
                        top: "-80px"
                    },500);

                    //异步获取密保数据
                }
            },
            error: function (xhr, status) {
                alert("服务器环境异常");
            }
        });
    };
    $(".usercheck a").click(cli_findpassbyuser);

    /**
     * 点击关闭按钮进行悬浮框关闭
     */
    var cli_closecontainer = function(){

        $(".container").animate({
            top: "-700px"
        },500);
        $(".zhezhao").css("display", "none");
    };
    $(".container a.close").click(cli_closecontainer);

    /**
     * 遮罩层的点击事件同样关闭悬浮框
     */
    $(".zhezhao").click(cli_closecontainer);

    /**
     * esc同样关闭悬浮框
     */
    var esc_closecontainer = function(event){

        var code = event.keyCode;
        if(code == 27){
            $(".container a.close").trigger("click");
        }
    };
    $(document).keydown(esc_closecontainer);

    /**
     * 发送邮箱邮件验证码的点击事件
     */
    var findpass_byemail = function(){

        var email = $(".container .mainshow .main-email input.email").val();
        var username = $(".usercheck input").val();

        var status = $(this).data("status");
        if(status == "ing"){
            return ;
        }

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/forget_sendmail",
            data: {email: email, username: username},
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
                    $(".container .mainshow .main-email a").data("status", "ing");
                    var interval = window.setInterval(function(){
                        if(time == 0){
                            $(".container .mainshow .main-email a").data("status", "ed").text("获取验证码");
                            window.clearInterval(interval);
                            return;
                        }
                        $(".container .mainshow .main-email a").text("还有"+time+"s可重发");
                        time--;
                    }, 1000);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法发送邮箱短码，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".container .mainshow .main-email a.resend").click(findpass_byemail);

    /**
     * 第一次密码输入框失去焦点时自动判断：
     *  1：密码是否为空
     *  2：密码长度是否大于六位小于等于十二位
     */
    var register_pass_blur = function () {

        var password = $(this).val();
        var len = password.length;
        //密码为空
        if (password.trim() == "") {
            $(this).closest("div").find("p.err-newpass").text("密码不能为空").css("display", "block");
        }
        //密码长度
        else if (len < 6) {
            $(this).closest("div").find("p.err-newpass").text("密码不能低于6位").css("display", "block");
        }
        else if (len > 12) {
            $(this).closest("div").find("p.err-newpass").text("密码长度不能超过12位").css("display", "block");
        }
        else {
            $(this).closest("div").find("p.err-newpass").css("display", "none");
        }
    };
    $(".container .main .newpass").blur(register_pass_blur);

    /**
     * 密码二次确认输入框失去焦点时自动判断：
     *  1：两次密码不相同
     *  2：密码输入是否为空
     *  3: 密码长度为6-12
     */
    var register_repass_blur = function () {

        var password = $(this).val();
        var len = password.length;
        //密码为空
        if (password.trim() == "") {
            $(this).closest("div").find("p.err-repass").text("密码不能为空").css("display", "block");
        }
        //密码长度
        else {
            var newpass = $(this).closest("div").find(".newpass").val();
            if(newpass === password) {
                $(this).closest("div").find("p.err-repass").css("display", "none");
            }
            else{
                $(this).closest("div").find("p.err-repass").text("两次输入密码不一致").css("display", "block");
            }
        }
    };
    $(".container .main .repass").blur(register_repass_blur);

}());