package com.fintech.transaction.services;

import com.fintech.transaction.client.CustomerClient;
import com.fintech.transaction.dto.request.CustomerRequest;
import com.fintech.transaction.dto.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerClient customerClient;

    public CustomerResponse getCustomerByPin(String pin) {
        return customerClient.getCustomerByPin(pin);
    }

    public CustomerResponse updateCustomerBalance(CustomerRequest request) {
        return customerClient.updateCustomerBalance(request);
    }

}
