package com.gk.my_secret_review.common.naver;

import com.gk.my_secret_review.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final RestClient restClient;

    @Value("${naver.secret}")
    private String secret;

    @Value("${naver.client_id}")
    private String clientId;

    public ResponseShopList getAllByQuery(String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("display", "5");

        URI uri = getURI("https://openapi.naver.com", "v1/search/local.json", params);

        return restClient.get()
                .uri(uri)
                .headers(getDefaultHeaders())
                .retrieve()
                .body(ResponseShopList.class);
    }

    private URI getURI(String url, String path, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(url)
                .path(path)
                .queryParams(params)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }

    private Consumer<HttpHeaders> getDefaultHeaders() {
        return header -> {
            header.add("X-Naver-Client-Id", clientId);
            header.add("X-Naver-Client-Secret", secret);
        };
    }

}
