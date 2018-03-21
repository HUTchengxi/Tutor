$(function() {

    /**
     * 获取家教的用户名和imgsrc
     */
    var async_gettutorinfo = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/getuserinfo",
            dataType: "json",
            success: function(data) {
                $(".layui-layout-right img.layui-nav-img").attr("src", data.imgsrc);
                $(".layui-layout-right span.author").text(data.nickname);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户数据，请稍后再试");
            }
        })
    };
    async_gettutorinfo();

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
                    alert("退出成功");
                    window.location = "/forward_con/welcome";
                },
                error: function(xhr, status){
                    alert("后台环境异常导致无法正确退出登录，请刷新页面重试");
                }
            });
        }
        $(this).closest("dd").removeClass("layui-this");
    };
    $(document).on("click", ".layui-layout-right dl dd a.logoff", logoff_btn);
});