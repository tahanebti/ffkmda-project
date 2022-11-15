package com.tahanebti.ffkmda.phone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {
    
    TEL("Tel"),
    MOBILE("Mobile"),
    PRO("Pro");

    private final String value;
}