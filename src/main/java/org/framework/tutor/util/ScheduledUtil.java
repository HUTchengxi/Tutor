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

import org.framework.tutor.entity.SysConstant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yinjimin
 * @Description: 定时任务工具类
 * @date 2018年05月01日
 */
public class ScheduledUtil {

    private static ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(SysConstant.getPoolMaxSize());

    /**
     * @param [runnable, execTime]
     * @return void
     * @Description 添加一个定时任务
     * @author yinjimin
     * @date 2018/5/1
     */
    public static void addTask(Runnable runnable) {
        scheduledExecutorService.scheduleAtFixedRate(runnable, SysConstant.getDelayPeriod(),
                SysConstant.getInitialPeriod(), TimeUnit.SECONDS);
    }
}
