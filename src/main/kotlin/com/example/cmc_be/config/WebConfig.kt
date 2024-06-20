package com.example.cmc_be.config

import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.config.annotation.*


@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://localhost:9000") // 모든 경로에 앞으로 만들 모든 CORS 정보를 적용한다
            .allowedMethods(
                "*", "PUT", "POST", "DELETE", "OPTIONS", "PATCH", "GET"
            ) // HTTP 요청의 Header에 어떤 값이든 들어갈 수 있도록 허용한다.
            .allowedHeaders("*")
            .exposedHeaders("Set-Cookie") // 자격증명 사용을 허용한다.
            .allowCredentials(true) // 해당 옵션 사용시 allowedOrigins를 * (전체)로 설정할 수 없다.
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        val cacheControl = CacheControl //                .noCache();
//            .maxAge(60, TimeUnit.SECONDS)
//        registry.addResourceHandler("**/*.*")
//            .addResourceLocations("classpath:/static/")
//            .setCacheControl(cacheControl)
    }


    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry
            .addViewController("/admin-page/**")
            .setViewName("forward:/")
        registry
            .addViewController("/swagger-ui/")
            .setViewName("forward:/swagger-ui/index.html")
    }

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.setPathMatcher(AntPathMatcher())
    }
}

