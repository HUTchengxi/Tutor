$(function() {

    /**
     * 获取家教的用户名和imgsrc
     */
    var async_gettutorinfo = function(){

        $.ajax({
            async: true,
            type: "post",
            url: "/usermain_con/getuserinfo",
            dataType: "json",
            success: function(data) {
                $(".layui-layout-right img.layui-nav-img").attr("src", data.imgsrc);
                $(".layui-layout-right span.author").text(data.nickname);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取用户数据，请稍后再试");
            }
        })
    };
    async_gettutorinfo();

    /**
     * 点击注销按钮的功能实现
     */
    var logoff_btn = function(){

        if(window.confirm("确认退出登录吗？")){
            $.ajax({
                asynv: false,
                type: "post",
                url: "/login_con/login_logoff",
                dataType: "json",
                success: function(data){
                    console.log(data);
                    alert("退出成功");
                    window.location = "/forward_con/welcome";
                },
                error: function(xhr, status){
                    alert("后台环境异常导致无法正确退出登录，请刷新页面重试");
                }
            });
        }
        $(this).closest("dd").removeClass("layui-this");
    };
    $(document).on("click", ".layui-layout-right dl dd a.logoff", logoff_btn);

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
                $.each(data, function(index, item){
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
                $(".syslog table thread tr td h4 span.pub-version").text(pversion);
                $(".syslog table thread tr td h4 span.pub-type").text(ptype);
                $(".syslog table thread tr td h4 span.pub-time").text(ptime);
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取版本更新数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getlognew();

    /**
     * 异步获取当前家教的所有常用链接数据
     */
    var async_getbtnslist = function(){

        $(".news_search .tutor-btns").remove();
        $.ajax({
            async: true,
            type: "post",
            url: "/tutorbtns_con/getbtnslist",
            dataType: "json",
            success: function(data){
                var count = data.count;
                if(count == 0){
                    return;
                }
                $.each(data, function(index, item){
                   var id = item.id;
                   var name = item.name;
                   var url = item.url;
                   $(".news_search").prepend("<div class=\"layui-inline tutor-btns\">\n" +
                       "                    <a class=\"layui-btn audit_btn layui-btn-normal\" href='/forward_con/"+url+"'>"+name+"</a>\n" +
                       "                    <i class=\"layui-icon remove\" data-id='"+id+"'>&#x1006;</i>\n" +
                       "                </div>");
                });
            },
            error: function(xhr, status){
                window.alert("后台环境异常导致无法获取常用链接数据，请稍后再试");
                window.console.log(xhr);
            }
        });
    };
    async_getbtnslist();

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
});