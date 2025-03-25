package com.app.api.service.interfaces;

import com.app.api.dto.AccountDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAccountService {

    public boolean update(AccountDTO account, String token, MultipartFile file);
    public boolean accessPermission(Integer id);
    public List<AccountDTO> listAccount(Integer page,Integer size);
    public List<AccountDTO> search(String value);
    public AccountDTO accountDetail(String token);

}
