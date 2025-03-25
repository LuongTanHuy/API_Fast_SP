package com.app.api.service.interfaces;

import com.app.api.dto.AuthDTO;

public interface IAuthentication {
    public AuthDTO login(String email, String password);

    public String signUp(String email, String password);

}
