package com.fintech.transaction.repositories;

import com.fintech.transaction.entities.TransactionEntity;

import com.fintech.transaction.enums.OperationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    List<TransactionEntity> getAllByOperationTypeAndActive(OperationTypeEnum operationType, Integer active);
}
