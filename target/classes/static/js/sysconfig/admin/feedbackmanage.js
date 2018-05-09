$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param.trim() == "" || param == "null");
    };

    /**
     * 初始化orderTable表格控件并获取评论列表数据
     */
    var load_getorderlist = function () {

        $('#orderTable').bootstrapTable({
            url: "/userfeedback_con/getuserfeedback.json",
            dataType: "json",
            undefinedText: "",
            pagination: true,
            paginationLoop: true,//设置为 true 启用分页条无限循环的功能。
            showToggle: "true",//是否显示 切换试图（table/card）按钮
            showColumns: "true",//是否显示 内容列下拉框
            striped: true,
            pageNumber: 1,
            showRefresh: true,
            singleSelect: false,
            pageSize: 10,
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            data_local: "zh-US",
            uniqueId: "serviceId",
            sidePagination: "server",
            queryParams: function (params) {
                return {
                    pageNo: params.offset / params.limit,
                    pageSize: params.limit,
                    courseName: $("#pageorder form .username").val(),
                    status: $("#pageorder form .respStatus").val()
                };
            },
            idField: 'orderCode',//指定主键列
            columns: [
                {
                    title: '用户名',
                    field: 'username',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '反馈信息',
                    field: 'info',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '发布时间',
                    field: 'ptime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '处理状态',
                    field: 'status',
                    align: 'center',
                    valign: 'middle',
                    formatter: function(value, row, index){
                        if(value == 0){
                            return "未处理";
                        }else if(value == 1){
                            return "已处理";
                        }else{
                            return "用户已删除";
                        }
                    }
                },
                {
                    title: '操作',
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var orderCode = value;
                        return "<button class='btn-primary btn btn-errdear' data-toggle='modal' data-target='#errDear' data-id='" + orderCode + "'>反馈处理</button>&nbsp;&nbsp;" +
                            "<button class='btn btn-danger btn-delete' data-id='"+orderCode+"'>删除</button>&nbsp;&nbsp;";
                    }
                }
            ]
        });
    };
    load_getorderlist();

    /**
     * 查询指定课程的订单数据
     */
    var click_selectorderlist = function(){

        $.ajax({
            type: "post",
            url: "/userfeedback_con/getuserfeedback.json",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                "courseName": $("#pageorder form .username").val(),
                "status": $("#pageorder form .respStatus").val()
            }),
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                $("#orderTable").bootstrapTable('load', data);
            }
        });
    };
    $("#pageorder form").submit(click_selectorderlist);

    /**
     * 删除指定反馈数据
     */
    var click_removeuserfeedback = function(){
        var id = $(this).data("id");
        if(confirm("确定删除该反馈吗？")){
            $.ajax({
                url: "/userfeedback_con/removeuserfeedback.json",
                data: {
                    id: id
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        alert("删除成功");
                        $("button[name=refresh]").click();
                    }
                }
            });
        }
    };
    $(document).on("click", "#orderTable .btn-delete", click_removeuserfeedback);

    /**
     * 打开处理反馈，渲染id到statusMod上
     */
    var click_openerrdear = function(){

        var id = $(this).data("id");
        $("#errDear").data("id", id);
        var reqUser = $(this).closest("tr").find("td:nth-child(1)").text();
        $("#errDear").data("username", reqUser);
    };
    $(document).on("click", "#pageorder .btn-errdear", click_openerrdear);

    /**
     * 保存修改对应的状态
     */
    var click_modreqstatus = function(){

        var id = $("#errDear").data("id");
        var status = $(".newStatus").val();

        $.ajax({
            type: "post",
            url: "/userfeedback_con/moduserfeedbackstatus.json",
            data: JSON.stringify({
                "pageNo": id,
                "status": status
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("修改成功");
                    $("#errDear").modal("hide");
                    $("button[name=refresh]").click();
                }
            }
        });
    };
    $("#errDear button.btn-saveMod").click(click_modreqstatus);

    /**
     * 点击发送邮件
     */
    var click_opensendmailpage = function(){

        window.open("/adminpage_con/sendmailpage?username="+$("#errDear").data("username"));
    };
    $("#errDear .link-sendmail").click(click_opensendmailpage);

    /**
     * 点击发送通知
     */
    var click_opensendmessagepage = function(){

        window.open("/adminpage_con/sendmessagepage?username="+$("#errDear").data("username"));
    };
    $("#errDear .link-sendmessage").click(click_opensendmessagepage);
});