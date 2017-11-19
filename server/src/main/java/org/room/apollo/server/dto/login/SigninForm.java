package org.room.apollo.server.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static org.room.apollo.server.utils.UserValidationConstants.MAX_PASSWORD_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.MIN_PASSWORD_LENGTH;
import static org.room.apollo.server.utils.UserValidationConstants.PASSWORD_REGEXP;

public class SigninForm {

    @JsonProperty("username")
    @NotNull
    private String usernameOrEmail;

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    @Pattern(regexp = PASSWORD_REGEXP)
    private String password;

    public SigninForm(RegistrationForm userData) {
        this.usernameOrEmail = userData.getUsername();
        this.password = userData.getPassword();
    }

    public SigninForm(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    @Override
    public String toString() {
        return "SigninForm{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
