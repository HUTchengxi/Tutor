package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseDeleteReqApi {

    /**
     *
     * @Description 提交课程下线申请
     * @param [cid, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void setMyCourseDeleteReq(Integer cid, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取课程下线申请数据列表
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    public void getReqList(ParamMap paramMap, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取课程下线申请详情
     * @param [reqid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    public void getReqDetail(Integer reqid, HttpServletResponse response) throws IOException;
}
