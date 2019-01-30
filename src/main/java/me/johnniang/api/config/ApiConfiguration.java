package me.johnniang.api.config;

import me.johnniang.api.config.properties.ApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Api configuration.
 *
 * @author johnniang
 */
@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class ApiConfiguration {

}
