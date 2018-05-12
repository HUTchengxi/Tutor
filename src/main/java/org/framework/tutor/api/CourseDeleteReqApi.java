package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseDeleteReqApi {

    /**
     * @Description 提交课程下线申请
     * @param cid 课程id
     * @param descript 申请备注信息
     */
    public String setMyCourseDeleteReq(Integer cid, String descript) throws IOException;

    /**
     * @Description 获取课程下线申请数据列表
     * @param paramMap
     */
    public String getReqList(ParamMap paramMap) throws IOException;

    /**
     * @Description 获取课程下线申请详情
     * @param reqid 下线申请id
     */
    public String getReqDetail(Integer reqid) throws IOException;
}
