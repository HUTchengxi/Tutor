package org.framework.tutor.api;

import com.google.gson.Gson;
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.service.CourseCMService;
import org.framework.tutor.service.CourseCommandDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
