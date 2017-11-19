package org.room.apollo.server.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.service.RegistrationService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationService registrationService;

    @Before
    public void reInit() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RegistrationController(registrationService))
                .build();
    }

    @Test
    public void registerNewUser_isReturningNewRegisteredUser() throws Exception {
        doReturn(true).when(registrationService).isUsernameAndEmaillFree(any(RegistrationForm.class));
        doReturn(new User("username", "hash", "example@example.com")).when(registrationService).
                createNewUser(any(RegistrationForm.class));
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", "password123")
                .param("email", "example@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"message\" : \"Successfully registered.\",\n" +
                        "  \"username\" : \"username\",\n" +
                        "  \"email\" : \"example@example.com\"\n" +
                        "}"));
    }

    @Test
    public void registerNewUser_isReturningBadRequest_IfUserAlreadyRegistered() throws Exception {
        doThrow(new RegistrationException("Test exception message."))
                .when(registrationService)
                .isUsernameAndEmaillFree(any(RegistrationForm.class));
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "existed")
                .param("password", "password123")
                .param("email", "existed@email.com"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\n" +
                        "  \"message\" : \"Test exception message.\"\n" +
                        "}"));
    }

    @Test
    public void registerNewUser_isReturningBadRequest_IfFormIsNotValid_ForUsername() throws Exception {
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "tiny")
                .param("password", "password123")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("password", "password123")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "too_big_1111111111111111111111111111")
                .param("password", "password123")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "!forbidden!")
                .param("password", "password123")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerNewUser_isReturningBadRequest_IfFormIsNotValid_ForPassword() throws Exception {
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", "small")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        StringBuilder bigPassword = new StringBuilder();
        for (int i = 0; i < 33; i++) {
            bigPassword.append("1");
        }
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", bigPassword.toString())
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", "forbidden!!!!!!")
                .param("email", "example@email.com"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerNewUser_isReturningBadRequest_IfFormIsNotValid_ForEmail() throws Exception {
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", "password"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "username")
                .param("password", "password")
                .param("email", "invalid"))
                .andExpect(status().isBadRequest());
    }
}
