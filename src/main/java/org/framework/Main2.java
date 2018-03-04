package org.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

/**
 * SpringBoot测试启动类
 * @author chengxi
 */
@SpringBootApplication
//指定mapper包，自动扫描，也可以@Mapper修饰每一个mapper类，不过这样更简单
//@MapperScan(basePackages = "org.springframework.tutor.mapper")
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

    public static void main(String[] args){

        SpringApplication.run(Main2.class, args);
    }
}
