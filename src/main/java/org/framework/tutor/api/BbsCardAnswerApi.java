package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public interface BbsCardAnswerApi {

    /**
     *
     * @Description 获取对应的帖子答案数据
     * @param [cardId, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/7
     */
    public void getCardAnswerByCardid(Integer cardId, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 判断当前用户是否已回答问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void checkUserCommand(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 添加回答
     * @param [cardId, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void addAnswer(Integer cardId, String answer, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取用户回答总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/12
     */
    public void getMyAnswerCount(HttpServletRequest request, HttpServletResponse response) throws IOException;


    public void getMyAnswerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
