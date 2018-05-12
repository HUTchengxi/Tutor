package org.framework.tutor.controller;

import org.framework.tutor.api.RankApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 排行榜控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/rank_con")
public class RankController {

    @Autowired
    private RankApi rankApi;

    /**
     * 获取rank榜数据
     */
    @RequestMapping("/rank_select")
    public String rankSelect(String type, String mark, Integer startpos) throws IOException {

        return rankApi.rankSelect(type, mark, startpos);
    }
}
