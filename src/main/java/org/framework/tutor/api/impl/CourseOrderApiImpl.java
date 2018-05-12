package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseOrderApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.service.CourseMainService;
import org.framework.tutor.service.CourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseOrderApiImpl implements CourseOrderApi {

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO：后续考虑使用redis
    @Override
    public String getOrderInfo(Integer cid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //获取课程订购人数
        Integer ccount = courseOrderService.getOrderCount(cid);
        resultMap.put("count", ccount);

        org.framework.tutor.domain.CourseOrder courseOrder = courseOrderService.getUserOrder(username, cid);
        if (courseOrder == null) {
            resultMap.put("state", "valid");
        } else {
            Integer state = courseOrder.getState();
            if (state == 0) {
                resultMap.put("state", "cart");
            } else {
                resultMap.put("state", "buy");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用redis  更新[username].cartcount
    @Override
    public String addCart(Integer cid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        Integer state = 0;
        if (!(courseOrderService.addUserOrder(username, cid, state))) {
            resultMap.put("status", "mysqlerr");
        } else {
            resultMap.put("statue", "valid");

            //更新[username].cartcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".cartcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getMyCart(Integer startpos) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        Integer status = 0;
        List<CourseOrder> courseOrders = courseOrderService.getMyOrder(username, status, startpos);
        if (courseOrders.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            for (org.framework.tutor.domain.CourseOrder courseOrder : courseOrders) {
                CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("name", courseMain.getName());
                rowMap.put("id", courseOrder.getId());
                rowMap.put("cid", courseOrder.getCid());
                rowMap.put("price", courseMain.getPrice());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用redis   更新[username].cartcount
    @Override
    public String delMyCart(Integer id) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        int len = courseOrderService.delMyCart(id, username);
        if (len == 0) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("status", "valid");

            //更新[username].cartcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".cartcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) - 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了redis    保存[username].cartcount
    @Override
    public String getMyCartCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".cartcount");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("count", redis.opsForValue().get(keyTemp.toString()));
        }else {
            Integer count = courseOrderService.getMyCartCount(username);
            resultMap.put("count", count);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getMyOrder(String status, Integer startpos) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        Integer state = 1;
        if (status.equals("ned")) {
            state = 3;
        } else if (status.equals("edn")) {
            state = 2;
        }
        List<org.framework.tutor.domain.CourseOrder> courseOrders = courseOrderService.getMyOrder(username, state, startpos);
        if (courseOrders.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (org.framework.tutor.domain.CourseOrder courseOrder : courseOrders) {
                CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("name", courseMain.getName());
                rowMap.put("id", courseOrder.getId());
                rowMap.put("cid", courseOrder.getCid());
                rowMap.put("otime", sdf.format(courseOrder.getOtime()));
                rowMap.put("price", courseMain.getPrice());
                rowList.add(rowMap);
            }
            resultMap.put("status", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String setInCycle(Integer oid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断用户名和订单是否对应，防止api非法调用
        org.framework.tutor.domain.CourseOrder courseOrder = courseOrderService.getByIdAndUser(username, oid);
        if (courseOrder == null) {
            resultMap.put("status", "invalid");
        } else {
            Integer row = courseOrderService.setInCycle(oid);
            if (row == 1) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getOrderCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        resultMap.put("count", courseOrderService.getOrderCountNow(username, now));
        return gson.toJson(resultMap);
    }
}
