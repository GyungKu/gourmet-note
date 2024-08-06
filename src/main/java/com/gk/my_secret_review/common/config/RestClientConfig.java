package com.gk.my_secret_review.common.config;

import com.gk.my_secret_review.common.exception.GlobalException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(300);
        factory.setReadTimeout(300);

        return RestClient.builder()
                .requestFactory(factory)
//                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (req, res) -> {
//                    throw new GlobalException("잘못된 요청입니다.", HttpStatus.resolve(res.getStatusCode().value()));
//                })
                .build();
    }
}
