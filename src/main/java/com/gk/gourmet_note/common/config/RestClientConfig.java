package com.gk.gourmet_note.common.config;

import com.gk.gourmet_note.image.service.PhoneStorageHttpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final Environment env;

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        return RestClient.builder()
                .requestFactory(factory)
//                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (req, res) -> {
//                    throw new GlobalException("잘못된 요청입니다.", HttpStatus.resolve(res.getStatusCode().value()));
//                })
                .build();
    }

    @Bean
    public PhoneStorageHttpInterface phoneStorageHttpInterface() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);

        String storageBaseUrl = env.getProperty("storage.url");
        RestClient restClient = RestClient.builder()
                .baseUrl(storageBaseUrl)
                .requestFactory(requestFactory).build();
        
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(PhoneStorageHttpInterface.class);
    }
}
