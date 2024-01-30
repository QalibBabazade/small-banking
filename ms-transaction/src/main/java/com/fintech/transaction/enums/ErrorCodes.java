package com.fintech.transaction.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {

    SUCCESS("Successfully operation!",0),
    BAD_REQUEST("Bad request!",11),
    INTERNAL_SERVER_ERROR("Internal server error!",10),
    FORBIDDEN("Forbiden", 13),
    NOT_FOUND("Not found for input parameter!",20),
    INVALID_ARGUMENTS("Invalid arguments!",30);

    private final String message;
    private final Integer code;


    public String getMessage() {
        return this.message;
    }

    public Integer getCode(){
        return this.code;
    }

}
