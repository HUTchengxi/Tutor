/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 登录队列维持工具类
 * @date 2018年05月01日
 */
public class LoginQueueUtil {

    private static List<String> userList = new ArrayList<>();

    /**
     * 需要退出的用户列表
     */
    private static List<String> outerList = new ArrayList<>();

    public static boolean checkUserExist(String username){

        synchronized (userList) {
            return userList.contains(username);
        }
    }

    public static boolean addUser(String username){
        synchronized (userList){
            if(checkUserExist(username)){
                addOuter(username);
                return false;
            }else{
                userList.add(username);
                System.out.println(username+"用户加入登录队列");
                return true;
            }
        }
    }

    public static boolean removeUser(String username){

        synchronized (userList){
            if(checkUserExist(username)){
                userList.remove(username);
                System.out.println(username+"被挤下线，等待退出");
                return true;
            }
            return false;
        }
    }

    public static boolean checkOuterExist(String username){

        synchronized (outerList) {
            return outerList.contains(username);
        }
    }

    public static boolean addOuter(String username){

        synchronized (outerList){
            if(checkOuterExist(username)){
                return false;
            }else{
                outerList.add(username);
                System.out.println(username+"被加入退出队列");
            }
        }
        return false;
    }

    public static boolean removeOuter(String username){

        synchronized (outerList){
            if(checkOuterExist(username)){
                outerList.remove(username);
                System.out.println(username+"用户已退出");
                return true;
            }
            return false;
        }
    }
}
