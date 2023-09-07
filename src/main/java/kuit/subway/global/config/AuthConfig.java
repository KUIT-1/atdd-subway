package kuit.subway.global.config;

import kuit.subway.auth.AuthenticationPrincipalArgumentResolver;
import kuit.subway.auth.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    // Interceptor 등록
    // Interceptor 가 어떤 요청에 대해서 작동할 것인지 미리 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/members/**")
                .excludePathPatterns("/members");
    }

    // ArgumentResolver 등록
    // ArgumentResolver 는 원하는 파라미터에만 적용할 수 있도록 이미 ArgumentResolver 에서 설정하고 있으므로 추가 설정 필요 x
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
