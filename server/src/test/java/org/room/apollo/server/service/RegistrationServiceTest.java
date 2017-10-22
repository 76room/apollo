package org.room.apollo.server.service;

import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.dto.registration.RegistrationForm;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.repository.UserRepository;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void reInit() throws Exception {
        registrationService = new RegistrationService(userRepository);
    }

    @Test
    public void isUsernameAndEmailFree_ReturnTrue_IfNoSuchUsernameAndEmailInDatabase() throws RegistrationException {
        doReturn(new User("", "", "")).when(userRepository).findUserByUsername("username");
        doReturn(new User("", "", "")).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        Assert.assertTrue(registrationService.isUsernameAndEmaillFree(form));
    }

    @Test
    public void hashPassword_IsHashingPasswordBySHA256() {
        String expected = "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f";
        String result = registrationService.hashPassword("password123");
        Assert.assertTrue(expected.equals(result));
    }

    @Test(expected = RegistrationException.class)
    public void isUsernameAndEmailFree_throwRegistrationException_IfEmailInDatabase() throws RegistrationException {
        doReturn(new User("", "", "")).when(userRepository).findUserByUsername("username");
        doReturn(null).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        registrationService.isUsernameAndEmaillFree(form);
    }

    @Test(expected = RegistrationException.class)
    public void isUsernameAndEmailFree_throwRegistrationException_IfUsernameInDatabase() throws RegistrationException {
        doReturn(null).when(userRepository).findUserByUsername("username");
        doReturn(new User("", "", "")).when(userRepository).findUserByEmail("email@email.com");
        RegistrationForm form = new RegistrationForm("username", "password", "email@email.com");
        registrationService.isUsernameAndEmaillFree(form);
    }
}
