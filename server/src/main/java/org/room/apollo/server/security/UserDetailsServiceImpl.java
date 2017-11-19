package org.room.apollo.server.security;

import org.room.apollo.server.entity.User;
import org.room.apollo.server.repository.UserRepository;
import org.room.apollo.server.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Our implementation that load user from Mongo.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Looking for username: {}", username);
        User user = getUserForCredentials(username);
        LOG.info("Found User: {} , for username: {}", user, username);
        return new UserDetailsImpl(user);
    }

    private User getUserForCredentials(String emailOrUsername) {
        return getUserByEmailOrUsername(emailOrUsername);
    }

    private User getUserByEmailOrUsername(String emailOrUsername) {
        User user = repository.findUserByEmail(emailOrUsername);
        if (user == null) {
            user = repository.findUserByUsername(emailOrUsername);
            if (user == null) {
                throw new UsernameNotFoundException("User not registered.");
            }
        }
        return user;
    }
}
