;(function () {

    /**
     * 点击重发进行邮件重发
     */
    var cli_resend = function () {

        $.ajax({
            async: true,
            type: "post",
            url: "/register_con/register_resendemail",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    window.location = "/forward_con/welcome";
                }
                else{
                    window.alert("邮件重发成功");
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法重发邮件，请稍后再式");
                window.console.log(xhr);
            }
        })
    };
    $(".resend").click(cli_resend);
}());