package org.framework.tutor.util;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 项目通用工具类
 * @author chengxi
 */
public class CommonUtil {

    /**  
     * @Description 用于记录访问记录
     */
    public static List<Integer> cardList = new ArrayList<>();

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


    /**
     *
     * @Description 获取真实的ip地址
     * @param [request]
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/11
     */
    public static String getIpAddr(HttpServletRequest request){
        try {
            if (request == null) {
                return null;
            }
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException unknownhostexception) {
                }
            }
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @Description 保存当前访问文章的id
     * @param [cardid]
     * @return void
     * @author yinjimin
     * @date 2018/4/11
     */
    public static void addCardList(Integer cardid){
        cardList.add(cardid);
    }

    /**
     *
     * @Description 是否存在对应的文章id
     * @param [cardid]
     * @return boolean
     * @author yinjimin
     * @date 2018/4/11
     */
    public static boolean isExistCardid(Integer cardid){

        return cardList.contains(cardid);
    }

    /**
     *
     * @Description 随机获取四位加密盐
     * @param []
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/28
     */
    public static Integer getMd5Salt(){
        return Integer.parseInt(String.valueOf(Math.random()*9000+1000).substring(0,4));
    }

    /**
     *
     * @Description 密码加密算法  加密策略：salt(0-2) + transpass + salt(2-4)
     * @param [transPass, salt]
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/28
     */
    public static String getMd5Pass(String transPass, Integer salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        transPass = salt/100 + transPass + salt%100;
        String md5Pass = base64Encoder.encode(md5.digest(transPass.getBytes("utf-8")));
        return md5Pass;
    }

    /**  
     * @Description 根据指定字符串生成订单编号
     * @param [str]
     * @return java.lang.String
     */  
    public static String getOrderCode(String name) {

        UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr.substring(0, 20);
    }
}
