$(function() {

    /**
     * 异步获取历史版本更新数据
     */
    var async_getlognew = function(){

        $(".syslog table tbody").empty();
        $.ajax({
            async: true,
            type: "post",
            url: "/publishlog_con/getlognew",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count == 0){
                    return ;
                }
                var ptype = null;
                var pversion = null;
                var ptime = null;
                $.each(data.list, function(index, item){
                   ptype = item.ptype;
                   pversion = item.pversion;
                   ptime = item.ptime;
                   var descript = item.descript;
                   $(".syslog table tbody").append("<tr>\n" +
                       "                            <td align=\"left\">\n" +
                       "                                <p>* "+descript+"</p>\n" +
                       "                            </td>\n" +
                       "                        </tr>");
                });
                $(".syslog table thead tr td h4 span.pub-version").text(pversion);
                $(".syslog table thead tr td h4 span.pub-type").text(ptype);
                $(".syslog table thead tr td h4 span.pub-time").text(ptime);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取版本更新数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getlognew();


    /**
     * 异步获取所有的历史版本更新数据
     */
    var async_getlogAll = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/publishlog_con/getlogall",
            dataType: "json",
            success: function(data){
                var currentVersion = "";
                var tempvercls = "";
                $.each(data.list, function(index, item){
                    var ptype = item.ptype;
                    var pversion = item.pversion;
                    var ptime = item.ptime;
                    var descript = item.descript;
                    if(currentVersion == ""){
                        currentVersion = pversion;
                        tempvercls = currentVersion.replace(".","-").replace(".","-").replace(".","-");
                        $("#syslogmore").append("<div class=\"ver"+currentVersion.substring(0,1)+"\">\n" +
                            "            <fieldset class=\"layui-elem-field layui-field-title first\">\n" +
                            "                <legend>"+currentVersion.substring(0,1)+".x.x系列更新</legend>\n" +
                            "            </fieldset>\n" +
                            "            <ul class=\"layui-timeline\">\n" +
                            "            <li class=\"layui-timeline-item\">\n" +
                            "                <i class=\"layui-icon layui-timeline-axis\"></i>\n" +
                            "                <div class=\"layui-timeline-content layui-text "+tempvercls+"\">\n" +
                            "                    <h3 class=\"layui-timeline-title\">"+ptime+"<span class=\"ptype\">("+pversion+"&nbsp;&nbsp;"+ptype+")</span></h3>\n" +
                            "                    <p>"+descript+"</p>\n" +
                            "                </div>\n" +
                            "            </li>\n" +
                            "        </ul>\n" +
                            "        </div>");
                    }
                    else if(pversion == currentVersion){
                        $("#syslogmore .ver"+currentVersion.substring(0,1)+" ul li div."+tempvercls).append("<p>"+descript+"</p>");
                    }
                    else if(pversion.substring(0,1) == currentVersion.substring(0,1)){
                        currentVersion = pversion;
                        tempvercls = currentVersion.replace(".","-").replace(".","-").replace(".","-");
                        $("#syslogmore .ver"+currentVersion.substring(0,1)+" ul").append("<li class=\"layui-timeline-item\">\n" +
                            "                <i class=\"layui-icon layui-timeline-axis\"></i>\n" +
                            "                <div class=\"layui-timeline-content layui-text "+tempvercls+"\">\n" +
                            "                    <h3 class=\"layui-timeline-title\">"+ptime+"<span class=\"ptype\">("+pversion+"&nbsp;&nbsp;"+ptype+")</span></h3>\n" +
                            "                    <p>"+descript+"</p>" +
                            "                </div>\n" +
                            "            </li>");
                    }
                    else{
                        $("#syslogmore .ver"+currentVersion.substring(0,1)+" ul").append("<li class=\"layui-timeline-item\">\n" +
                            "                <i class=\"layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis\"></i>\n" +
                            "            </li>");
                        currentVersion = pversion;
                        tempvercls = currentVersion.replace(".","-").replace(".","-").replace(".","-");
                        $("#syslogmore").append("<div class=\"ver"+currentVersion.substring(0,1)+"\">\n" +
                            "            <fieldset class=\"layui-elem-field layui-field-title first\">\n" +
                            "                <legend>"+currentVersion.substring(0,1)+".x.x系列更新</legend>\n" +
                            "            </fieldset>\n" +
                            "            <ul class=\"layui-timeline\">\n" +
                            "            <li class=\"layui-timeline-item\">\n" +
                            "                <i class=\"layui-icon layui-timeline-axis\"></i>\n" +
                            "                <div class=\"layui-timeline-content layui-text "+tempvercls+"\">\n" +
                            "                    <h3 class=\"layui-timeline-title\">"+ptime+"<span class=\"ptype\">("+pversion+"&nbsp;&nbsp;"+ptype+")</span></h3>\n" +
                            "                    <p>"+descript+"</p>\n" +
                            "                </div>\n" +
                            "            </li>\n" +
                            "        </ul>\n" +
                            "        </div>");
                    }
                });
                $("#syslogmore .ver"+currentVersion.substring(0,1)+" ul").append("<li class=\"layui-timeline-item\">\n" +
                    "                <i class=\"layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis\"></i>\n" +
                    "            </li>");
                $("#syslogmore").append("<p class=\"nomore\">---加载到底啦---</p>");
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取版本更新数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getlogAll();

    /**
     * 异步获取当前家教的所有常用链接数据
     */
    // var async_getbtnslist = function(){
    //
    //     $(".news_search .tutor-btns").remove();
    //     $.ajax({
    //         async: true,
    //         type: "post",
    //         url: "/tutorbtns_con/getbtnslist",
    //         dataType: "json",
    //         success: function(data){
    //             var count = data.count;
    //             if(count == 0){
    //                 return;
    //             }
    //             $.each(data, function(index, item){
    //                var id = item.id;
    //                var name = item.name;
    //                var url = item.url;
    //                $(".news_search").prepend("<div class=\"layui-inline tutor-btns\">\n" +
    //                    "                    <a class=\"layui-btn audit_btn layui-btn-normal\" href='/forward_con/"+url+"'>"+name+"</a>\n" +
    //                    "                    <i class=\"layui-icon remove\" data-id='"+id+"'>&#x1006;</i>\n" +
    //                    "                </div>");
    //             });
    //         },
    //         error: function(xhr, status){
    //             window.alert("后台环境异常导致无法获取常用链接数据，请稍后再试");
    //             window.console.log(xhr);
    //         }
    //     });
    // };
    // async_getbtnslist();

    /**
     * 异步获取今日我的课程收藏总次数
     */
    var async_getcollectcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecollect_con/getcollectcount",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count >= 999) {
                    $(".userAll cite").text("999+");
                }
                else if(count == 0){
                    $(".userAll cite").text("--");
                }
                else{
                    $(".userAll cite").text(data.count);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取今日课程收藏数量，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getcollectcount();

    /**
     * 异步获取今日我的课程评论总次数
     */
    var async_getcommandcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/getcommandcount",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count >= 999) {
                    $(".waitNews cite").text("999+");
                }
                else if(count == 0){
                    $(".waitNews cite").text("--");
                }
                else{
                    $(".waitNews cite").text(data.count);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取今日课程评价数量，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getcommandcount();

    /**
     * 异步获取今日我的课程下单总次数
     */
    var async_getordercount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/courseorder_con/getordercount",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count >= 999) {
                    $(".imgAll cite").text("999+");
                }
                else if(count == 0){
                    $(".imgAll cite").text("--");
                }
                else{
                    $(".imgAll cite").text(data.count);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取今日课程下单数量，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getordercount();

    /**
     * 异步获取今日我的课程评分
     */
    var async_getvisitcount = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/coursecommand_con/getscoreavg",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count == "null"){
                    $(".newMessage cite").text("--");
                }
                else{
                    $(".newMessage cite").text(data.count);
                }
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取今日课程访问数量，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getvisitcount();
});