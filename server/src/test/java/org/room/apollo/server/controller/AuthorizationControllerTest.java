package org.room.apollo.server.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.room.apollo.server.dto.deezer.DeezerToken;
import org.room.apollo.server.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorizationController.class)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationService authorizationService;

    @Test
    public void testThatSigninWithDeezer_OnStep2_ReturnTokenAndPutInSession_IfNoErrors() throws Exception {
        DeezerToken token = new DeezerToken("tokenMock", 10);
        doReturn(token)
                .when(authorizationService).getDeezerAccessToken("mock");

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/signin/deezer/step2").param("code", "mock").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{ \n" +
                        "  \"expires\" : 10,\n" +
                        "  \"access_token\" : \"tokenMock\"\n" +
                        "}"));
        Assert.assertEquals(session.getAttribute("deezerToken"), token);
    }

    @Test
    public void testThatSigninWithDeezer_OnStep2_ReturnBadRequestAndError_InCaseOfErrorParameter() throws Exception {
        mockMvc.perform(get("/signin/deezer/step2").param("error_reason", "error mock"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\n" +
                        "  \"message\" : \"error mock\"\n" +
                        "  }"));
    }
}
