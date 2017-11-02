package org.room.apollo.server.dto.registration;

public class RegistrationResponse {

    private String username;

    private String email;

    private String message;

    public RegistrationResponse() {
    }

    public RegistrationResponse(String username, String email, String message) {
        this.username = username;
        this.email = email;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
