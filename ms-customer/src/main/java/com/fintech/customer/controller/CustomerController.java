package com.fintech.customer.controller;

import com.fintech.customer.dto.request.CustomerRequest;
import com.fintech.customer.dto.request.UpdateCustomerRequest;
import com.fintech.customer.dto.response.CommonResponse;
import com.fintech.customer.dto.response.CustomerResponse;
import com.fintech.customer.entities.CustomerEntity;
import com.fintech.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/create")
    public CommonResponse<CustomerEntity> create(@RequestHeader("authorization") String authToken,
                                                 @RequestBody @Valid CustomerRequest request) {
        return customerService.createCustomer(authToken, request);
    }

    @GetMapping("/get-by-pin/{pin}")
    public CustomerResponse customerByPin(@PathVariable("pin") String pin){
        return customerService.customerByPin(pin);
    }


    @PostMapping("/update-balance")
    public CustomerResponse updateBalance(@RequestBody UpdateCustomerRequest request){
        return customerService.updateBalance(request);
    }
}
