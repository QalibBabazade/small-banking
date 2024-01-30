package com.fintech.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {

    SUCCESS("Successfully operation!",0),
    BAD_REQUEST("Bad request!",11),
    USER_ALREADY_EXITS("User already exits",12),
    INTERNAL_SERVER_ERROR("Internal server error!",10),
    NOT_FOUND("Not found for input parameter!",20),
    PIN_NOT_FOUND("Wrong pin!!!!",21),

    INVALID_ARGUMENTS("Invalid arguments!",30),

    PASSWORD_INCORRECT("Password is incorrect",31);

    private final String message;
    private final Integer code;


    public String getMessage() {
        return this.message;
    }

    public Integer getCode(){
        return this.code;
    }

}
