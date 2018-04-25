package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.PublishLog;
import org.framework.tutor.domain.PublishType;
import org.framework.tutor.service.PublishLogService;
import org.framework.tutor.service.PublishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public interface PublishLogApi {

    /**
     * 加载最新的版本更新记录
     * @param response
     * @return
     */
    public void getLogNew(HttpServletResponse response) throws IOException ;


    /**
     *
     * @Description 获取所有的版本更新记录
     * @param [response]
     * @return void
     * @author yinjimin
     * @date 2018/3/31
     */
    public void getLogAll(HttpServletResponse response) throws IOException;
}
