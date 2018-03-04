package org.framework.tutor.controller;

import com.google.gson.JsonParser;
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
    private UserSService userSService;

    @Autowired
    private UserMService userMService;

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

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        //最勤打卡榜
        if("sign".equals(type)){
            //日榜
            if("day".equals(mark)){
                //获取今天的日期
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("-MM-dd");
                String daystr = sdf.format(now);

                List<UserSign> userSigns = userSService.rankSignDay(daystr, startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    res = "{";
                    int i = 1;
                    for (UserSign userSign: userSigns) {
                        UserMain userMain = userMService.getByUser(userSign.getUsername());
                        res += "\""+i+"\": ";
                        String temp = "{\"nickname\": \""+userMain.getNickname()+"\", " +
                                "\"stime\": \""+simpleDateFormat.format(userSign.getStime())+"\"}, ";
                        res += temp;
                        i++;
                    }
                    res = res.substring(0, res.length()-2);
                    res += "}";
                }
            }
            //总榜
            else if("total".equals(mark)){

                System.out.println("total");
                List<RankTemp> userSigns = userSService.rankSignTotal(startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    res = "{";
                    int i = 1;
                    for (RankTemp userSign: userSigns) {
                        UserMain userMain = userMService.getByUser(userSign.getUsername());
                        res += "\"" + i + "\": ";
                        String temp = "{\"nickname\": \"" + userMain.getNickname() + "\", " +
                                "\"stime\": \"" +userSign.getTotal()+ "\"}, ";
                        res += temp;
                        i++;
                    }
                    res = res.substring(0, res.length()-2);
                    res += "}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
