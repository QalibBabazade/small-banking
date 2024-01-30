package com.fintech.customer.mappers;

import com.fintech.customer.dto.request.CustomerRequest;
import com.fintech.customer.dto.response.CustomerResponse;
import com.fintech.customer.entities.CustomerEntity;
import com.fintech.customer.enums.AvailableEnum;

public class CustomerMappers {

    public static CustomerEntity requestToEntity(CustomerRequest request, String pin) {
        return CustomerEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .birthDate(request.getBirthDate())
                .balance(request.getBalance())
                .gsmNumber(request.getGsmNumber())
                .pin(pin)
                .active(AvailableEnum.ACTIVE.getValue())
                .build();
    }

    public static CustomerResponse entityToResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .balance(entity.getBalance())
                .birthDate(entity.getBirthDate())
                .gsmNumber(entity.getGsmNumber())
                .pin(entity.getPin())
                .build();
    }
}
