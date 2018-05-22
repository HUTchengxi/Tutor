package org.framework;

import org.apache.catalina.core.ApplicationContext;
import org.framework.tutor.util.WebsocketUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * SpringBoot测试启动类
 * @author chengxi
 */
@SpringBootApplication
//开启缓存
@EnableCaching
//开启异步
@EnableAsync
public class Main2 {

    /**
     * 自定义错误处理页面
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/forward_con/error_404");

                configurableEmbeddedServletContainer.addErrorPages(error404Page);
            }
        };
    }

    /**  
     *    
     * @Description 重新配置RedisCacheManager
     * @param [redisCacheManager]
     * @return void
     * @author yinjimin  
     * @date 2018/5/10
     */  
    @Autowired
    public void configRedisCacheManager(RedisCacheManager redisCacheManager){
        redisCacheManager.setDefaultExpiration(100L);
    }

    public static void main(String[] args){

        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main2.class, args);
        //解决WebSocket无法注入其他类实例的问题
        WebsocketUtil.setApplicationContext(applicationContext);
    }
}
