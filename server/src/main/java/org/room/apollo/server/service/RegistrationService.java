package org.room.apollo.server.service;

import org.room.apollo.server.controller.RegistrationController;
import org.room.apollo.server.dto.login.RegistrationForm;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;


@Service
public class RegistrationService {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param form Registration form.
     * @return true if username and email are free.
     * @throws RegistrationException if username or email already taken.
     */
    @Transactional
    public boolean isUsernameAndEmaillFree(RegistrationForm form) throws RegistrationException {
        if (repository.findUserByEmail(form.getEmail()) != null) {
            LOG.info("Email: {} already used.", form.getEmail());
            throw new RegistrationException("Email is already taken by another user.");
        }
        if (repository.findUserByUsername(form.getUsername()) != null) {
            LOG.info("Username: {} already used.", form.getUsername());
            throw new RegistrationException("Username is already taken by another user.");
        }
        return true;
    }

    /**
     * @param form Registration form.
     * @return Create new user in data source.
     */
    @Transactional
    public User createNewUser(@Valid RegistrationForm form) {
        String passwordHash = passwordEncoder.encode(form.getPassword());
        User newUser = new User(form.getUsername(), passwordHash, form.getEmail());
        LOG.info("Creating new user: {} .", newUser);
        return repository.insert(newUser);
    }

    public void registerUserFromExternalAPI(RegistrationForm userData) {
        try {
            isUsernameAndEmaillFree(userData);
            LOG.info("User {} is not registered yet in system registering.", userData);
            createNewUser(userData);
        } catch (RegistrationException e) {
            LOG.info("User already registered: {}", userData);
        }
    }
}
