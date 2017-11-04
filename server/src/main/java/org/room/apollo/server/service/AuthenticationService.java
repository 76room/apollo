package org.room.apollo.server.service;

import org.room.apollo.server.controller.RegistrationController;
import org.room.apollo.server.dto.login.SigninForm;
import org.room.apollo.server.dto.login.SigninResponse;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
public class AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    /**
     * Signin user that come from external api.
     *
     * @param signinData signin from.
     * @return signing response with user data and token.
     * @throws AuthenticationException if wrong credentials was provided.
     */
    @Transactional
    public SigninResponse signinUserFromExternalAPI(@Valid SigninForm signinData) {
        User user = getUserByEmailOrUsername(signinData.getUsernameOrEmail());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword());
        LOG.info("Trying authenticate User: {} , with token: {}", user, token);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new SigninResponse(authentication, "Successfully sign in user.");
        } catch (AuthenticationException e) {
            LOG.error("Cant authenticate User: {}, with token: {}, for Signin data: {}.", user, token, signinData);
            throw e;
        }
    }

    public User getUserByEmailOrUsername(String emailOrUsername) {
        User user = userRepository.findUserByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findUserByUsername(emailOrUsername);
            if (user == null) {
                throw new IllegalArgumentException("User not registered.");
            }
        }
        return user;
    }
}
