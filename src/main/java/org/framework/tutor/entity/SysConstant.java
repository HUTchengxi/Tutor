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

import org.apache.ibatis.annotations.Param;

/**
 * @author yinjimin
 * @Description: 系统常量实体勒
 * @date 2018年05月01日
 */
public class SysConstant {

    private static int POOL_MAX_SIZE = 10;

    private static long INITIAL_PERIOD = 3;

    private static long DELAY_PERIOD = 10;

    public static int getPoolMaxSize(){
        return POOL_MAX_SIZE;
    }

    public static long getInitialPeriod(){return INITIAL_PERIOD;}

    public static long getDelayPeriod(){return DELAY_PERIOD;}
}
