package com.sharif.edu.hw1.Model;

import lombok.Getter;

@Getter
public enum Error {
    USER_LOGGED_IN_SUCCESSFULLY("user logged in successfully"),
    USER_REGISTERED_SUCCESSFULLY("user registered successfully, pending activation"),
    USER_NAME_NOT_FOUND ("user name not found"),
    USER_NAME_IN_ACTIVE("user name not active"),
    USER_NAME_LOGGED_IN_TIME_EXPIRED ("user name logged in time expired"),
    PASSWORD_INCORRECT ("password incorrect"),
    USER_NAME_ALREADY_EXIST ("user name already exist"),
    USER_ACTIVATED_SUCCESSFULLY( "user activated successfully");



    public final String errorDesc;
    private Error(String errorCode) {
        this.errorDesc = errorCode;
    }

}
