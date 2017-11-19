package org.room.apollo.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.dto.login.SigninForm;
import org.room.apollo.server.dto.login.SigninResponse;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.repository.UserRepository;
import org.room.apollo.server.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
    private User user;
    @Mock
    private AuthenticationManager manager;
    @Mock
    private UserRepository repository;
    private AuthenticationService authenticationService;

    @Before
    public void reInit() throws Exception {
        user = new User("username", "password", "email@email.com");
        authenticationService = new AuthenticationService(manager, repository);
    }

    @Test
    public void canSigninIfUserCanBeFoundByEmail() {
        User userThatWasFound = new User("username", "password", "email@email.com");
        authenticationAuthorizeUser(new UserDetailsImpl(userThatWasFound));
        userWasFoundByEmail(userThatWasFound);
        SigninForm given = new SigninForm("email@email.com", "password");
        SigninResponse expected = new SigninResponse();
        expected.setUsername("username");
        expected.setEmail("email@email.com");
        expected.setMessage("Successfully sign in user.");
        Assert.assertEquals(expected, authenticationService.signinUserFromExternalAPI(given));
    }

    @Test
    public void canSigninIfUserCanBeFoundByUsername() {
        authenticationAuthorizeUser(new UserDetailsImpl(user));
        userWasFoundByUsername(user);
        SigninForm given = new SigninForm("username", "password");
        SigninResponse expected = new SigninResponse();
        expected.setUsername("username");
        expected.setEmail("email@email.com");
        expected.setMessage("Successfully sign in user.");
        Assert.assertEquals(expected, authenticationService.signinUserFromExternalAPI(given));
    }

    @Test(expected = IllegalArgumentException.class)
    public void notSigninUserIfItNotFound() {
        SigninForm given = new SigninForm("username", "password");
        authenticationService.signinUserFromExternalAPI(given);
    }

    private void authenticationAuthorizeUser(UserDetailsImpl user) {
        Authentication response = new UsernamePasswordAuthenticationToken(user, null);
        doReturn(response).when(manager).authenticate(any());
    }

    private void userWasFoundByEmail(User founded) {
        doReturn(founded).when(repository).findUserByEmail(founded.getEmail());
    }

    private void userWasFoundByUsername(User founded) {
        doReturn(founded).when(repository).findUserByEmail(founded.getUsername());
    }
}
