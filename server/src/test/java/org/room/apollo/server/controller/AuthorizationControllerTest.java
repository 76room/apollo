package org.room.apollo.server.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.room.apollo.server.dto.login.SigninForm;
import org.room.apollo.server.dto.login.SigninResponse;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.service.AuthenticationService;
import org.room.apollo.server.service.AuthorizationService;
import org.room.apollo.server.service.RegistrationService;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private RegistrationService registrationService;

    private RegistrationForm user;

    @Before
    public void reInit() throws Exception {
        user = new RegistrationForm("username", "password", "email");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthorizationController(authorizationService,
                        authenticationService,
                        registrationService)).build();
    }

    @Test
    public void signinWithDeezer_OnStep2_PutTokenInSession_IfNoErrors() throws Exception {
        DeezerToken token = new DeezerToken("tokenMock", 10);
        doReturn(token)
                .when(authorizationService).getDeezerAccessToken("mock");

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/signin/deezer/step2").param("code", "mock").session(session))
                .andExpect(status().is(302));

        Assert.assertEquals(session.getAttribute("deezerToken"), token);
    }

    @Test
    public void signinWithDeezer_OnStep2_ReturnBadRequestAndError_InCaseOfErrorParameter() throws Exception {
        mockMvc.perform(get("/signin/deezer/step2").param("error_reason", "error mock"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\n" +
                        "  \"message\" : \"error mock\"\n" +
                        "  }"));
    }

    @Test
    public void signinWithDeezer_OnStep3_SigninUser_IfTokenInSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        DeezerToken token = new DeezerToken("mock", 0);
        session.setAttribute("deezerToken", token);
        returnUserDataForToken(token);
        registerUser(user);
        signinUser(user);
        mockMvc.perform(get("/signin/deezer/step3").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("  {\n" +
                        "    \"email\": \"email\",\n" +
                        "    \"username\": \"username\",\n" +
                        "    \"message\": \"Successfully sign in user.\"\n" +
                        "  }\n"));
    }

    @Test
    public void signinWithDeezer_OnStep3_ReturnError_IfNoTokeInSession() throws Exception {
        mockMvc.perform(get("/signin/deezer/step3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\n" +
                        "  \"message\": \"Cant register user.\"\n" +
                        "}"));

    }

    @Test
    public void  signinWithDeezer_OnStep3_ReturnError_IfFailToRegisterUser() throws Exception {
        MockHttpSession session = new MockHttpSession();
        DeezerToken token = new DeezerToken("mock", 0);
        session.setAttribute("deezerToken", token);
        returnUserDataForToken(token);
        failToRegister(user);
        mockMvc.perform(get("/signin/deezer/step3").session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\n" +
                        "  \"message\" : \"Fail to signin user.\"\n" +
                        "}"));

    }

    private void registerUser(RegistrationForm user) throws RegistrationException {
        doReturn(true).when(authorizationService).normalizeUsername(user);
        doReturn(true).when(registrationService).isUsernameAndEmaillFree(user);
        doNothing().when(registrationService).registerUserFromExternalAPI(user);
    }

    private void returnUserDataForToken(DeezerToken token) {
        doReturn(user)
                .when(authorizationService)
                .getUserDataFromDeezerApi(token);
    }

    private void signinUser(RegistrationForm user) {
        SigninResponse response = new SigninResponse(user.getUsername(),
                user.getEmail(), "Successfully sign in user.");
        doReturn(response).when(authenticationService).signinUserFromExternalAPI(any(SigninForm.class));
    }

    private void failToRegister(RegistrationForm user) throws RegistrationException {
        doReturn(true).when(authorizationService).normalizeUsername(user);
        doReturn(true).when(registrationService).isUsernameAndEmaillFree(user);
        doNothing().when(registrationService).registerUserFromExternalAPI(user);
        doThrow(new IllegalArgumentException("Fail to signin user."))
                .when(authenticationService).signinUserFromExternalAPI(any(SigninForm.class));
    }
}
