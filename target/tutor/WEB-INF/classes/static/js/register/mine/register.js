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
     * 简单实现暂不验证的注册
     */
    var register_submit = function () {

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
        var checktype = $("#checktype").val();
        var email = $("#email").val();
        var telephone = $("#telephone").val();

        $.ajax({
            async: true,
            type: "post",
            url: "/register_con/register_main",
            data: {username: username, password: password, checktype: checktype, email: email, telephone: telephone},
            dataType: "json",
            success: function (data) {
                var status = data.status;
                var url = data.url;
                if (status === "invalid") {
                    alert("后台数据库环境异常，请重新注册");
                    return;
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