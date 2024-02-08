package com.fintech.transaction.controller;


import com.fintech.transaction.dto.request.TransactionRequest;
import com.fintech.transaction.dto.response.CommonResponse;
import com.fintech.transaction.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/top-up")
    public CommonResponse<String> topup(@RequestHeader("authorization") String authToken,
                                        @RequestBody @Valid TransactionRequest request){
        return  transactionService.topup(authToken,request);
    }

    @PostMapping("/purchase")
    public CommonResponse<String> purchase(@RequestHeader("authorization") String authToken,
                                           @RequestBody @Valid TransactionRequest request){
        return  transactionService.purchase(authToken,request);
    }

    @PostMapping("/refund")
    public CommonResponse<String> refund(@RequestHeader("authorization") String authToken,
                                           @RequestBody @Valid TransactionRequest request){
        return  transactionService.refund(authToken,request);
    }

}
