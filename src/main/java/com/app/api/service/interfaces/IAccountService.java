package com.app.api.service.interfaces;

import com.app.api.model.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAccountService {

    public boolean updateOtp(Account _account);
    public boolean updateProfile(Account _account);
    public boolean agreeOpenStore(int idAccount);
    public boolean NotAgreeOpenStore(int idAccount);
    public boolean lockAccount(int id);
    public boolean updateIdStore(int idStore,int idAccount);
    public boolean checkEmail(String Email);
    public boolean checkOtp(int idAccount, String otp);
    public int totalAccount();
    public boolean deleteAccount(int id);
    public List<Account> getAllRequestOpenStore();
    public List<Account> pageAccounts(Pageable pageable);
    public List<Account> searchAccounts(String value);
    public Account accountProfile(int id, String WebOrApp);
    public Account getAccountDetailForWeb(int id);

}
