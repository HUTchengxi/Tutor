package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CommandStar;
import org.framework.tutor.service.CommandStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public interface CommandStarApi {

    /**
     * 获取指定用户的指定课程评论的点赞数据
     * @param cmid 评论id
     */
    public String getMyCommandStar(Integer cmid) throws IOException;

    /**
     * 实现评论的点赞与踩
     * @param status 用户评分（1/-1）
     * @param cmid 评论id
     */
    public String addMyStar(Integer score, Integer cmid) throws IOException;
}
