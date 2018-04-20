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
            url: "/sysemailmanage_con/getemaillist",
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
                    emailStatus: $("#pageorder form .emailStatus").val()
                };
            },
            idField: 'emailId',//指定主键列
            columns: [
                {
                    title: '邮件主题',
                    field: 'emailTheme',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '邮件状态',
                    field: 'emailStatus',
                    align: 'center',
                    valign: 'middle',
                    formatter: function(value, row, index){
                        return "<span>"+(value==1?'已发送':'草稿')+"</span>";
                    }
                },
                {
                    title: '更新时间',
                    field: 'updateTime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: 'emailId',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var orderCode = value;
                        return "<button class='btn-link btn btn-more' data-toggle='modal' data-target='#emailMore' data-code='" + orderCode + "'>详情</button>&nbsp;&nbsp;" +
                            "<button class='btn-link btn btn-mod' data-code='" + orderCode + "'>修改</button>&nbsp;&nbsp;" +
                            "<button class='btn btn-link btn-del' style='color: rgba(255,0,0,0.69);' data-code='" + orderCode + "'>删除</button>&nbsp;&nbsp;";
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
            url: "/sysemailmanage_con/getemaillist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                emailStatus: $("#pageorder form .emailStatus").val()
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
     * 查看指定邮件详情
     */
    var click_openemailmore = function(){

        var id = $(this).data("code");
        $.ajax({
            type: "post",
            url: "/sysemailmanage_con/getemaildetail",
            data: {
                id: id
            },
            dataType: "json",
            success: function(data){
                $.each(data, function(index, item){
                    $("#emailMore span."+index).text(item);
                });
            }
        });
    };
    $(document).on("click", "#pageorder button.btn-more", click_openemailmore);

    /**
     * 点击删除指定邮件
     */
    var click_delemail = function(){

        var theme = $(this).closest("tr").find("td:nth-child(1)").text();
        if(window.confirm("确定要删除邮件’" + theme + "‘吗？")){
            var id = $(this).data("code");
            $.ajax({
                type: "post",
                url: "/sysemailmanage_con/deleteemail",
                data: {
                    id: id
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        alert("操作成功");
                        $("#pageorder button[name=refresh]").click();
                    }else{
                        alert("删除失败，请稍后再试");
                    }
                }
            });
        }
    };
    $(document).on("click", "#pageorder button.btn-del", click_delemail);

    /**
     * 点击修改
     */
    var click_openmod = function(){

        var id = $(this).data("code");
        window.open("/forward_con/sendmailpage?id="+id);
    };
    $(document).on("click", "#pageorder button.btn-mod", click_openmod);
});