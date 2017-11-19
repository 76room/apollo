package org.room.apollo.server.configuration;

import org.room.apollo.server.repository.UserRepository;
import org.room.apollo.server.security.ApolloPasswordEncoder;
import org.room.apollo.server.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
