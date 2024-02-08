package com.fintech.transaction.services;

import com.fintech.transaction.dto.request.CustomerRequest;
import com.fintech.transaction.dto.request.TransactionRequest;
import com.fintech.transaction.dto.response.AuthResponse;
import com.fintech.transaction.dto.response.CommonResponse;
import com.fintech.transaction.dto.response.CustomerResponse;
import com.fintech.transaction.entities.TransactionEntity;
import com.fintech.transaction.enums.AvailableEnum;
import com.fintech.transaction.enums.ErrorCodes;
import com.fintech.transaction.enums.OperationTypeEnum;
import com.fintech.transaction.exceptions.BaseException;
import com.fintech.transaction.mappers.TransactionMapper;
import com.fintech.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AuthService authService;

    private final CustomerService customerService;

    @Transactional
    public CommonResponse<String> topup(String authToken, TransactionRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if (Objects.isNull(authResponse)) {
            throw BaseException.of(ErrorCodes.FORBIDDEN);
        }
        CustomerResponse response = customerService.getCustomerByPin(authResponse.getPin());

        if (request.getAmount().intValue() < 0) {
            throw BaseException.of(ErrorCodes.MIN_AMOUNT);
        }
        CustomerRequest customer = new CustomerRequest();
        customer.setPin(authResponse.getPin());
        customer.setGsmNumber(request.getGsmNumber());
        customer.setBalance(response.getBalance().add(request.getAmount()));
        customerService.updateCustomerBalance(customer);

        transactionRepository.save(TransactionMapper.requestConvertToEntity(request, OperationTypeEnum.TOPUP));
        System.err.println(response.getBalance().add(request.getAmount()));

        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }

    @Transactional
    public CommonResponse<String> purchase(String authToken, TransactionRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if (Objects.isNull(authResponse)) {
            throw BaseException.of(ErrorCodes.FORBIDDEN);
        }
        CustomerResponse response = customerService.getCustomerByPin(authResponse.getPin());

        if (request.getAmount().intValue() < 0) {
            throw BaseException.of(ErrorCodes.MIN_AMOUNT);
        }

        CustomerRequest customer = new CustomerRequest();
        customer.setPin(authResponse.getPin());
        customer.setGsmNumber(request.getGsmNumber());
        BigDecimal amount = request.getAmount();
        customer.setBalance(response.getBalance()
                .subtract(amount)
        );
        customerService.updateCustomerBalance(customer);

        transactionRepository.save(TransactionMapper.requestConvertToEntity(request, OperationTypeEnum.PURCHASE));

        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }


    @Transactional
    public CommonResponse<String> refund(String authToken, TransactionRequest request) {

        AuthResponse authResponse = authService.getUserDetailsByJwtToken(authToken);

        if (Objects.isNull(authResponse)) {
            throw BaseException.of(ErrorCodes.FORBIDDEN);
        }
        CustomerResponse response = customerService.getCustomerByPin(authResponse.getPin());

        if (request.getAmount().intValue() < 0) {
            throw BaseException.of(ErrorCodes.MIN_AMOUNT);
        }

        List<TransactionEntity> entityList = transactionRepository.getAllByOperationTypeAndActive(OperationTypeEnum.PURCHASE, AvailableEnum.ACTIVE.getValue());

        if (entityList.isEmpty()) {
            throw BaseException.of(ErrorCodes.NO_ELIGIBLE_PURCHASE);
        }

        TransactionEntity lastPurchase = entityList.get(entityList.size() - 1);
        if (lastPurchase.getAmount().compareTo(request.getAmount()) < 0) {

            throw BaseException.of(ErrorCodes.EXCEEDS_REFUND);

        }
        lastPurchase.setAmount(lastPurchase.getAmount().subtract(request.getAmount()));
        transactionRepository.save(lastPurchase);

        transactionRepository.save(TransactionEntity
                .builder()
                .gsmNumber(lastPurchase.getGsmNumber())
                .operationType(OperationTypeEnum.REFUND)
                .amount(request.getAmount())
                .build());

        CustomerRequest customer = new CustomerRequest();
        customer.setPin(authResponse.getPin());
        customer.setGsmNumber(request.getGsmNumber());
        customer.setBalance(response.getBalance().add(request.getAmount()));
        customerService.updateCustomerBalance(customer);

        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }
}
