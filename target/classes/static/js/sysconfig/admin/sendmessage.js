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
    var async_loadeditormd = function () {

        testEditor = editormd("my-editormd", {//注意1：这里的就是上面的DIV的id属性值
            width: "90%",
            height: 600,
            syncScrolling: "single",
            path: "../../editor-md/lib/",//注意2：你的路径
            tex: true,
            placeholder: "在这里输入通知内容...",
            flowChart: true,
            sequenceDiagram: true,
            saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
        });
    };
    async_loadeditormd();

    /**
     * 通知方式的切换
     */
    var change_identity = function(){

        var identity = $(this).val();
        if(identity == 1){
            $(".userdiv").css("display", "block");
        }else{
            $(".userdiv").css("display", "none");
        }
    };
    $(".editor-header .identity").change(change_identity);

    /**
     * 点击发送通知
     */
    var click_sendmessage = function(){

        var identity = $(".editor-header .identity").val();
        var username = $(".editor-header .username").val();
        var title = $(".editor-header .title").val();
        var message = $("#my-editormd-markdown-doc").text();

        $.ajax({
            type: "post",
            url: "/usermessage_con/sendmessage",
            data: JSON.stringify({
                "id": identity,
                "send": username,
                "theme": title,
                "email": message
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("发送成功");
                }else if(status == "nouser"){
                    alert("用户不存在");
                }else if(status == "sqlerr"){
                    alert("数据库环境异常，请稍后再试");
                }else if(status = "titleexist"){
                    alert("标题已使用");
                }
            }
        });
    };
    $(".editor-footer .btn-send").click(click_sendmessage);

});