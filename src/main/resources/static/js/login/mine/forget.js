;(function () {

    /**
     * 找回密码的方式的选择
     */
    var cli_modchange = function(){

        if($(this).closest("li").hasClass("cli")){
            return;
        }
        $(".err").css("display", "none");
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
                    $.ajax({
                        async: true,
                        type: "post",
                        url: "/usersecret_con/getsecretinfo",
                        data: {username: username},
                        dataType: "json",
                        success: function(data){
                            var status = data.status;
                            if(status == "invalid"){
                                return;
                            }
                            else if(status == "valid"){
                                $(".container .mainshow .main-secret").empty().append("<p class=\"none\">您暂未设置密保</p>");
                                $(".container nav ul li a.bysecret").data("hg", "80");
                                return ;
                            }
                            else{
                                var cnt = 1;
                                $.each(data, function(index, item){
                                    var question = item.question;
                                    $(".container .mainshow .main-secret form input.mb"+index)
                                        .attr("disabled", "disabled").val(question);
                                    cnt = index;
                                });

                                //定制化错误标签的位置
                                if(cnt == 1){
                                    $(".err-mb1").css("top", "23%");
                                    $(".err-newpass").css("top", "43%");
                                    $(".err-repass").css("top", "62%");
                                    $("nav ul li a.bysecret").data("hg", "230");
                                }
                                else if(cnt == 2){
                                    $(".err-mb1").css("top", "16%");
                                    $(".err-mb2").css("top", "42%");
                                    $(".err-newpass").css("top", "56%");
                                    $(".err-repass").css("top", "70%");
                                    $("nav ul li a.bysecret").data("hg", "330");
                                }
                                else{
                                    $(".err-mb1").css("top", "11.5%");
                                    $(".err-mb2").css("top", "31.5%");
                                    $(".err-mb3").css("top", "51.5%");
                                    $(".err-newpass").css("top", "61.5%");
                                    $(".err-repass").css("top", "72%");
                                    $("nav ul li a.bysecret").data("hg", "410");
                                }

                                //清除多余的错误提示标签
                                for(var i=3; i!=cnt; i--){
                                    $(".err-mb"+i+"").remove();
                                }

                                cnt *= 2;
                                //清除多余的input
                                $(".container .mainshow .main-secret input").each(function(){
                                    if(!$(this).hasClass("password") && $(this).val().trim() == "" && cnt == 0){
                                        $(this).prev().remove();
                                        $(this).next().remove();
                                        $(this).remove();
                                    }
                                    else{
                                        cnt--;
                                    }
                                });
                            }
                        },
                        error: function(xhr, status){
                            window.alert("后台环境异常导致无法获取密保数据，请稍后再试");
                            window.console.log(xhr);
                        }
                    });
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
        $(".err").css("display", "none");
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

        if(email == null || email.trim() == ""){
            $(".err-email").css("display", "block").text("邮箱不能为空");
            return ;
        }

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
                    $(".err-email").css("display", "block").text("邮箱不对应");
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
     * 手机验证码发送的点击事件
     */
    var findpass_byphone = function(){

        var phone = $(".container .mainshow .main-phone input.phone").val();
        var username = $(".usercheck input").val();

        if(phone == null || phone.trim() == ""){
            $(".err-phone").css("display", "block").text("手机不能为空");
            return ;
        }

        var status = $(this).data("status");
        if(status == "ing"){
            return ;
        }

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/sendunbindphone",
            data: {phone: phone, username: username},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    $(".err-phone").css("display", "block").text("手机不对应");
                    return ;
                }
                else{
                    $(".err-phone").css("display", "none");
                    window.alert("验证码发送成功");
                    var interval;
                    var time = 60;
                    //邮件验证码发送成功的冷却事件
                    $(".container .mainshow .main-phone a").data("status", "ing");
                    var interval = window.setInterval(function(){
                        if(time == 0){
                            $(".container .mainshow .main-phone a").data("status", "ed").text("获取验证码");
                            window.clearInterval(interval);
                            return;
                        }
                        $(".container .mainshow .main-phone a").text("还有"+time+"s可重发");
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
    $(".container .mainshow .main-phone a.resend").click(findpass_byphone);


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

    /**
     * 手机验证的方式点击提交按钮进行密码更改的提交
     */
    var cli_findpassbyphone = function(){

        var valicode = $(".container .main-phone .valicode").val();
        var username = $(".usercheck .username").val();
        var email = $(".container .main-phone .phone").val();
        var newpass = $(".container .main-phone .newpass").val();
        var repass = $(".container .main-phone .repass").val();

        //进行密码修改
        $.ajax({
            async: true,
            type:　"post",
            url: "/usermain_con/forget_modpass",
            data: {username: username, phone: email, valicode: valicode, newpass: newpass, repass: repass},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "inerr"){
                    $(".container .main-phone .resend").trigger("click");
                    $(".container .main-phone .newpass").trigger("blur");
                    $(".container .main-phone .repass").trigger("blur");
                    return ;
                }
                else if(status == "invalid"){
                    $(".container .main-phone .err-phone").css("display", "block").text("手机不对应");
                    $(".container .main-phone .newpass").trigger("blur");
                    $(".container .main-phone .repass").trigger("blur");
                    return ;
                }
                else{
                    window.alert("修改成功");
                    window.location = "/forward_con/gologin";
                }
            },
            erorr: function(xhr, status){
                window.alert("后台环境异常导致无法修改用户密码，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".container .main-phone form button").click(cli_findpassbyphone);

    /**
     *  邮箱找回的方式点击提交按钮进行密码更改的提交
     */
    var cli_findpassbyemail = function(){

        var valicode = $(".container .main-email .valicode").val();
        var username = $(".usercheck .username").val();
        var email = $(".container .main-email .email").val();
        var newpass = $(".container .main-email .newpass").val();
        var repass = $(".container .main-email .repass").val();

        //进行密码修改
        $.ajax({
            async: true,
            type:　"post",
            url: "/usermain_con/forget_modpass",
            data: {username: username, email: email, valicode: valicode, newpass: newpass, repass: repass},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "inerr"){
                    $(".container .main-email .resend").trigger("click");
                    $(".container .main-email .newpass").trigger("blur");
                    $(".container .main-email .repass").trigger("blur");
                    return ;
                }
                else if(status == "invalid"){
                    $(".container .main-email .err-email").css("display", "block").text("邮箱不存在");
                    $(".container .main-email .newpass").trigger("blur");
                    $(".container .main-email .repass").trigger("blur");
                    return ;
                }
                else{
                    window.alert("修改成功");
                    window.location = "/forward_con/gologin";
                }
            },
            erorr: function(xhr, status){
                window.alert("后台环境异常导致无法修改用户密码，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".container .main-email form button").click(cli_findpassbyemail);

    /**
     * 密保找回的方式点击提交按钮进行密码更改的提交
     */
    var cli_findpassbysecret = function(){

        $(".err").css("display", "none");

        var ansone = $(".container .main-secret form .mb1-answer").val();
        if(ansone.trim() == ""){
            $(".err-mb1").css("display", "block").text("答案不能为空");
            return ;
        }

        var anstwo = null;
        if($(".container .main-secret form input").hasClass("mb2-answer")){
            anstwo = $(".container .main-secret form .mb2-answer").val();
            if(anstwo.trim() == ""){
                $(".err-mb2").css("display", "block").text("答案不能为空");
                return ;
            }
        }

        var ansthree = null;
        if($(".container .main-secret form input").hasClass("mb3-answer")){
            ansthree = $(".container .main-secret form .mb3-answer").val();
            if(ansthree.trim() == ""){
                $(".err-mb3").css("display", "block").text("答案不能为空");
                return ;
            }
        }

        var newpass = $(".container .main-secret form input.newpass").val();
        if(newpass.trim() == ""){
            $(".main-secret .err-newpass").css("display", "block").text("密码不能为空");
            return ;
        }
        if(newpass.length > 12){
            $(".main-secret .err-newpass").css("display", "block").text("长度不能超过12位");
            return ;
        }
        if(newpass.length < 6){
            $(".main-secret .err-newpass").css("display", "block").text("长度不能低于6位");
            return ;
        }

        var repass = $(".container .main-secret form input.repass").val();
        if(repass.trim() == ""){
            $(".main-secret .err-repass").css("display", "block").text("密码不能为空");
            return ;
        }
        if(repass != newpass){
            $(".main-secret .err-repass").css("display", "block").text("两次密码不相等");
            return ;
        }

        var username = $(".usercheck .username").val();
        var queone = $(".container .main-secret .mb1").val();
        var quetwo = $(".container .main-secret .mb2").val();
        var quethree = $(".container .main-secret .mb3").val();

        //进行更新提交
        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/forget_modpassbysecret",
            data: {queone: queone, ansone: ansone, quetwo: quetwo, anstwo: anstwo, quethree: quethree, ansthree: ansthree, password: newpass, username: username},
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status != "ok"){
                    $(".container ."+status).css("display", "block").text("答案不正确");
                    return ;
                }
                window.alert("修改成功");
                window.location = "/forward_con/gologin";
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法进行密保密码找回，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    $(".container .main-secret form button").click(cli_findpassbysecret);

}());