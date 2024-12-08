package com.posturstuff.enums;

public enum PostVisibility {

    PUBLIC(1),
    PRIVATE(2);

    public final int code;

    PostVisibility(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static PostVisibility valueOf(int code) {
        for(PostVisibility value : PostVisibility.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid PostVisibility code");
    }

    public String toString() {
        return this.name();
    }

}
