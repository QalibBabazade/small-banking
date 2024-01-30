package com.fintech.customer.enums;

public enum AvailableEnum {

    ACTIVE(1),
    DE_ACTIVE(0);


    private final int value;

    private AvailableEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
