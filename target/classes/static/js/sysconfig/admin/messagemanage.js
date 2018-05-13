$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param.trim() == "" || param == "null");
    };

    /**
     * 初始化datetimepicker控件
     */
    var load_datetimepicker = function(){
        $("input[name=sendTime]").datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose: true,
            todayBtn: true
        });
    };
    load_datetimepicker();

    /**
     * 初始化orderTable表格控件并获取评论列表数据
     */
    var load_getorderlist = function () {

        $('#orderTable').bootstrapTable({
            url: "/usermessage_con/getmessagelist",
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
            uniqueId: "id",
            sidePagination: "server",
            queryParams: function (params) {
                return {
                    pageNo: params.offset / params.limit,
                    pageSize: params.limit,
                    status: $("#pageorder form .status").val(),
                    startTime: $("#pageorder form .sendTime").val(),
                    courseName: $("#pageorder form .sendTitle").val()
                };
            },
            idField: 'id',//指定主键列
            columns: [
                {
                    title: '标题',
                    field: 'title',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '私信',
                    field: 'identity',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '通知用户',
                    field: 'username',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '通知时间',
                    field: 'time',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '接收状态',
                    field: 'status',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var orderCode = value;
                        return "<button class='btn btn-danger btn-more' data-toggle='modal' data-target='#orderMore' data-code='" + orderCode + "'>查看详情</button>&nbsp;&nbsp;";
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
            url: "/usermessage_con/getmessagelist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                status: $("#pageorder form .status").val(),
                startTime: $("#pageorder form .sendTime").val(),
                courseName: $("#pageorder form .sendTitle").val()
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
            url: "/usermessage_con/getmessagedetail",
            data: {
                id: code
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
     * 点击按钮进入发送通知界面
     */
    var click_gosendmessage = function(){
        window.open("/adminpage_con/sendmessagepage");
    };
    $("#pageorder form button.btn-success").click(click_gosendmessage);
});