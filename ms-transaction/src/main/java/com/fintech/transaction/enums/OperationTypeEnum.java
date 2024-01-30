package com.fintech.transaction.enums;

public enum OperationTypeEnum {
    TOPUP(1),
    PURCHASE(0),
    REFUND(2);


    private final int value;

    private OperationTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
