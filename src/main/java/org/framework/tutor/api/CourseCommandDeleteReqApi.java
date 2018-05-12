package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseCommandDeleteReqApi {

    /**
     * @Description 提交评论删除申请
     * @param cid 待删除评论id
     * @param info 申请备注信息
     */
    public String addCommandDeleteReq(Integer cid, String info) throws IOException;
}
