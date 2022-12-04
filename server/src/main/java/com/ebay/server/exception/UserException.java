package com.ebay.server.exception;

public class UserException extends CustomException {
    public static final String USERNAME_TAKEN = "This username is already taken";
    public static final String EMAIL_TAKEN = "There is already an account using this email";

    public UserException(String message) {
        super(message);
    }
}
