package org.scoula.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        "org.scoula.sectionGeo.controller",
})
@ComponentScan(basePackages = "org.scoula")
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/resources/assets/");

        registry.addResourceHandler("/@/**")
                .addResourceLocations("/resources/assets/");

        registry.addResourceHandler("/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0);
    }
}
