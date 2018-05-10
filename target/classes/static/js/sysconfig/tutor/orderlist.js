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
            url: "/courseordermanager_con/getcourseorderlist",
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
                    courseName: $("#pageorder form .courseName").val()
                };
            },
            idField: 'commandid',//指定主键列
            columns: [
                {
                    title: '课程名称',
                    field: 'courseName',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '下单用户',
                    field: 'orderUser',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '订单编号',
                    field: 'orderCode',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '下单时间',
                    field: 'orderTime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '处理状态',
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
                            "<button class='btn btn-danger btn-socket'>建立会话</button>&nbsp;&nbsp;" +
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
            url: "/courseordermanager_con/getcourseorderlist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                "courseName": $("#pageorder form .courseName").val()
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
            url: "/courseordermanager_con/getorderdetail",
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

    /**
     * 点击修改状态进行相应操作，分两种情况，已须知确认，未须知确认
     */
    var click_openstatusmodbtn = function(){

        var code = $(this).data("code");
        $("#orderStatusEditAlert").data("code", code);
        $.ajax({
            type: "post",
            url: "/courseordermanager_con/getorderdetail",
            data: {
                code: code
            },
            dataType: "json",
            success: function(data){
                $.each(data, function(index, item){
                    $("#orderStatusEditAlert span."+index).text(item);
                });
            }
        });
    };
    $(document).on("click", "#orderTable button.btn-statusMod", click_openstatusmodbtn);

    /**
     * 点击提交状态更改
     */
    var click_openstatusmod = function(){

        var newTutorStatus = $("#orderStatusEditAlert .newTutorStatus").val();
        var newTutorInfo = $("#orderStatusEditAlert .newTutorInfo").val();
        var code = $("#orderStatusEditAlert").data("code");
        $.ajax({
            type: "post",
            url: "/courseordermanager_con/updatetutorstatus",
            data: {
                code: code,
                tutorStatus: newTutorStatus,
                tutorInfo: newTutorInfo
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("修改成功");
                    $("#orderStatusEditAlert").modal("hide");
                    $("#pageorder button[name=refresh]").click();
                }
            }
        });
    };
    $("#orderStatusEditAlert button:nth-child(1)").click(click_openstatusmod);

    /**
     *
     */
    var btn_gowebsocket = function(){
        var username = $(this).closest("tr").find("td:nth-child(2)").text();
        window.open("/personalpage_con/websocketpage?username="+username);
    };
    $(document).on("click", ".btn-socket", btn_gowebsocket);
});