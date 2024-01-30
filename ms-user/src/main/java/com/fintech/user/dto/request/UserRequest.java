package com.fintech.user.dto.request;

import com.fintech.user.constants.Constants;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest implements Serializable {

    @NotEmpty(message = "Pin must be provided")
    String pin;

    @NotEmpty(message = "Password must be provided")
    @Pattern(regexp = Constants.VALID_PASSWORD_REGEXP, message = Constants.VALID_PASSWORD_MESSAGE)
    String password;
}
