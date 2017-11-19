package org.room.apollo.server.dto.login;

import org.room.apollo.server.entity.User;
import org.room.apollo.server.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class SigninResponse {

    private String username;

    private String email;

    private String message;

    public SigninResponse(Authentication authentication, String message) {
        this.message = message;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails instanceof UserDetailsImpl) {
            User user = ((UserDetailsImpl) userDetails).getUser();
            email = user.getEmail();
            username = user.getUsername();
        }
    }

    public SigninResponse(String username, String email, String message) {
        this.username = username;
        this.email = email;
        this.message = message;
    }

    public SigninResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SigninResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SigninResponse)) return false;

        SigninResponse response = (SigninResponse) o;

        if (username != null ? !username.equals(response.username) : response.username != null) return false;
        if (email != null ? !email.equals(response.email) : response.email != null) return false;
        return message != null ? message.equals(response.message) : response.message == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
