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
package org.framework.tutor.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description: 一个账号一个用户登录控制类
 * @date 2018年05月01日
 */
public class OneLoginSingle {

    private static Map<String, String> loginMap = new HashMap<>();

    public static void addKAndV(String username, String sessionId){
        loginMap.put(username, sessionId);
    }

    public static String getV(String username){
        return loginMap.get(username);
    }
}
