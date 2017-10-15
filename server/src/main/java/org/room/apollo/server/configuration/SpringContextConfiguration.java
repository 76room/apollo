package org.room.apollo.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for spring context. Add there beans.
 */
@Configuration
public class SpringContextConfiguration {

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
