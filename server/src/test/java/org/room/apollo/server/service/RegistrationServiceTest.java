package org.room.apollo.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder mockEncoder;

    @Before
    public void reInit() throws Exception {
        registrationService = new RegistrationService(userRepository, mockEncoder);
    }

    @Test
    public void isUsernameAndEmailFree_ReturnTrue_IfNoSuchUsernameAndEmailInDatabase() throws RegistrationException {
        doReturn(null).when(userRepository).findUserByUsername("username");
        doReturn(null).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        Assert.assertTrue(registrationService.isUsernameAndEmaillFree(form));
    }

    @Test(expected = RegistrationException.class)
    public void isUsernameAndEmailFree_throwRegistrationException_IfEmailInDatabase() throws RegistrationException {
        doReturn(null).when(userRepository).findUserByUsername("username");
        doReturn(new User("", "", "email@email.com")).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        registrationService.isUsernameAndEmaillFree(form);
    }

    @Test(expected = RegistrationException.class)
    public void isUsernameAndEmailFree_throwRegistrationException_IfUsernameInDatabase() throws RegistrationException {
        doReturn(new User("username", "", "")).when(userRepository).findUserByUsername("username");
        doReturn(null).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        registrationService.isUsernameAndEmaillFree(form);
    }
}
