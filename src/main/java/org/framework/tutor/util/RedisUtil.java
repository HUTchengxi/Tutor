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

import redis.clients.jedis.Jedis;

/**
 * @author yinjimin
 * @Description: redis二级缓存
 * @date 2018年04月28日
 */
public class RedisUtil {

    public static Jedis jedis;

    static {
        jedis = new Jedis("localhost");
        jedis.auth("chengxi");
    }

    /**
     *
     * @Description 判断并返回相应的值
     * @param [key]
     * @return boolean
     * @author yinjimin
     * @date 2018/4/28
     */
    public static String getMapValue(String obj, String prop){
        return jedis.get(obj+prop);
    }

    /**
     *
     * @Description 将对应的值注入到redis缓存中
     * @param [obj, prop, value]
     * @return void
     * @author yinjimin
     * @date 2018/4/30
     */
    public static void setMapKey(String obj, String prop, String value){
        jedis.set(obj+prop, value);
    }

    public static void main(String[] args) {

        System.out.println(jedis.get("11"));
    }
}
