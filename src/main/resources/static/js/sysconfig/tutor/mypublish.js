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
                            "                    </div>\n" +
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
});