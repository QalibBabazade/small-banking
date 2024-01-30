package com.fintech.user.constants;

public interface Constants {
    String VALID_PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*[&%$]).{6,}$";
    String VALID_PASSWORD_MESSAGE = "Your password include at " +
            "least one upper, one lower and one numeric character.";
}
