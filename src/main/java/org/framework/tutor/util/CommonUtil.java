package org.framework.tutor.util;

import java.util.UUID;

/**
 * 项目通用工具类
 * @author chengxi
 */
public class CommonUtil {

    /**
     * 随机生成16为code
     * @return
     */
    public static String getUUID(){

        UUID uuid = UUID.randomUUID();
        String valicode = uuid.toString().replaceAll("-", "").substring(0, 16);

        return valicode;
    }
}
