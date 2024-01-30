package com.fintech.customer.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse implements Serializable {

    String name;

    String surname;

    LocalDate birthDate;

    BigDecimal balance;

    String gsmNumber;

    String pin;
}
