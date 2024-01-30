package com.fintech.transaction.dto.response;


import com.fintech.transaction.enums.ErrorCodes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonResponse<T> implements Serializable {

    Integer code;
    String message;
    LocalDateTime timestamp;
    T data;

    public static <T> CommonResponse<T> successInstance(T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .code(ErrorCodes.SUCCESS.getCode())
                .message(ErrorCodes.SUCCESS.getMessage())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
}
