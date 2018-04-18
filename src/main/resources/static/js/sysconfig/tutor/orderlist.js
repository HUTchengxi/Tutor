$(function () {

    /**
     * 字段判空
     */
    var str_isnull = function (param) {
        return (param == null || param == undefined || param == "undefined" || param.trim() == "" || param == "null");
    };

    /**
     * 初始化commandTable表格控件并获取评论列表数据
     */
    var load_getcommandlist = function () {

        $('#courseTable').bootstrapTable({
            url: "/coursecommand_con/getcommandlist",
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
                    courseName: $("#pagecommand form .courseName").val()
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
                    title: '所属分类',
                    field: 'courseType',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '评论用户',
                    field: 'commandUser',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '评论',
                    field: 'commandDesc',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '评论时间',
                    field: 'commandTime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '神评',
                    field: 'godStatus',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return "<span>" + (value == 1 ? "是" : "否") + "</span>";
                    }
                },
                {
                    title: '状态',
                    field: 'commandStatus',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return "<span>" + (value == 0 ? "正常" : value == 1 ? "删除待审" : "已删除") + "</span>";
                    }
                },
                {
                    title: '操作',
                    field: 'commandId',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var commandId = value;
                        return "<button class='btn-primary btn btn-god' data-id='" + commandId + "'>神评</button>&nbsp;&nbsp;" +
                            "<button class='btn btn-danger' data-toggle='modal' data-target='#comDele' data-id='" + commandId + "'>删除</button> ";
                    }
                }
            ]
        });
    };
    load_getcommandlist();


    /**
     * 点击查询指定课程评价
     */
    var click_getcommandlistbyname = function () {

        $.ajax({
            type: "post",
            url: "/coursecommand_con/getcommandlist",
            data: JSON.stringify({
                "pageNo": 0,
                "pageSize": 10,
                "courseName": $("#pagecommand form .courseName").val()
            }),
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                $("#courseTable").bootstrapTable('load', data);
            }
        });
    };
    $("#pagecommand form").submit(click_getcommandlistbyname);

    /**
     * 指定神评
     */
    var click_setcommandgod = function(){

        var godStatus = $(this).closest("tr").find("td:nth-child(6)").text();
        if(godStatus === "是"){
            alert("已经指定过了哦");
            return ;
        }

        var id = $(this).data("id");
        console.log(id);
        var commandUser = $(this).closest("tr").find("td:nth-child(3)").text();
        if(confirm("确认指定用户'" + commandUser + "'的评论为神评吗? ")){
            $.ajax({
                type: "post",
                url: "/coursecommand_con/setcommandgodstate",
                data: {
                    id: id
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        alert("设置成功");
                        $("#pagecommand button[name=refresh]").click();
                    } else if(status == "full"){
                        alert("该课程已有三个神评");
                        return ;
                    }
                }
            });
        }
 };
    $(document).on("click", "#courseTable .btn-god", click_setcommandgod);

    /**
     * 点击删除打开模态框
     */
    var click_openDelModal = function(){

        var id = $(this).data("id");
        $("#comDele").data("id", id);
        $("#comDele .commandUser").text($(this).closest("tr").find("td:nth-child(3)").text());
        $("#comDele .commandDesc").text($(this).closest("tr").find("td:nth-child(4)").text());
        $("#comDele p").text("");
    };
    $(document).on("click", "#courseTable .btn-danger", click_openDelModal);

    /**
     * 点击提交删除申请
     */
    var click_submitDeleteReq = function(){

        var id = $("#comDele").data("id");
        var reqInfo = $("#comDele p").text();

        $.ajax({
            type: "post",
            url: "/coursecommanddeletereq_con/addcommanddeletereq",
            data: {
                cid: id,
                info: reqInfo
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("已提交申请，请留意通知");
                    $("#comDele .modal-footer button:nth-child(2)").click();
                    $("#pagecommand button[name=refresh]").click();
                }else{
                    alert("别闹");
                }
            }
        });
    };
    $("#comDele .modal-footer button:nth-child(1)").click(click_submitDeleteReq);
});