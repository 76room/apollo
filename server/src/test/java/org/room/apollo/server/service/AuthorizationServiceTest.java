package org.room.apollo.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.room.apollo.server.configuration.DeezerConfiguration;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    private DeezerConfiguration mockConfigs;

    private HttpUtil mockHttp;

    @Before
    public void reInit() {
        mockConfigs = spy(DeezerConfiguration.class);
        mockHttp = mock(HttpUtil.class);
        authorizationService = new AuthorizationService(mockConfigs, mockHttp);
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
        doReturn("{\n" +
                "  \"access_token\": \"mock token\",\n" +
                "  \"expires\": 11\n" +
                "}").when(mockHttp).doGet(anyString());
        DeezerToken expected = new DeezerToken("mock token", 11);
        Assert.assertEquals(authorizationService.getDeezerAccessToken("whatever"), expected);


    }
}
