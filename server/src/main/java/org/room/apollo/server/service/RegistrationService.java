package org.room.apollo.server.service;

import com.google.common.hash.Hashing;
import org.room.apollo.server.dto.registration.RegistrationForm;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.exception.RegistrationException;
import org.room.apollo.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
public class RegistrationService {

    private final UserRepository repository;

    @Autowired
    public RegistrationService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param form Registration form.
     * @return true if username and email are free.
     * @throws RegistrationException if username or email already taken.
     */
    @Transactional
    public boolean isUsernameAndEmaillFree(RegistrationForm form) throws RegistrationException {
        if (repository.findUserByEmail(form.getEmail()) != null) {
            throw new RegistrationException("Email is already taken by another user.");
        }
        if (repository.findUserByUsername(form.getUsername()) != null) {
            throw new RegistrationException("Username is already taken by another user.");
        }
        return true;
    }

    /**
     * @param form Registration form.
     * @return Create new user in data source.
     */
    @Transactional
    public User createNewUser(RegistrationForm form) {
        String passwordHash = hashPassword(form.getPassword());
        return repository.insert(new User(form.getUsername(), passwordHash, form.getEmail()));
    }

    /**
     * Calculate hash of given password, current algorithm is SHA-256
     *
     * @param password string password.
     * @return hashed string.
     */
    public String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
