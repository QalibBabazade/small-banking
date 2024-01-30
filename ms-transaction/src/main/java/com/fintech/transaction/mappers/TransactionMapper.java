package com.fintech.transaction.mappers;

import com.fintech.transaction.dto.request.TransactionRequest;
import com.fintech.transaction.entities.TransactionEntity;
import com.fintech.transaction.enums.OperationTypeEnum;

public class TransactionMapper {

    public static TransactionEntity requestConvertToEntity(TransactionRequest request, OperationTypeEnum operationType){
        return TransactionEntity.builder()
                .gsmNumber(request.getGsmNumber())
                .amount(request.getAmount())
                .operationType(operationType)
                .build();
    }
}
