package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.api.RankApi;
import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排行榜控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/rank_con")
public class Rank {

    @Autowired
    private RankApi rankApi;

    /**
     * 获取rank榜数据
     * @param type
     * @param mark
     * @param startpos
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/rank_select")
    public void rankSelect(String type, String mark, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        rankApi.rankSelect(type, mark, startpos, request, response);
    }
}
