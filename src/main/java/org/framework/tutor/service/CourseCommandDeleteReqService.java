package org.framework.tutor.service;

public interface CourseCommandDeleteReqService {

    /**
     *
     * @Description 添加删除申请
     * @param [username, cid, info]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    void addCommandDeleteReq(String username, Integer cid, String info);
}
