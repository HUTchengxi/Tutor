package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseOrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 课程订单控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/courseorder_con")
public class CourseOrderController {

    @Autowired
    private CourseOrderApi courseOrderApi;

    /**
     * 获取课程订购数据
     */
    @RequestMapping("/getorderinfo")
    public String getOrderInfo(Integer cid) throws IOException {

        return courseOrderApi.getOrderInfo(cid);
    }

    /**
     * 加入购物车
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addcart")
    public String addCart(Integer cid) throws IOException {

        return courseOrderApi.addCart(cid);
    }

    /**
     * 获取用户购物车数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycart")
    public String getMyCart(Integer startpos) throws IOException {

        return courseOrderApi.getMyCart(startpos);
    }

    /**
     * 删除指定用户的购物车物品
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delmycart")
    public String delMyCart(Integer id) throws IOException {

        return courseOrderApi.delMyCart(id);
    }

    /**
     * 获取指定用户的购物车物品总数
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycartcount")
    public String getMyCartCount() throws IOException {

        return courseOrderApi.getMyCartCount();
    }

    /**
     * 获取用户已支付/未支付/已失效订单数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmyorder")
    public String getMyOrder(@RequestParam("status") String status, Integer startpos) throws IOException {

        return courseOrderApi.getMyOrder(status, startpos);
    }

    /**
     * 将指定订单放入回收站
     * @param oid
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setinrecycle")
    public String setInCycle(Integer oid) throws IOException {

        return courseOrderApi.setInCycle(oid);
    }

    /**
     * 获取家教的课程订单总数
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getordercount")
    public String getOrderCount() throws IOException {

        return courseOrderApi.getOrderCount();
    }
}
