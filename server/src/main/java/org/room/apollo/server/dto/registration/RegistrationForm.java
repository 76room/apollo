package org.room.apollo.server.dto.registration;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static org.room.apollo.server.utils.UserValidationConstants.MAX_PASSWORD_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.MAX_USERNAME_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.MIN_PASSWORD_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.MIN_USERNAME_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.PASSWORD_REGEXP;
import static org.room.apollo.server.utils.UserValidationConstants.USERNAME_REGEXP;

public class RegistrationForm {

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Pattern(regexp = USERNAME_REGEXP)
    private String username;

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    @Pattern(regexp = PASSWORD_REGEXP)
    private String password;

    @NotNull
    @Email
    private String email;

    public RegistrationForm() {
    }

    public RegistrationForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationForm)) return false;

        RegistrationForm that = (RegistrationForm) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
