;(function () {

    /**
     * 用户名输入框失去焦点时自动判断
     *  1: 用户名是否已被注册
     *  2：用户名是否为空
     *  3：用户名长度不能大于20
     */
    var register_user_blur = function () {

        var username = $("#username").val();
        var len = username.length;
        //用户名为空
        if (username.trim() == "") {
            $(".div-cls-userinfo").data("status", 1).addClass("alert-danger")
                .removeClass("alert-success").text("用户名不能为空").parent().animate({
                height: "50px"
            }, 100);
        }
        //用户名长度
        else if (len > 20) {
            $(".div-cls-userinfo").data("status", 1).addClass("alert-danger")
                .removeClass("alert-success").text("用户名长度不能超过20位").parent().animate({
                height: "50px"
            }, 100);
        }
        else {
            //判断用户名是否已存在
            $.ajax({
                async: true,
                type: "post",
                url: "/register_con/check_exist_user",
                data: {username: username},
                dataType: "json",
                success: function (data) {
                    var status = data.status;
                    //用户名已存在
                    if (status == "invalid") {
                        $(".div-cls-userinfo").data("status", 2).addClass("alert-danger")
                            .removeClass("alert-success").text("用户名已存在").parent().animate({
                            height: "50px"
                        }, 200);
                    }
                    else {
                        $(".div-cls-userinfo").text("可以注册").removeClass("alert-danger")
                            .addClass("alert-success").parent().animate({
                            height: "50px"
                        }, 100);
                        window.setTimeout(function () {
                            $(".div-cls-userinfo").data("status", 10).parent().animate({
                                height: "0"
                            }, 100);
                        }, 2000);
                    }
                },
                error: function (xhr, status) {
                    alert("服务器环境异常");
                }
            });
        }
    };
    $("#username").blur(register_user_blur);

    /**
     * 第一次密码输入框失去焦点时自动判断：
     *  1：密码是否为空
     *  2：密码长度是否大于六位小于等于十二位
     */
    var register_pass_blur = function () {

        var password = $("#password").val();
        var len = password.length;
        //密码为空
        if (password.trim() == "") {
            $(".div-cls-passinfo").data("status", 1).addClass("alert-danger")
                .removeClass("alert-success").text("密码不能为空").parent().animate({
                height: "50px"
            }, 100);
        }
        //密码长度
        else if (len < 6) {
            $(".div-cls-passinfo").data("status", 2).addClass("alert-danger")
                .removeClass("alert-success").text("密码长度应大于等于6位").parent().animate({
                height: "50px"
            }, 100);
        }
        else if (len > 12) {
            $(".div-cls-passinfo").data("status", 2).addClass("alert-danger")
                .removeClass("alert-success").text("密码长度应小于等于12位").parent().animate({
                height: "50px"
            }, 100);
        }
        else {
            $(".div-cls-passinfo").text("可以使用").removeClass("alert-danger")
                .addClass("alert-success").parent().animate({
                height: "50px"
            }, 100);
            window.setTimeout(function () {
                $(".div-cls-passinfo").data("status", 10).parent().animate({
                    height: "0"
                }, 100);
            }, 2000);
        }
    };
    $("#password").blur(register_pass_blur);

    /**
     * 密码二次确认输入框失去焦点时自动判断：
     *  1：两次密码不相同
     *  2：密码输入是否为空
     *  3: 密码长度为6-12
     */
    var register_repass_blur = function () {

        var repassword = $("#re-password").val();
        var password = $("#password").val();
        var len = repassword.length;
        //两次密码不相同
        if (!(password === repassword)) {
            $(".div-cls-repassinfo").data("status", 1).addClass("alert-danger")
                .removeClass("alert-success").text("两次密码不相同").parent().animate({
                height: "50px"
            }, 100);
        }
        //密码为空
        else if (repassword.trim() == "") {
            $(".div-cls-repassinfo").data("status", 2).addClass("alert-danger")
                .removeClass("alert-success").text("密码不能为空").parent().animate({
                height: "50px"
            }, 100);
        }
        //密码长度
        else if (len < 6) {
            $(".div-cls-repassinfo").data("status", 2).addClass("alert-danger")
                .removeClass("alert-success").text("密码长度应大于等于6位").parent().animate({
                height: "50px"
            }, 100);
        }
        else if (len > 12) {
            $(".div-cls-repassinfo").data("status", 2).addClass("alert-danger")
                .removeClass("alert-success").text("密码长度应小于等于12位").parent().animate({
                height: "50px"
            }, 100);
        }
        else {
            $(".div-cls-repassinfo").text("可以使用").removeClass("alert-danger")
                .addClass("alert-success").parent().animate({
                height: "50px"
            }, 100);
            window.setTimeout(function () {
                $(".div-cls-repassinfo").data("status", 10).parent().animate({
                    height: "0"
                }, 100);
            }, 2000);
        }
    };
    $("#re-password").blur(register_repass_blur);

    /**
     * 校验方式选择输入框失去焦点时自动判断
     *  1: 是否选择了校验方式
     */
    var register_check_blur = function () {

        var checktype = $("#checktype").val();
        $("#p-id-err").css("display", "none").text("");
        //没有选择
        if (checktype === "null") {
            $(".div-cls-checkinfo").data("status", 1).addClass("alert-danger")
                .removeClass("alert-success").text("请选择校验方式").parent().animate({
                height: "50px"
            }, 100);
            $(".telephone-group").animate({
                minHeight: "0px"
            }, 100);
            $(".email-group").animate({
                minHeight: "0px"
            }, 100);
        }
        else {
            $(".div-cls-checkinfo").data("status", 10).parent().animate({
                height: "0"
            }, 100);

            //手机验证
            if (checktype === "telephone") {
                //这里不能设置height，因为高度时自定义的，有错误提示没包含进来，只能设置minheight来达到同样的效果
                $(".email-group").animate({
                    minHeight: "0"
                }, 100);
                $(".telephone-group").animate({
                    minHeight: "165px"
                }, 300);
            }
            else if (checktype === "email") {
                //这里设置overflow是因为错误提示要下来
                $(".email-group").animate({
                    minHeight: "165px"
                }, 300);
                $(".telephone-group").animate({
                    minHeight: "0px"
                }, 100);
            }
            else {
                $(".telephone-group").animate({
                    minHeight: "0px"
                }, 100);
                $(".email-group").animate({
                    minHeight: "0px"
                }, 100);
            }
        }

    };
    $("#checktype").blur(register_check_blur);
    $("#checktype").change(register_check_blur);

    /**
     * 验证码失去焦点的校验
     */
    // var register_valicode_blur = function(){
    //
    //     var valicode = $(this).val();
    //     if(valicode.length != 4){
    //         $("#p-id-err").css("display", "block").text("请认真填写");
    //         return ;
    //     }
    // };
    // $("#valicode").blur(register_valicode_blur);

    /**
     * 点击发送手机验证短码
     */
    var cli_sendphonecode = function(){

        if($(this).data("status") == "ing"){
            return ;
        }

        var phone = $("#telephone").val();
        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/register_sendbindcode",
            data: {phone: phone},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "exist"){
                    $("#p-id-err").css("display", "block").text("号码已被注册");
                    return ;
                }
                else{
                    window.alert("发送成功");
                    var interval;
                    var time = 60;
                    //邮件验证码发送成功的冷却事件
                    $(".btn-cls-sendcode").data("status", "ing");
                    var interval = window.setInterval(function(){
                        if(time == 0){
                            $(".btn-cls-sendcode").data("status", "ed").text("获取验证码");
                            window.clearInterval(interval);
                            return;
                        }
                        $(".btn-cls-sendcode").text("还有"+time+"s可重发");
                        time--;
                    }, 1000);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法发送手机验证短码，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".btn-cls-sendcode").click(cli_sendphonecode);

    /**
     * 简单实现暂不验证的注册
     */
    var register_submit = function () {

        var checktype = $("#checktype").val();
        $("#username").trigger("blur");
        $("#password").trigger("blur");
        $("#re-password").trigger("blur");
        $("#checktype").trigger("blur");

        var userid = $(".div-cls-userinfo").data("status");
        var passid = $(".div-cls-passinfo").data("status");
        var repassid = $(".div-cls-repassinfo").data("status");
        var checkid = $(".div-cls-checkinfo").data("status");


        if (userid != 10 || passid != 10 || repassid != 10 || checkid != 10) {
            $("#p-id-err").css("display", "block");
            return;
        }
        $("#p-id-err").css("display", "none");

        var username = $("#username").val();
        var password = $("#password").val();
        var email = $("#email").val();
        var telephone = $("#telephone").val();
        var phonecode = $("#valicode").val();

        if(checktype == "telephone") {
            $("#valicode").trigger("blur");
        }

        $.ajax({
            async: true,
            type: "post",
            url: "/register_con/register_main",
            data: {username: username, password: password, checktype: checktype, email: email, telephone: telephone, phonecode: phonecode},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                var url = data.url;
                console.log(status);
                if (status === "invalid") {
                    alert("后台数据库环境异常，请重新注册");
                    return;
                }
                else if(status == "exist"){
                    alert("该邮箱已经被注册过了");
                    return false;
                }
                else if(status == "codeerr"){
                    $("#p-id-err").css("display", "block").text("验证码不正确");
                    return ;
                }
                window.alert("注册成功");
                window.location.href = url;
            },
            error: function (xhr, status) {
                alert("服务器环境异常");
                console.log(xhr);
            }
        });
    };
    $(".btn-cls-submit").click(register_submit);
}());