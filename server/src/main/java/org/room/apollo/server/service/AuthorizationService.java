package org.room.apollo.server.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.room.apollo.server.configuration.DeezerConfiguration;
import org.room.apollo.server.controller.RegistrationController;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.dto.deezer.DeezerUser;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.room.apollo.server.utils.UserValidationConstants.MAX_USERNAME_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.MIN_USERNAME_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.USERNAME_REGEXP;

/**
 * Service for authorization methods.
 */
@Service
public class AuthorizationService {

    private static final int GENERATED_PASSWORD_LENGTH = 30;
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final RestTemplate template;
    private final DeezerConfiguration deezerConfiguration;

    @Autowired
    public AuthorizationService(DeezerConfiguration deezerConfiguration,
                                RestTemplate template) {
        this.deezerConfiguration = deezerConfiguration;
        this.template = template;
    }

    /**
     * @return response to deezer api for authorization. In case of URISyntaxException return HttpCode 400(Bad Request).
     */
    public ResponseEntity<Object> getRedirectResponseForDeezerAuthorization() {
        String url = "";
        try {
            url = String.format("https://connect.deezer.com/oauth/auth.php" +
                            "?app_id=%s" +
                            "&redirect_uri=%s" +
                            "&perms=%s",
                    deezerConfiguration.getAppId(),
                    deezerConfiguration.getRedirectUrl(),
                    deezerConfiguration.getPermissions());
            LOG.debug("Url for deezer redirect to registration: {}", url);
            URI deezer = new URI(url);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(deezer);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            LOG.error("Cant create url for deezer from string: {} . Error was: ", url, e);
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
        LOG.debug("Url for deezer code is: {}", url);
        return template.getForObject(url,
                DeezerToken.class,
                deezerConfiguration.getAppId(),
                deezerConfiguration.getSecret(),
                code);
    }

    public RegistrationForm getUserDataFromDeezerApi(DeezerToken token) {
        String url = "https://api.deezer.com/user/me" +
                "?access_token=" + token.getAccessToken() +
                "&output=json";
        LOG.debug("Url for user data: ", url);
        DeezerUser user = template.getForObject(url, DeezerUser.class);
        LOG.debug("User data: {} , from url: ", user, url);
        return new RegistrationForm(user.getName(), generatePassword(GENERATED_PASSWORD_LENGTH), user.getEmail());
    }

    public String generatePassword(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    /**
     * Normalize username to match our requirements.
     */
    public boolean normalizeUsername(RegistrationForm user) {
        String username = user.getUsername();
        LOG.info("Checking is username: {} , already normalized.", username);
        if (isUsernameNotNormalized(username)) {
            LOG.warn("Username: {}, not normalized.", username);
            username = RandomStringUtils.random(15, true, true);
            LOG.debug("New username is: {}", username);
            user.setUsername(username);
            return false;
        }
        LOG.info("Username: {} , is already normalized.", username);
        return true;
    }

    private boolean isUsernameNotNormalized(String username) {
        if (username == null) {
            LOG.debug("Username is null.");
            return true;
        }
        if (!username.matches(USERNAME_REGEXP)) {
            LOG.debug("Username: {}  dont match regexp: {} .", username, USERNAME_REGEXP);
            return true;
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            LOG.debug("Username: {}  is shorter than allowed value: {} .", username, MIN_USERNAME_LENGTH);
            return true;
        }
        if (username.length() > MAX_USERNAME_LENGTH) {
            LOG.debug("Username: {}  is longer than allowed value: {} .", username, MAX_USERNAME_LENGTH);
            return true;
        }
        return false;
    }
}
