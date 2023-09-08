package kuit.subway.utils;

import kuit.subway.auth.StubOAuthProvider;
import kuit.subway.auth.service.OAuthProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExternalConfig {

    @Bean
    public OAuthProvider oAuthProvider() {
        return new StubOAuthProvider();
    }
}
