package org.scoula.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.exception",
        "org.scoula.controller",
        "org.scoula.user.controller",
        "org.scoula.agent.controller",
        "org.scoula.checklist.controller",
        "org.scoula.dangerResult.controller",
        "org.scoula.documentAnalysis.controller",
        "org.scoula.listing.controller",
        "org.scoula.ai.controller",
        "org.scoula.quiz.controller",
})
@ComponentScan(basePackages = "org.scoula")
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")     // url이 /resources/로 시작하는 모든 경로
                .addResourceLocations("/resources/");    // webapp/resources/경로로 매핑

        //frontend의 static경로 파일 경로(css, js, html)
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/resources/assets/");

        registry.addResourceHandler("/@/**")
                .addResourceLocations("/resources/assets/");

        // index.html에 대한 캐시 방지
        registry.addResourceHandler("/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0);

        /// /////////////////////////////////////////////

        // Swagger UI 리소스를 위한 핸들러 설정
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // Swagger WebJar 리소스 설정
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // Swagger 리소스 설정
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/v2/api-docs")
                .addResourceLocations("classpath:/META-INF/resources/");


        /// ////////////////////////////////////////////
    }


    //선언적 코드(java, properties, xml, yaml대체 가능)
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/")
        //.setViewName("forward:/resources/index.html");
    }    // 404 에러를 index.html로 리다이렉션하는 컨트롤러 추가


    //	Servlet 3.0 파일 업로드 사용시
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }
}
