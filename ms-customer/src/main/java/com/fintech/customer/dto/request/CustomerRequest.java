package com.fintech.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest implements Serializable {

    String name;

    String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    BigDecimal balance;

    String gsmNumber;
}
