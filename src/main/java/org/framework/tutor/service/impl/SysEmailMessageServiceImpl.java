package org.framework.tutor.service.impl;

import org.framework.tutor.domain.SysEmailManage;
import org.framework.tutor.mapper.SysEmailManageMapper;
import org.framework.tutor.service.SysEmailManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
@Service
public class SysEmailMessageServiceImpl implements SysEmailManageService {

    @Autowired
    private SysEmailManageMapper sysEmailManageMapper;

    @Override
    public void sendEmail(String send, String address, String theme, String email, Integer status) {
        sysEmailManageMapper.sendEmail(send, address, theme, email, status);
    }

    @Override
    public SysEmailManage getById(Integer emailId) {
        return sysEmailManageMapper.getById(emailId);
    }

    @Override
    public Integer getLastId() {
        return sysEmailManageMapper.getLastId();
    }

    @Override
    public void updateEmail(Integer emailId, String theme, String email) {
        sysEmailManageMapper.updateEmail(emailId, theme, email);
    }

    @Override
    public List<SysEmailManage> getAllEmailListLimit(Integer offset, Integer pageSize) {
        return sysEmailManageMapper.getAllEmailListLimit(offset, pageSize);
    }

    @Override
    public Integer getAllEmailCount() {
        return sysEmailManageMapper.getAllEmailCount();
    }

    @Override
    public List<SysEmailManage> getEmailListByStatusLimit(Integer emailStatus, Integer offset, Integer pageSize) {
        return sysEmailManageMapper.getEmailListByStatusLimit(emailStatus, offset, pageSize);
    }

    @Override
    public Integer getEmailCountByStatus(Integer emailStatus) {
        return sysEmailManageMapper.getEmailCountByStatus(emailStatus);
    }

    @Override
    public void deleteEmial(Integer id) {
        sysEmailManageMapper.deleteEmail(id);
    }
}
