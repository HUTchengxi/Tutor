package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public interface BbsCardCollectApi {

    /**
     *
     * @Description 获取当前用户的收藏总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    public void getMyCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 判断当前用户是否已收藏
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    public void checkCollectStatus(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    public void collectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 取消收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    public void uncollectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 获取当前用户收藏的帖子数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/13
     */
    public void getMyCollectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
