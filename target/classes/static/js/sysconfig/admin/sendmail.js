$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param == "" || param == "null");
    };

    /**
     * 获取url的请求参数
     */
    var str_geturlparam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
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
            tex: true,
            flowChart: true,
            sequenceDiagram: true,
            saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
            onload: function(){
                var id = str_geturlparam("id");
                var $this = this;
                //修改
                if(!str_isnull(id)){
                    $(".emailId").val(id);
                    $.ajax({
                        type: "post",
                        url: "/sysemailmanage_con/getmodinfobyid",
                        data: {
                            id
                        },
                        dataType: "json",
                        success: function(data){
                            var status = data.status;
                            if(status == "valid"){
                                $(".sender").remove();
                                $(".senddiv").append("<span class='form-control sender'>"+data.username+"</span>");
                                $(".theme").val(data.theme);
                                while(str_isnull(testEditor)){}
                                $this.setValue(data.email);
                            }
                        }
                    });
                }
            }
        });
    };
    async_loadeditormd();

    /**
     * 点击发送邮件
     */
    var click_sendmail = function(){

        var sender = $(".editor-header .sender").val();
        if(str_isnull(sender)){
            sender = $(".editor-header .sender").text();
        }
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
                    $(".editor-header .sedner").text("");
                    $(".editor-header .theme").val("");
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
        if(str_isnull(sender)){
            sender = $(".editor-header .sender").text();
        }
        var theme = $(".editor-header .theme").val();
        var email = $("#my-editormd-markdown-doc").text();
        var id = $(".editor-header .emailId").val();

        $.ajax({
            type: "post",
            url: "/sysemailmanage_con/saveemail",
            data: JSON.stringify({
                id: id,
                send: sender,
                theme: theme,
                email: email
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "noaddress"){
                    alert("该用户暂未注册邮箱");
                }else if(status == "valid"){
                    alert("保存成功");
                    var emailId = data.emailId;
                    if(!str_isnull(emailId)){
                        $(".editor-header .emalId").val(emailId);
                    }
                }else{
                    alert("保存失败，请重新保存");
                }
            }
        });
    };
    $(".editor-footer button.btn-save").click(click_savemail);
});