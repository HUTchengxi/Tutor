package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseCommandDeleteReqApi {

    /**
     *
     * @Description 提交评论删除申请
     * @param [cid, info, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    public void addCommandDeleteReq(Integer cid, String info, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
