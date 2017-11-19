package org.room.apollo.server.security;

import com.google.common.hash.Hashing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApolloPasswordEncoder implements PasswordEncoder {

    private String salt = "some_salt"; //TODO add property to .yml salt

    /**
     * Calculate hash of given password, current algorithm is SHA-256
     *
     * @param rawPassword not encoded password.
     * @return hashed string.
     */
    private static String hashPassword(CharSequence rawPassword) {
        return Hashing.sha256().hashUnencodedChars(rawPassword).toString();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String result = rawPassword + salt;
        result = hashPassword(result);
        return result;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = hashPassword(rawPassword + salt);
        return hashedPassword.equals(encodedPassword);
    }
}
