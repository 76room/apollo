package org.room.apollo.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeezerConfiguration {

    @Value("${deezer.app_id}")
    private String appId;

    @Value("${deezer.redirect_url}")
    private String redirectUrl;

    @Value("${deezer.perms}")
    private String permissions;

    @Value("${deezer.secret}")
    private String secret;

    public String getAppId() {
        return appId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getSecret() {
        return secret;
    }
}
