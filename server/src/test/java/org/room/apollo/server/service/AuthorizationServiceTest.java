package org.room.apollo.server.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.room.apollo.server.configuration.DeezerConfiguration;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.dto.deezer.DeezerUser;
import org.room.apollo.server.dto.registration.RegistrationForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    private RestTemplate mockTemplate;

    private DeezerConfiguration mockConfigs;

    @Before
    public void reInit() {
        mockConfigs = mock(DeezerConfiguration.class);
        mockTemplate = mock(RestTemplate.class);
        authorizationService = new AuthorizationService(mockConfigs, mockTemplate);
    }

    @Test
    public void testThatGetRedirectResponseForDeezerAuthorization_ReturnRedirectResponse_IfUrlWasOk() {
        doReturn("mockId").when(mockConfigs).getAppId();
        doReturn("mockRedirect").when(mockConfigs).getRedirectUrl();
        doReturn("mockPerms").when(mockConfigs).getPermissions();
        ResponseEntity<Object> response = authorizationService.getRedirectResponseForDeezerAuthorization();
        String expectedUrl = "https://connect.deezer.com/oauth/auth.php" +
                "?app_id=mockId&redirect_uri=mockRedirect&perms=mockPerms";
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SEE_OTHER);
        Assert.assertEquals(response.getHeaders().getLocation().toString(), expectedUrl);
    }

    @Test
    public void testThatGetRedirectResponseForDeezerAuthorization_ReturnBadRequest_IfUrlWasCorrupted() {
        doReturn("\"").when(mockConfigs).getAppId();
        ResponseEntity<Object> response = authorizationService.getRedirectResponseForDeezerAuthorization();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(response.getBody(), "Illegal character in query");
        System.out.println(response.toString());
    }

    @Test
    public void testGetDeezerAccessToken_ReturnDeezerToken_ForValidJson() throws IOException {
        doReturn("appId").when(mockConfigs).getAppId();
        doReturn("secret").when(mockConfigs).getSecret();
        doReturn(new DeezerToken("mock token", 11))
                .when(mockTemplate)
                .getForObject(anyString(), eq(DeezerToken.class),
                        eq("appId"), eq("secret"), eq("code"));
        DeezerToken expected = new DeezerToken("mock token", 11);
        Assert.assertEquals(authorizationService.getDeezerAccessToken("code"), expected);
    }

    @Test
    public void getUserDataFromDeezerApi_IsReturningRegistrationForm_WithGeneratedPassword() {
        doReturn(new DeezerUser("username", "email"))
                .when(mockTemplate)
                .getForObject(anyString(), eq(DeezerUser.class));
        RegistrationForm userData = authorizationService.getUserDataFromDeezerApi(new DeezerToken("token", 0));
        Assert.assertEquals("username", userData.getUsername());
        Assert.assertEquals("email", userData.getEmail());
        Assert.assertEquals(30, userData.getPassword().length());
    }

    @Test
    public void normalizeUsername_returnTrueIfUsernameIsValid() {
        RegistrationForm user = new RegistrationForm();
        user.setUsername("valid");
        Assert.assertTrue(authorizationService.normalizeUsername(user));
    }

    @Test
    public void normalizeUsername_returnFalseIfUsernameIsInValid() {
        RegistrationForm user = new RegistrationForm();
        Assert.assertFalse(authorizationService.normalizeUsername(user));
        user.setUsername("tiny");
        Assert.assertFalse(authorizationService.normalizeUsername(user));
        user.setUsername("!invalid!");
        Assert.assertFalse(authorizationService.normalizeUsername(user));
        user.setUsername("too_long_11111111111111111111111111111");
        Assert.assertFalse(authorizationService.normalizeUsername(user));
    }
}
