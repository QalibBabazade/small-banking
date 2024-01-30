package com.fintech.transaction.client;

import com.fintech.transaction.dto.request.CustomerRequest;
import com.fintech.transaction.dto.response.CustomerResponse;
import com.fintech.transaction.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${client.ms-customer.by-pin.url}")
    private String customerByPinUrl;

    @Value("${client.ms-customer.update-balance.url}")
    private String updateBalanceUrl;

    public CustomerResponse getCustomerByPin(String pin) {
        try {
            String expandedUrl = UriComponentsBuilder.fromUriString(customerByPinUrl)
                    .buildAndExpand(pin)
                    .toUriString();
            return restTemplate.getForObject(expandedUrl, CustomerResponse.class);
        } catch (HttpStatusCodeException ex) {
            throw new BaseException(ex.getStatusCode().value(), ex.getMessage());
        }
    }

    public CustomerResponse updateCustomerBalance(CustomerRequest request) {
        try {
            return restTemplate.postForObject(updateBalanceUrl, new HttpEntity<>(request), CustomerResponse.class);
        } catch (HttpStatusCodeException ex) {
            throw new BaseException(ex.getStatusCode().value(), ex.getMessage());
        }
    }
}
