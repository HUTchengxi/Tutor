$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param.trim() == "" || param == "null");
    };

    /**
     * 点击登录
     */
    var click_checklogin = function(){

        var username = $("#username").val();
        var password = $("#password").val();

        $.ajax({
            type: "post",
            url: "/admin_con/login.json",
            data: {
                username: username,
                password: password
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "error"){
                    alert("用户名或密码错误");
                }
                else{
                    alert("登录成功");
                    window.location = data.url;
                }
            }
        });
    };
    $("form").submit(click_checklogin);
});