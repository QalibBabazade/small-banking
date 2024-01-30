package com.fintech.transaction.exceptions;


import com.fintech.transaction.enums.ErrorCodes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {

    Integer code;
    String message;

    public static BaseException of(ErrorCodes errorCodes){
        return new BaseException(errorCodes.getCode(),errorCodes.getMessage());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
