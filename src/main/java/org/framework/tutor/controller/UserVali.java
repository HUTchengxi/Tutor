package org.framework.tutor.controller;

import org.framework.tutor.service.UserVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/uservali_con")
public class UserVali {

    @Autowired
    private UserVService userVService;


}
