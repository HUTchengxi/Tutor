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
