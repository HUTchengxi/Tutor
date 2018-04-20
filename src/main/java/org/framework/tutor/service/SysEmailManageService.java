package org.framework.tutor.service;

public interface SysEmailManageService {

    void sendEmail(String send, String address, String theme, String email, Integer status);
}
