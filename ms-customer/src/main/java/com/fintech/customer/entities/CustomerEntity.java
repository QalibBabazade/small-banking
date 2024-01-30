package com.fintech.customer.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    @NotNull
    String name;

    @Column(name = "surname")
    @NotNull
    String surname;

    @Column(name = "birthDate")
    LocalDate birthDate;

    @Column(name = "balance")
    @Min(value = 0, message = "Minimum amount must be 0")
    BigDecimal balance;

    @Column(name = "gsmNumber")
    @NotNull
    String gsmNumber;

    @Column(name = "pin")
    @NotNull
    String pin;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    LocalDateTime createdDate;

    Integer active;

}
