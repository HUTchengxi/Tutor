//package org.framework;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.web.servlet.ErrorPage;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//
///**
// * SpringBoot项目的启动类，可用于部署到tomcat上
// * @author chengxi
// */
//@SpringBootApplication
//public class Main extends SpringBootServletInitializer{
//
//    /**
//     * 生成war并部署到tomcat用
//     * @param application
//     * @return
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Main.class);
//    }
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(Main.class, args);
//    }
//
//    /**
//     * 自定义错误处理页面
//     * @return
//     */
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer(){
//
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/forward_con/error_404");
//
//                configurableEmbeddedServletContainer.addErrorPages(error404Page);
//            }
//        };
//    }
//
//}
