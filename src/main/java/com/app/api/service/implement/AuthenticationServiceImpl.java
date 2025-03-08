package com.app.api.service.implement;

import com.app.api.model.Account;
import com.app.api.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
public class AuthenticationServiceImpl {

    @Autowired
    private IAccountRepository accountRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Account login(String email, String password) {
        List<Account> accountModels = this.accountRepository.findByEmail(email);
        if (!accountModels.isEmpty()) {
            for (Account accountModel : accountModels) {
                if (this.passwordEncoder.matches(password, accountModel.getPassword())) {
                    return accountModel;
                }
            }
        }
        return null;
    }

    public Account signUp(String Email, String Password) {
        Account accountModel = new Account();
        accountModel.setUsername("user");
        accountModel.setStatus(0);
        accountModel.setEmail(Email);
        accountModel.setImage("imagedefault.jpg");
        accountModel.setPassword(this.passwordEncoder.encode(Password));
        accountModel.setPermission("2");
        return this.accountRepository.save(accountModel);
    }

}
