package org.room.apollo.server.service;

import org.room.apollo.server.configuration.DeezerConfiguration;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Service for authorization methods.
 */
@Service
public class AuthorizationService {

    private DeezerConfiguration deezerConfiguration;

    private RestTemplate template;

    @Autowired
    public AuthorizationService(DeezerConfiguration deezerConfiguration, RestTemplate template) {
        this.deezerConfiguration = deezerConfiguration;
        this.template = template;
    }

    /**
     * @return response to deezer api for authorization. In case of URISyntaxException return HttpCode 400(Bad Request).
     */
    public ResponseEntity<Object> getRedirectResponseForDeezerAuthorization() {
        try {
            String url = String.format("https://connect.deezer.com/oauth/auth.php" +
                            "?app_id=%s" +
                            "&redirect_uri=%s" +
                            "&perms=%s",
                    deezerConfiguration.getAppId(),
                    deezerConfiguration.getRedirectUrl(),
                    deezerConfiguration.getPermissions());
            URI deezer = new URI(url);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(deezer);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(e.getReason(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Make request with given code to deezer api for access token.
     *
     * @param code deezer code.
     * @return access token.
     */
    public DeezerToken getDeezerAccessToken(String code) {
        String url = "https://connect.deezer.com/oauth/access_token.php" +
                "?app_id={app_id}" +
                "&secret={secret}" +
                "&code={code}" +
                "&output=json";
        return template.getForObject(url,
                DeezerToken.class,
                deezerConfiguration.getAppId(),
                deezerConfiguration.getSecret(),
                code);
    }
}
