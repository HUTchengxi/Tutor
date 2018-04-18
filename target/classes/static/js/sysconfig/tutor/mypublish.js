$(function() {

    /**
     * 字段判空
     */
    var str_isnull = function(param){
        return (param==null || param==undefined || param=="undefined" || param.trim()=="" || param=="null");
    }

    /**
     * 获取我的所有发布课程
     */
    var async_getmypublish = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursemain_con/getmypublish",
            dataType: "json",
            success: function (data) {
                var status = data.status;
                if (status == 0) {
                    $("body > div").append("<div class=\"none\"><p>还未发布过任何课程！</p></div>");
                }
                else {
                    var regtime = "";
                    $.each(data, function (index, item) {
                        var rtime = item.regtime;
                        var cid = item.id;
                        var imgsrc = item.imgsrc;
                        var viscount = item.viscount;
                        var comcount = item.comcount;
                        var buycount = item.buycount;
                        var score = item.score;
                        var name = item.name;
                        var ptime = item.ptime;
                        var $li = $("<li class='layui-timeline-item'></li>");
                        var $div = $("<div class='layui-timeline-content layui-text "+rtime+"'></div>");
                        var $i, $h3;
                        var $p = $("<p class='score'>课程评分：</p>");
                        if(str_isnull(score)){
                            $p.append("<span>暂无评分</span>");
                        }
                        else{
                            while(score-- != 0){
                                $p.append("<span class=\"glyphicon glyphicon-star\"></span>");
                            }
                        }
                        var $publog = $("<div class=\"publog clearfix\">\n" +
                            "                    <div class=\"pull-left\">\n" +
                            "                        <a href=\"/forward_con/showcourse?id="+cid+"\" target=\"_blank\">\n" +
                            "                            <img src=\""+imgsrc+"\" />\n" +
                            "                        </a>\n" +
                            "                    </div>\n" +
                            "                    <div class=\"pull-left\">\n" +
                            "                        <div class=\"main-show "+cid+"\">\n" +
                            "                            <p class=\"main-top\">"+name+"</p>\n" +
                            "                            <p class=\"ctime\">发布于: "+ptime+"</p>\n" +
                            "                            <p class=\"viscount count\"><span>"+viscount+"</span>人浏览</p>\n" +
                            "                            <p class=\"comcount count\"><span>"+comcount+"</span>条评论</p>\n" +
                            "                            <p class=\"buycount count\"><span >"+buycount+"</span>人购买</p>\n" +
                            "                        </div>\n" +
                            "                    </div>"  +
                            "                    <div class=\"pull-left\" id=\"talkbubble\" data-cid=\""+cid+"\">\n" +
                            "                        <button class=\"btn btn-primary\" data-toggle='modal' data-target='#chapterModal'>课程目录</button>\n" +
                            "                        <button class=\"btn btn-primary\" data-toggle='modal' data-target='#summaryModal'>课程概述</button>\n" +
                            "                        <button class=\"btn btn-primary\" data-toggle='modal' data-target='#infoModal'>基本信息</button>\n" +
                            "                        <button class=\"btn btn-primary\" data-toggle='modal' data-target='#delModal'>申请下线</button>\n" +
                            "                    </div>" +
                            "                </div>");
                        if(regtime != rtime){
                            regtime = rtime;
                            $h3 = $("<h3 class=\"layui-timeline-title\">"+rtime+"</h3>");
                            $li.append($h3);
                            $i = $("<i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>");
                            $li.append($i);
                            $("body > div ul").append($li.append($div.append($publog)));
                        }
                        else{
                            $("body > div ul li div."+rtime).append($publog);
                        }
                        $(".main-show."+cid).append($p);
                    });
                    $("body > div ul").append("<li class=\"layui-timeline-item\">\n" +
                        "        <i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>\n" +
                        "        </li>");
                }
            },
            error: function (xhr, status) {
                window.alert("后台环境异常导致无法获取我的收藏数据，请刷新页面重试");
                window.console.log(xhr);
            }
        });
    };
    async_getmypublish();



    //--------------------------------课程目录----------------------------------


    /**
     * 获取指定课程的目录数据
     */
    var click_getcoursechapter = function(){

        var cid = $(this).closest("#talkbubble").data("cid");
        $("#chapterModal").data("cid", cid);
        $.ajax({
            type: "post",
            url: "/coursechapter_con/getcoursechapter",
            data: {
                cid: cid
            },
            dataType: "json",
            success: function(data){
                $("#chapterModal .btndiv button:nth-child(2)").data("status", "off").text("删除目录");
                $("#chapterModal .chapters").empty();
                var count = data.count;
                if(count == 0){
                    $("#chapterModal .chapters").append("<div class=\"none\">暂未设置任何目录</div>");
                }
                else{
                    $.each(data, function(index, item){
                        var id = item.id;
                        var ord = item.ord;
                        var title = item.title;
                        var descript = item.descript;
                        $("#chapterModal .chapters").append("<div class=\"chapter\" data-id='"+id+"' data-ord='"+ord+"'>\n" +
                            "                        <legend class=\"clearfix\">目录<span class=\"pull-right glyphicon glyphicon-remove\"></span></legend>\n" +
                            "                        <div class=\"col-lg-12\">\n" +
                            "                            <span>目录标题</span>\n" +
                            "                            <input class=\"form-control title\" type=\"text\" value='"+title+"' />\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-lg-12\">\n" +
                            "                            <span>目录描述</span>\n" +
                            "                            <input class=\"form-control descript\" type=\"text\" value='"+descript+"' />\n" +
                            "                        </div>\n" +
                            "                        <div class=\"clearfix\"></div>\n" +
                            "                    </div>")
                    });
                }
            }
        })
    };
    $(document).on("click", "#talkbubble button:nth-child(1)", click_getcoursechapter);

    /**
     * 点击删除，显示所有remove按钮
     */
    var click_showchapterdelbtn = function(){

        var status = $(this).data("status");
        console.log(status);
        if(status != "on"){
            $(this).data("status", "on");
            $("#chapterModal .modal-body legend span").css("display", "inline-block");
            $(this).text("考虑一下");
        }
        else{
            $(this).data("status", "off");
            $("#chapterModal .modal-body legend span").css("display", "none");
            $(this).text("删除目录");
        }
    };
    $("#chapterModal .modal-body .btndiv button:nth-child(2)").click(click_showchapterdelbtn);

    /**
     * 删除指定目录
     */
    var click_delchapter = function(){

        if(window.confirm("确定删除该目录吗？")){
            var id = $(this).closest(".chapter").data("id");
            var $this = $(this);
            $.ajax({
                type: "post",
                url: "/coursechapter_con/deletechapter",
                data: {
                    id: id
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status == "valid"){
                        $this.closest(".chapter").remove();
                        $("#chapterModal .btndiv button:nth-child(2)").click();
                    }
                }
            });
        }
    };
    $(document).on("click", "#chapterModal .modal-body legend span", click_delchapter);

    /**
     * 点击增加添加一个增加输入框
     */
    var click_showaddchapter = function(){

        if($("#chapterModal .chapters div").hasClass("none")){
            $("#chapterModal .chapters").empty();
        }
        $("#chapterModal .chapters").append("<div class=\"chapter chapteradd\">\n" +
            "                        <legend class=\"clearfix\">新增目录</legend>\n" +
            "                        <div class=\"col-lg-12\">\n" +
            "                            <span>输入目录标题</span>\n" +
            "                            <input class=\"form-control title\" type=\"text\" placeholder='输入新增目录标题' />\n" +
            "                        </div>\n" +
            "                        <div class=\"col-lg-12\">\n" +
            "                            <span>输入目录描述</span>\n" +
            "                            <input class=\"form-control descript\" type=\"text\" placeholder='输入新增目录描述' />\n" +
            "                        </div>\n" +
            "                        <div class=\"clearfix\"></div>\n" +
            "                    </div>")
    };
    $("#chapterModal .modal-body .btndiv button:nth-child(1)").click(click_showaddchapter);

    /**
     * 点击保存课程目录的修改（新增/修改）
     */
    var click_submodchapter = function(){

        var flag = true;
        $("#chapterModal .chapters .chapter").each(function(){
            if(str_isnull($(this).find(".title").val()) || str_isnull($(this).find(".descript").val())){
                flag = false;
            }
        });
        if(!flag){
            alert("请填写完整");
            return;
        }
        var cid = $("#chapterModal").data("cid");
        $("#chapterModal .chapters .chapter").each(function(){
            var id = $(this).data("id");
            var ord = $(this).data("ord");
            var title = $(this).find(".title").val();
            var descript = $(this).find(".descript").val();

            $.ajax({
                async: false,
                type: "post",
                url: "/coursechapter_con/modchapter",
                data: {
                    id: id,
                    cid: cid,
                    ord: ord,
                    title: title,
                    descript: descript
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(statsu != "valid"){
                        alert("上传过程中报错了，请稍后再试");
                        return ;
                    }
                }
            });
        });
            alert("保存成功");
            $("#chapterModal .modal-footer button:nth-child(2)").click();
    };
    $("#chapterModal .modal-footer button:nth-child(1)").click(click_submodchapter);




    //----------------------------------------- 课程基本信息 -------------------------------------------
    /**
     * 获取对应的课程基本信息数据
     */
    var click_getcoursemaininfo = function(){

        var cid = $(this).closest("#talkbubble").data("cid");

        $.ajax({
            type: "post",
            url: "/coursemain_con/getcoursebyid",
            data: {
                id: cid
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "invalid"){
                    return ;
                }
                var stype = "";
                var ctype = "";
                $.each(data, function(index, item){
                   $("#infoModal p."+index).text(item);
                   if(index == "imgsrc"){
                       $("#infoModal img").attr("src", item);
                   }
                   if(index == "stype"){
                       stype = item==1?"小学":item==2?"初中":item==3?"高中":"其他";
                   }
                   if(index == "ctype"){
                        ctype = item;
                   }
                });
                $("#infoModal p.courseType").text(stype+"--"+ctype);
            }
        });
    };
    $(document).on("click", "#talkbubble button:nth-child(3)", click_getcoursemaininfo);



    //----------------------------------------课程概述--------------------------------------
    /**
     * 获取课程概述信息
     */
    var click_getcoursesummaryinfo = function(){

        var cid = $(this).closest("#talkbubble").data("cid");

        $.ajax({
            type: "post",
            url: "/coursesummary_con/getcoursesummaryinfo",
            data: {
                cid: cid
            },
            dataType: "json",
            success: function(data){
                $("#summaryModal .modal-body").empty();
                var status = data.status;
                if(status == 0){
                    //TODO: 因为课程概述必须设置的，可能是黑客或者管理员删了导致无法获取
                    $("#summaryModal .modal-body").append("<div class='none'>无法获取数据，请联系管理员</div>");
                    return ;
                }
                $.each(data, function(index, item){
                    var id = item.id;
                    var title = item.title;
                    var descript = item.descript;
                    $("#summaryModal .modal-body").append("<div class=\"col-lg-12\" data-sid='"+id+"'>\n" +
                        "                        <span>"+title+"</span>\n" +
                        "                        <p class=\"form-control\" contenteditable='true'>"+descript+"</p>\n" +
                        "                    </div>");
                });
                $("#summaryModal .modal-body").append("<div class=\"clearfix\"></div>");
            }
        });
    };
    $(document).on("click", "#talkbubble button:nth-child(2)", click_getcoursesummaryinfo);

    /**
     * 保存课程概述的修改
     */
    var click_updatecoursesummary = function(){

        $("#summaryModal .modal-body .col-lg-12").each(function(){

            var id = $(this).data("sid");
            var title = $(this).find("span").text();
            var descript = $(this).find("p").text();

            $.ajax({
                async: false,
                type: "post",
                url: "/coursesummary_con/updatecoursesummary",
                data: {
                    id: id,
                    title: title,
                    descript: descript
                },
                dataType: "json",
                success: function(data){
                    var status = data.status;
                    if(status != "valid"){
                        alert("数据发生错误，请联系管理员");
                        return;
                    }
                }
            });
        });
        alert("更新成功");
        $("#summaryModal .modal-footer button:nth-child(2)").click();
    };
    $("#summaryModal .modal-footer button:nth-child(1)").click(click_updatecoursesummary);



    //---------------------------------------课程下线申请--------------------------
    /**
     * 打开modal，传入cid
     */
    var click_opendelmodal = function(){

        var cid = $(this).closest("#talkbubble").data("cid");
        $("#delModal").data("cid", cid);
    };
    $(document).on("click", "#talkbubble button:nth-child(4)", click_opendelmodal);
    /**
     * 提交课程下线申请
     */
    var click_setmycoursedeletereq = function(){

        var descript = $("#delModal .modal-body p").text();
        var cid = $("#delModal").data("cid");
        $.ajax({
            type: "post",
            url: "/coursedeletereq_con/setmycoursedeletereq",
            data: {
                cid: cid,
                descript: descript
            },
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "valid"){
                    alert("提交成功，请等待审核");
                    $("#delModal .modal-footer button:nth-child(2)").click();
                    $("#delModal p").text("");
                }
            }
        });
    };
    $("#delModal .modal-footer button:nth-child(1)").click(click_setmycoursedeletereq);
});