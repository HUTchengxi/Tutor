package org.framework.tutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author yinjimin
 * @Description: 用于自动注册使用了@ServerEndPoint的bean，如果使用外置tomcat就不需要此步骤
 * @date 2018年05月10日
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
