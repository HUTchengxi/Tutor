$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param == "" || param == "null");
    };

    /**
     * 初始化markdown插件
     */
    var testEditor = null;
    var async_loadeditormd = function () {

        testEditor = editormd("my-editormd", {//注意1：这里的就是上面的DIV的id属性值
            width: "90%",
            height: 400,
            syncScrolling: "single",
            path: "../../editor-md/lib/",//注意2：你的路径
            placeholder: "在这里输入通知内容...",
            tex: true,
            flowChart: true,
            sequenceDiagram: true,
            saveHTMLToTextarea: true //注意3：这个配置，方便post提交表单
        });
    };
    async_loadeditormd();

    /**
     * 通知方式的选择切换
     */
    var click_identityChange = function(){

        var identity = $(this).val();
        $(".userdiv").css("display", "none");
        $(".userdiv .username").val("");
        if(identity == 1){
            $(".userdiv").css("display", "block");
        }
    };
    $(".editor-header .identity").change(click_identityChange);

    /**
     * 点击发送通知
     */
    var click_sendmessage = function(){

        var sender = $(".editor-header .username").val();
        var theme = $(".editor-header .title").val();
        var email = $("#my-editormd-markdown-doc").text();
        var identity = $(".editor-header .identity").val();

        $.ajax({
            type: "post",
            url: "/usermessage_con/sendmessage",
            data: JSON.stringify({
                id: identity,
                send: sender,
                theme: theme,
                email: email
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("发送成功");
                    $(".editor-header .identity").val(0);
                    $(".editor-header .title").val("");
                    $(".editor-header .username").val("");
                }
            }
        });
    };
    $(".editor-footer button.btn-send").click(click_sendmessage);

});