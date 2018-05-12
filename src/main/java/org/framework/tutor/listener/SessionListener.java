package org.framework.tutor.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author yinjimin
 * @Description: Session自定义监听
 * @date 2018年05月01日
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        //TODO：两种情况下会触发该事件： invalidate方法调用或者session超时，
        // TODO：关闭浏览器但是没超时还是不会调用该方法的
    }
}
