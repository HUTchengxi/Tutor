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
     * @Description 更新状态
     * @param id: 课程下线响应id
     * @param status：课程下线状态
     * @param respDesc：课程下线响应备注
     */
    public String modReqStatus(Integer id, Integer status, String respDesc) throws IOException;
}
