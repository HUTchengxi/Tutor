package org.framework.tutor.util;

import java.util.Random;
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

    /**
     * 为阿里语音验证码额外添加的生成四位数字验证码
     * @return
     */
    public static String getUUIDInt(){

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<4; i++){
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
