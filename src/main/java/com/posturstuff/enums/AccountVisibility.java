package com.posturstuff.enums;

public enum AccountVisibility {

    PUBLIC(1),
    PRIVATE(2);

    private final int code;

    AccountVisibility(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static AccountVisibility valueOf(int code) {
        for(AccountVisibility value : AccountVisibility.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid AccountVisibility code");
    }

    public String toString() {
        return this.name();
    }

}
