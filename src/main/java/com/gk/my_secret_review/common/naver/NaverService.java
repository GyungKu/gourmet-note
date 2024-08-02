package com.gk.my_secret_review.common.naver;

import com.gk.my_secret_review.shop.vo.ResponseShopList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional
public class NaverService {

    private final RestClient restClient;

    @Value("${naver.secret}")
    private String secret;

    @Value("${naver.client_id}")
    private String clientId;

    public NaverUserInfo login(String code) {
        NaverToken token = getTokenFromCode(code);
        return getUserInfoFromToken(token.access_token());
    }

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

    private URI getURI(String url, String path) {
        return getURI(url, path, null);
    }

    private NaverToken getTokenFromCode(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", secret);
        params.add("code", code);
        params.add("state", "1234");
        URI uri = getURI("https://nid.naver.com/oauth2.0", "/token", params);

        return restClient.get().uri(uri).retrieve().body(NaverToken.class);
    }

    private NaverUserInfo getUserInfoFromToken(String accessToken) {
        URI uri = getURI("https://openapi.naver.com", "/v1/nid/me");
        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(NaverResponse.class)
                .response();
    }

    private Consumer<HttpHeaders> getDefaultHeaders() {
        return header -> {
            header.add("X-Naver-Client-Id", clientId);
            header.add("X-Naver-Client-Secret", secret);
        };
    }

}
