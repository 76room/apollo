package org.room.apollo.server.dto.login;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status of Registration.
 */
public enum RegistrationAdditionalRequests {

    CHANGE_PASSWORD("Request for change password"),

    CHANGE_USERNAME("Request for change username");

    private String request;

    RegistrationAdditionalRequests(String request) {
        this.request = request;
    }

    @JsonValue
    public String getRequest() {
        return request;
    }
}
