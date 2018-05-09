package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseOrderApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.service.CourseMainService;
import org.framework.tutor.service.CourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 获取课程订购数据
     *
     * @param cid
     * @param request
     * @param response
     */
    @Override
    public void getOrderInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 加入购物车
     *
     * @param cid
     * @param request
     * @param response
     */
    @Override
    public void addCart(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
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
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户购物车数据
     *
     * @param request
     * @param response
     */
    @Override
    public void getMyCart(Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 删除指定用户的购物车物品
     *
     * @param id
     * @param request
     * @param response
     */
    @Override
    public void delMyCart(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        int len = courseOrderService.delMyCart(id, username);
        if (len == 0) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.close();
    }

    /**
     * 获取指定用户的购物车物品总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getMyCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("count", courseOrderService.getMyCartCount(username));
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户已支付/未支付/已失效订单数据
     *
     * @param status
     * @param startpos
     * @param request
     * @param response
     */
    @Override
    public void getMyOrder(String status, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 将指定订单放入回收站
     *
     * @param oid
     * @param request
     * @param response
     */
    @Override
    public void setInCycle(Integer oid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取家教的课程订单总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        resultMap.put("count", courseOrderService.getOrderCountNow(username, now));
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
