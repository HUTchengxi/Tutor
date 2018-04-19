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
            url: "/courseordermanager_con/geterrororderlist",
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
                    tutorName: $("#pageorder form .tutorName").val(),
                    username: $("#pageorder form .userName").val()
                };
            },
            idField: 'orderCode',//指定主键列
            columns: [
                {
                    title: '订单编号',
                    field: 'orderCode',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '下单用户',
                    field: 'userName',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '家教',
                    field: 'tutorName',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '课程名称',
                    field: 'courseName',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '家教状态',
                    field: 'tutorStatus',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '用户状态',
                    field: 'userStatus',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: 'orderCode',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var orderCode = value;
                        return "<button class='btn-primary btn btn-statusMod' data-toggle='modal' data-target='#orderStatusEditAlert' data-code='" + orderCode + "'>修改状态</button>&nbsp;&nbsp;" +
                            "<button class='btn btn-danger' data-code='" + orderCode + "'>建立会话</button>&nbsp;&nbsp;" +
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
            url: "/courseordermanager_con/geterrororderlist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                courseName: $("#pageorder form .courseName").val(),
                tutorName: $("#pageorder form .tutorName").val(),
                username: $("#pageorder form .userName").val()
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
     * 打开订单模态框进行数据渲染
     */
    var click_openordermore = function(){

        var code = $(this).data("code");
        $.ajax({
            type: "post",
            url: "/courseordermanager_con/geterrororderdetail",
            data: {
                code: code
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

});