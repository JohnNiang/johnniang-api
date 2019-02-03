package me.johnniang.api.config;

import me.johnniang.api.config.filter.CorsFilter;
import me.johnniang.api.config.properties.ApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Api configuration.
 *
 * @author johnniang
 */
@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class ApiConfiguration {

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();
        corsFilter.setFilter(new CorsFilter());
        corsFilter.setOrder(Ordered.LOWEST_PRECEDENCE);
        corsFilter.addUrlPatterns("/api/*");
        return corsFilter;
    }
}
