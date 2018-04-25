package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface RankApi {

    /**
     * 获取rank榜数据
     * @param type
     * @param mark
     * @param startpos
     * @param request
     * @param response
     * @throws IOException
     */
    public void rankSelect(String type, String mark, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
