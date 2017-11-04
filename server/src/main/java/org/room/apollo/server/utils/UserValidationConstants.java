package org.room.apollo.server.utils;

public final class UserValidationConstants {

    public static final int MIN_USERNAME_LENGTH = 5;
    public static final int MAX_USERNAME_LENGTH = 16;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final String USERNAME_REGEXP = "[a-zA-Z0-9_.]+";
    public static final String PASSWORD_REGEXP = "[a-zA-Z0-9_]+";

    private UserValidationConstants() {
    }


}
