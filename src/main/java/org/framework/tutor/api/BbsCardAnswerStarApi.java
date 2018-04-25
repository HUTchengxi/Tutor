package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.BbsCardAnswerStar;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardAnswerStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public interface BbsCardAnswerStarApi {

    /**
     *
     * @Description 判断当前用户是否star指定回答
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    public void checkUserStar(Integer aid, HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 用户star指定回答
     * @param [aid, score, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    public void addUserStar(Integer aid, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
