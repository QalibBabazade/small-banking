package com.fintech.customer.services;

import com.fintech.customer.dto.request.CustomerRequest;
import com.fintech.customer.dto.request.UpdateCustomerRequest;
import com.fintech.customer.dto.response.AuthResponse;
import com.fintech.customer.dto.response.CommonResponse;
import com.fintech.customer.dto.response.CustomerResponse;
import com.fintech.customer.entities.CustomerEntity;
import com.fintech.customer.enums.AvailableEnum;
import com.fintech.customer.enums.ErrorCodes;
import com.fintech.customer.exceptions.BaseException;
import com.fintech.customer.mappers.CustomerMappers;
import com.fintech.customer.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AuthService authService;

    public CommonResponse<CustomerEntity> createCustomer(String authToken, CustomerRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if (Objects.isNull(request) || Objects.isNull(request.getName()) || Objects.isNull(request.getSurname())
                || Objects.isNull(request.getGsmNumber()) || Objects.isNull(request.getBalance())) {
            throw BaseException.of(ErrorCodes.INVALID_ARGUMENTS);
        }

        if(request.getBalance().intValue() < 100){
            throw BaseException.of(ErrorCodes.MINIMUM_AMOUNT);
        }

        CustomerEntity entity = customerRepository.save(CustomerMappers.requestToEntity(request, authResponse.getPin()));

        return CommonResponse.successInstance(entity);
    }

    public CustomerResponse customerByPin(String pin) {

        if (Objects.isNull(pin)) {
            throw BaseException.of(ErrorCodes.INVALID_ARGUMENTS);
        }

        CustomerEntity entity = customerRepository.getCustomerEntitiesByPinAndActive(pin, AvailableEnum.ACTIVE.getValue())
                .orElseThrow(() -> BaseException.of(ErrorCodes.NOT_FOUND));

        return CustomerMappers.entityToResponse(entity);
    }

    public CustomerResponse updateBalance(UpdateCustomerRequest request) {

        CustomerEntity entity = customerRepository.getCustomerEntitiesByPinAndGsmNumberAndActive(request.getPin(), request.getGsmNumber(), AvailableEnum.ACTIVE.getValue())
                .orElseThrow(() -> BaseException.of(ErrorCodes.NOT_FOUND));

        if(Objects.isNull(request.getBalance())){
            throw BaseException.of(ErrorCodes.INVALID_ARGUMENTS);
        }

        entity.setBalance(request.getBalance());

        return CustomerMappers.entityToResponse(customerRepository.save(entity));
    }
}
