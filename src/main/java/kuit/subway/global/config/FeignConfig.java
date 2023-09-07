package kuit.subway.global.config;

import kuit.subway.SubwayApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = SubwayApplication.class)
public class FeignConfig {
}
