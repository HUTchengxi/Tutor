$(function() {

    /**
     * 字段判空
     */
    var str_isnull = function(param){
        return (param==null || param==undefined || param=="undefined" || param.trim()=="" || param=="null");
    };


    /**
     *  初始化fileinput上传控件
     */
    function load_fileinput() {
        $(".file").fileinput({
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            maxFileSize: 10000,
            enctype: "multipart/form-data"
        });
    };
    load_fileinput();


});