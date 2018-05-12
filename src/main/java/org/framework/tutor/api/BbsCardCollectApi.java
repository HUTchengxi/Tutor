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
     * @Description 获取当前用户的收藏总数
     */
    public String getMyCollectCount() throws IOException;

    /**
     * @Description 判断当前用户是否已收藏
     * @param cardId: 帖子id
     */
    public String checkCollectStatus(Integer cardId) throws IOException;


    /**
     * @Description 收藏问题
     * @param cardId：帖子id
     */
    public String collectCard(Integer cardId) throws IOException;

    /**
     * @Description 取消收藏问题
     * @param cardId：帖子id
     */
    public String uncollectCard(Integer cardId) throws IOException;


    /**
     * @Description 获取当前用户收藏的帖子数据
     */
    public String getMyCollectInfo() throws IOException;
}
