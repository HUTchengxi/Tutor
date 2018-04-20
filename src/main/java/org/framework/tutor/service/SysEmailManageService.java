package org.framework.tutor.service;

import org.framework.tutor.domain.SysEmailManage;

import java.util.List;

public interface SysEmailManageService {

    void sendEmail(String send, String address, String theme, String email, Integer status);

    SysEmailManage getById(Integer emailId);

    /**
     *
     * @Description 获取最新修改的数据，即新增的数据的id
     * @param []
     * @return org.framework.tutor.domain.SysEmailManage
     * @author yinjimin
     * @date 2018/4/20
     */
    Integer getLastId();

    void updateEmail(Integer emailId, String theme, String email);

    List<SysEmailManage> getAllEmailListLimit(Integer offset, Integer pageSize);

    Integer getAllEmailCount();

    List<SysEmailManage> getEmailListByStatusLimit(Integer emailStatus, Integer offset, Integer pageSize);

    Integer getEmailCountByStatus(Integer emailStatus);

    void deleteEmial(Integer id);
}
