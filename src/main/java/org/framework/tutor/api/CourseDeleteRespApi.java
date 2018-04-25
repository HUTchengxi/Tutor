package org.framework.tutor.api;

import com.google.gson.Gson;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public interface CourseDeleteRespApi {

    /**
     *
     * @Description 更新状态
     * @param [reqid, status, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    public void modReqStatus(Integer id, Integer status, String respDesc, HttpServletResponse response) throws IOException;
}
