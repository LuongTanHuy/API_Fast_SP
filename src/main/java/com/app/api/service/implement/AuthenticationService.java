package com.app.api.service.implement;

import com.app.api.dto.AuthDTO;
import com.app.api.model.Account;
import com.app.api.repository.IAccountRepository;
import com.app.api.service.interfaces.IAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthentication {

    private final IAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenService;

    @Override
    public AuthDTO login(String email, String password) {
        Account accountModels = this.accountRepository.findByEmail(email);

        if (accountModels != null) {
            if (this.passwordEncoder.matches(password, accountModels.getPassword())) {
                if (accountModels.getPermission() == true) {
                    if(accountModels.getRole().equals("store")){
                        String accessToken = this.tokenService.generateToken(accountModels.getStoreModel().getId());
                        String refreshToken = this.tokenService.generateRefreshToken(accountModels.getStoreModel().getId());

                        return new AuthDTO(accessToken, refreshToken);
                    }
                    String accessToken = this.tokenService.generateToken(accountModels.getId());
                    String refreshToken = this.tokenService.generateRefreshToken(accountModels.getId());

                    return new AuthDTO(accessToken, refreshToken);
                }
            }
        }
        return null;
    }


    @Override
    public String signUp(String Email, String Password) {
//        Account accountModel = new Account();
//        accountModel.setUsername("user");
//        accountModel.setStatus(0);
//        accountModel.setEmail(Email);
//        accountModel.setImage("imagedefault.jpg");
//        accountModel.setPassword(this.passwordEncoder.encode(Password));
//        accountModel.setPermission("2");
        return null;
    }

    @Override
    public String refreshToken(String refreshToken) {
        if (refreshToken != null) {
            return this.tokenService.refreshToken(refreshToken);
        }
        return null;
    }

}
