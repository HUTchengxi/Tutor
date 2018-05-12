package org.framework.tutor.api.impl;

import com.google.gson.JsonParser;
import org.framework.tutor.api.RankApi;
import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class RankApiImpl implements RankApi {

    @Autowired
    private UserSignService userSignService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String rankSelect(String type, String mark, Integer startpos) throws IOException {

        String res = null;

        //最勤打卡榜
        if("sign".equals(type)){
            //日榜
            if("day".equals(mark)){
                //获取今天的日期
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("-MM-dd");
                String daystr = sdf.format(now);

                List<UserSign> userSigns = userSignService.rankSignDay(daystr, startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    res = "{";
                    int i = 1;
                    for (UserSign userSign: userSigns) {
                        UserMain userMain = userMainService.getByUser(userSign.getUsername());
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
                List<RankTemp> userSigns = userSignService.rankSignTotal(startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    res = "{";
                    int i = 1;
                    for (RankTemp userSign: userSigns) {
                        UserMain userMain = userMainService.getByUser(userSign.getUsername());
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

        return  new JsonParser().parse(res).getAsJsonObject().toString();
    }
}
