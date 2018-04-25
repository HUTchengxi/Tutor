package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.service.CourseTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public interface CourseTreplyApi {

    /**
     * 获取对应用户的指定课程的家教回复数据
     * @param cid
     * @param id
     * @param response
     * @throws IOException
     */
    public void getTreply(Integer cid, Integer cmid, HttpServletResponse response) throws IOException;
}
