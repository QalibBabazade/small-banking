package com.fintech.transaction.entities;

import com.fintech.transaction.enums.OperationTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class TransactionEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "gsm_number")
    @NotNull
    String gsmNumber;

    @Column(name = "amount")
    @NotNull
    BigDecimal amount;

    @Column(name = "operation_type")
    @NotNull
    OperationTypeEnum operationType;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    LocalDateTime createdDate;

    @ColumnDefault(value = "1")
    Integer active;
}
