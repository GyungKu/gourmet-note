package com.gk.gourmet_note.common.config;

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

    /**
     * 등록된 url 외의 요청은 vue 에서 받게 하기 위한 설정 ( vue router 사용 )
     * -> static 폴더에 vue 프로젝트를 빌드할 때만 필요한 설정
     * 같은 프로젝트로 배포하기 위해 필요한 설정임 ( Cloud Front 나 웹 호스팅을 통해 vue 프로젝트를 따로 배포 할 경우에는 필요 없음 )
     * 하나의 인스턴스 서버에서 Nginx 등으로 배포할 때도 필요 없음
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/get/files/**")
                .addResourceLocations("file:C:/study/upload/");

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
