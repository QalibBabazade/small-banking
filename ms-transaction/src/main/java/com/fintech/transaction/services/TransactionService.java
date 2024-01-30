package com.fintech.transaction.services;

import com.fintech.transaction.dto.request.CustomerRequest;
import com.fintech.transaction.dto.request.TransactionRequest;
import com.fintech.transaction.dto.response.AuthResponse;
import com.fintech.transaction.dto.response.CommonResponse;
import com.fintech.transaction.dto.response.CustomerResponse;
import com.fintech.transaction.enums.ErrorCodes;
import com.fintech.transaction.enums.OperationTypeEnum;
import com.fintech.transaction.exceptions.BaseException;
import com.fintech.transaction.mappers.TransactionMapper;
import com.fintech.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AuthService authService;

    private final CustomerService customerService;

    public CommonResponse<String> topup(String authToken, TransactionRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if(Objects.isNull(authResponse)){
            throw BaseException.of(ErrorCodes.FORBIDDEN);
        }
        CustomerResponse response = customerService.getCustomerByPin(authResponse.getPin());

        CustomerRequest customer = new CustomerRequest();
        customer.setPin(authResponse.getPin());
        customer.setGsmNumber(request.getGsmNumber());
        customer.setBalance(response.getBalance().add(request.getAmount()));
        customerService.updateCustomerBalance(customer);

        transactionRepository.save(TransactionMapper.requestConvertToEntity(request, OperationTypeEnum.TOPUP));
        System.err.println(response.getBalance().add(request.getAmount()));

        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }

    public CommonResponse<String> purchase(String authToken, TransactionRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if(Objects.isNull(authResponse)){
            throw BaseException.of(ErrorCodes.FORBIDDEN);
        }
        CustomerResponse response = customerService.getCustomerByPin(authResponse.getPin());

        CustomerRequest customer = new CustomerRequest();
        customer.setPin(authResponse.getPin());
        customer.setGsmNumber(request.getGsmNumber());
        BigDecimal amount = request.getAmount();
        BigDecimal divideAmount = amount.divide(new BigDecimal("3"), 3, BigDecimal.ROUND_HALF_UP);
        customer.setBalance(response.getBalance()
                .subtract(amount)
                .add(divideAmount)
        );
        customerService.updateCustomerBalance(customer);

        transactionRepository.save(TransactionMapper.requestConvertToEntity(request, OperationTypeEnum.PURCHASE));
        request.setAmount(divideAmount);
        transactionRepository.save(TransactionMapper.requestConvertToEntity(request, OperationTypeEnum.REFUND));


        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }
}
