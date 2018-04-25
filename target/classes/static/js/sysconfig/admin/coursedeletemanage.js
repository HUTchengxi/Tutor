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
            url: "/coursedeletereq_con/getreqlist",
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
                    courseName: $("#pageorder form .courseName").val(),
                    status: $("#pageorder form .respStatus").val()
                };
            },
            idField: 'orderCode',//指定主键列
            columns: [
                {
                    title: '课程名称',
                    field: 'courseName',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '申请用户',
                    field: 'reqUser',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '申请时间',
                    field: 'reqTime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '处理状态',
                    field: 'respStatus',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: 'reqId',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var orderCode = value;
                        return "<button class='btn-primary btn btn-errdear' data-toggle='modal' data-target='#errDear' data-code='" + orderCode + "'>处理申请</button>&nbsp;&nbsp;" +
                            "<button class='btn btn-danger btn-more' data-toggle='modal' data-target='#orderMore' data-code='" + orderCode + "'>查看详情</button>&nbsp;&nbsp;";
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
            url: "/coursedeletereq_con/getreqlist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                courseName: $("#pageorder form .courseName").val(),
                status: $("#pageorder form .respStatus").val()
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
     * 打开处理申请，渲染id到statusMod上
     */
    var click_openerrdear = function(){

        var id = $(this).data("code");
        $("#statusMod").data("reqid", id);
        var reqUser = $(this).closest("tr").find("td:nth-child(2)").text();
        $("#errDear").data("username", reqUser);
    };
    $(document).on("click", "#pageorder .btn-errdear", click_openerrdear);


    /**
     * 打开订单模态框进行数据渲染
     */
    var click_openordermore = function(){

        var code = $(this).data("code");
        $.ajax({
            type: "post",
            url: "/coursedeletereq_con/getreqdetail",
            data: {
                reqid: code
            },
            dataType: "json",
            success: function(data){
                $.each(data, function(index, item){
                   $("#orderMore ."+index).text(item);
                });
            }
        });
    };
    $(document).on("click", "#orderTable button.btn-more", click_openordermore);

    /**
     * 保存修改对应的状态
     */
    var click_modreqstatus = function(){

        var reqid = $("#statusMod").data("reqid");
        var status = $("#statusMod .status").val();
        var respDesc = $("#statusMod .respDesc").val();
        
        $.ajax({
            type: "post",
            url: "/coursedeleteresp_con/modreqstatus",
            data: {
                id: reqid,
                status: status,
                respDesc: respDesc
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("操作成功");
                    $(".modal").modal('hide');
                    $("#pageorder button[name=refresh]").click();
                }else{
                    alert("操作失败");
                }
            }
        });
    };
    $("#statusMod button.btn-save").click(click_modreqstatus);

    /**
     * 点击发送邮件
     */
    var click_opensendmailpage = function(){

        window.open("/adminpage_con/sendmailpage?username="+$("#errDear").data("username"));
    };
    $("#errDear .link-sendmail").click(click_opensendmailpage);
});