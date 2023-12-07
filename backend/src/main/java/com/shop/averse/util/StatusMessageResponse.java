package com.shop.averse.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusMessageResponse {
    public String message;
    public boolean status;

    public boolean getStatus() {
        return this.status;
    }
}