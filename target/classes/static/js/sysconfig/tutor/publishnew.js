$(function() {

    /**
     * 字段判空
     */
    var str_isnull = function(param){
        return (param==null || param==undefined || param=="undefined" || param.trim()=="" || param=="null");
    };

    /**
     * 初始化datetimepick
     */
    var load_datetimepicker = function(){
        //服务生效日期插件初始化
        $(".startTime").datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose: true,
            todayBtn: true
        });
    };
    load_datetimepicker();


    /**
     *  初始化fileinput上传控件
     */
    var eq = "c03c1650fa940cd2f5de959bfbd6d8a6";
    function load_fileinput() {
        $(".file").fileinput({
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            maxFileSize: 10000,
            enctype: "multipart/form-data"
        });
    };
    load_fileinput();

    /**
     * 获取所对应主类的所有课程类别
     */
    var async_getcoursetype = function(){
        $.ajax({
            type: "post",
            url: "/coursemain_con/getallcoursetype",
            dataType: "json",
            success: function(data){
                $.each(data, function(index, item){
                   $(".courseCtype").append("<option value='"+item+"'>"+item+"</option>");
                });
            }
        });
    };
    async_getcoursetype();

    /**
     * 选择目录总数进行指定目录数创建
     */
    var click_onchaptercountchange = function(){

        var count = parseInt($(this).val());
        $(".chapterlist").empty();
        for(var i=0; i<count; i++){
            $(".chapterlist").append("<div class='chapter'><div class=\"form-group\">\n" +
                "                <label class=\"control-label col-sm-1\">目录标题：</label>\n" +
                "                <div class=\"col-sm-5\">\n" +
                "                    <input class=\"form-control title\"/>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group clearfix\">\n" +
                "                <label class=\"control-label col-sm-1\">目录描述：</label>\n" +
                "                <div class=\"col-sm-5\">\n" +
                "                    <p class=\"form-control desc\" contenteditable=\"true\"></p>\n" +
                "                </div>\n" +
                "            </div></div>");
        }
    };
    $(".chapterCount").change(click_onchaptercountchange);


    /**
     * 点击发布新的课程
     */
    var click_publishnewcourse = function(){

        var formData = new FormData();
        var name = $(".courseName").val();
        var stype = $(".courseStype").val();
        var ctype = $(".courseCtype").val();
        var descript = $(".courseDesc").val();
        var imagesrc = $("input.file")[0].files[0];
        var total = $(".courseTotal").val();
        var jcount = $(".courseJcount").val();
        var price = $(".coursePrice").val();
        var sumTitle1 = "适合人群";
        var sumDescript1 = $(".comPerson").text();
        var sumTitle2 = "课程概述";
        var sumDescript2 = $(".courseDetail").text();
        var sumTitle3 = "mark";
        var sumDescript3 = $(".connect").text();
        formData.append("name", name);
        formData.append("stype", stype);
        formData.append("ctype", ctype);
        formData.append("descript", descript);
        formData.append("imgsrc", imagesrc);
        formData.append("total", total);
        formData.append("jcount", jcount);
        formData.append("price", price);
        formData.append("sumTitle1", sumTitle1);
        formData.append("sumDescript1", sumDescript1);
        formData.append("sumTitle2", sumTitle2);
        formData.append("sumDescript2", sumDescript2);
        formData.append("sumTitle3", sumTitle3);
        formData.append("sumDescript3", sumDescript3);

        var i = 1;
        var chapTitle = "";
        var chapDescript = "";
        $(".chapterlist .chapter").each(function(index, item){
            var title = $(this).find(".title").val();
            var descript = $(this).find(".desc").text();
            chapTitle += title + eq;
            chapDescript += descript + eq;
        });
            formData.append("chapTitle", chapTitle.substring(0, chapTitle.lastIndexOf(eq)));
            formData.append("chapDescript", chapDescript.substring(0, chapDescript.lastIndexOf(eq)));

        $.ajax({
            type: "post",
            url: "/coursemain_con/publishnewcourse",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function(data){
                var status = data.status;
                if(status == "fileexist"){
                    alert("图片已存在，请重新上传");
                    return ;
                }
                else if(status == "courseexist"){
                    alert("课程名称已存在");
                    return ;
                }
                else if(status == "valid"){
                    alert("发布成功");
                    window.history.go(0);
                }
            }
        });
    };
    $(".btn-save").click(click_publishnewcourse);

});