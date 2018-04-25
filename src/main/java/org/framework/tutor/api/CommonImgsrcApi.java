package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public interface CommonImgsrcApi {

    /**
     *
     * @Description 获取所有图片数据
     * @param [response]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    public void getAll(HttpServletResponse response) throws IOException;
}
