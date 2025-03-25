package com.app.api.dto;

import lombok.Data;

@Data
public class AuthDTO {
    String accessToken;
    String refreshToken;

    public AuthDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
