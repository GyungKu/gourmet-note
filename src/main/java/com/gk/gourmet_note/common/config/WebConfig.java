package com.gk.gourmet_note.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.image.dir}")
    private String dir;
    @Value("${upload.image.path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(path)
                .addResourceLocations("file:" + dir);

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestResource = location.createRelative(resourcePath);
                        return requestResource.exists() && requestResource.isReadable() ?
                                requestResource : new ClassPathResource("/static/index.html");
                    }
                });
    }

    /**
     * CORS 설정 하나의 프로젝트로 배포할 때를 제외하고 필요함
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 환경변수로 CORS 주소를 ,를 기준으로 여러 값을 넣는다. 만약 없다면 localhost:5173을 CORS 설정에 추가한다.
        String corsOrigins = System.getenv("CORS_ORIGINS");
        String[] allowedOrigins = StringUtils.hasText(corsOrigins) ?
                corsOrigins.split(",") : new String[]{"http://localhost:5173"};

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .allowedOrigins(allowedOrigins);
    }
}
