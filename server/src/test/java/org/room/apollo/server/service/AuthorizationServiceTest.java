package org.room.apollo.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.room.apollo.server.configuration.DeezerConfiguration;
import org.room.apollo.server.dto.deezer.DeezerToken;
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
}
