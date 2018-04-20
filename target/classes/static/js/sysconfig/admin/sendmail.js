$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param.trim() == "" || param == "null");
    };

    /**
     * 初始化markdown插件
     */
    var async_loadeditormd = function () {

        editormd("my-editormd", {//注意1：这里的就是上面的DIV的id属性值
            width: "90%",
            height: 400,
            syncScrolling: "single",
            path: "../../editor-md/lib/",//注意2：你的路径
            tex: true,
            flowChart: true,
            sequenceDiagram: true,
            saveHTMLToTextarea: true//注意3：这个配置，方便post提交表单
        });
    };
    async_loadeditormd();

    /**
     * 点击发送邮件
     */
    var click_sendmail = function(){

        var sender = $(".editor-header .sender").val();
        var theme = $(".editor-header .theme").val();
        var email = $("#my-editormd-markdown-doc").text();
        var formatEmail = $(".markdown-body").html();

        $.ajax({
            type: "post",
            url: "/sysemailmanage_con/sendemail",
            data: JSON.stringify({
                send: sender,
                theme: theme,
                email: email,
                formatEmail: formatEmail
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "noaddress"){
                    alert("该用户并未注册邮箱");
                }else if(status == "sendok"){
                    alert("发送成功");
                    $(".editor-header .sender").val("");
                    $(".editor-header .theme").val("");
                    $("#my-editormd-markdown-doc").html("");
                }
            }
        });
    };
    $(".editor-footer button.btn-send").click(click_sendmail);

    /**
     * 保存邮件
     */
    var click_savemail = function(){

        var sender = $(".editor-header .sender").val();
        var theme = $(".editor-header .theme").val();
        var email = $("#my-editormd-markdown-doc").text();
        var formatEmail = $(".markdown-body").html();

        $.ajax({
            type: "post",
            url: "/sysemailmanage_con/sendemail",
            data: JSON.stringify({
                send: sender,
                theme: theme,
                email: email,
                formatEmail: formatEmail
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "noaddress"){
                    alert("该用户并未注册邮箱");
                }else if(status == "sendok"){
                    alert("发送成功");
                    $(".editor-header .sender").val("");
                    $(".editor-header .theme").val("");
                    $("#my-editormd-markdown-doc").html("");
                }
            }
        });
    };
    $(".editor-footer button.btn-save").click(click_savemail);
});