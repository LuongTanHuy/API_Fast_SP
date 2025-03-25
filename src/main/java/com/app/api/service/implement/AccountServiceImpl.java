package com.app.api.service.implement;

import com.app.api.dto.AccountDTO;
import com.app.api.model.Account;
import com.app.api.repository.IAccountRepository;
import com.app.api.repository.IOrderRepository;
import com.app.api.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final TokenServiceImpl tokenService;
    private final FileStorageServiceImpl fileStorageService;
    private final IAccountRepository accountRepository;
    private final IOrderRepository orderRepository;
    public final Boolean LOCK_ACCOUNT = true;
    public final Boolean UNLOCK_ACCOUNT = false;
    public final Integer ORDERBOUGHT = 3;

    @Override
    @Transactional
    public boolean update(AccountDTO account, String token, MultipartFile file) {
        String nameImage = !file.isEmpty() ? this.fileStorageService.storeFile(file) : null;

        Integer idAccount = this.tokenService.validateTokenAndGetId(token);
        if(idAccount.equals(null)){ return false;};

        Optional<Account> optionAccount = accountRepository.findById(idAccount);
        if (optionAccount.isPresent()) {
            Account updateAccount = optionAccount.get();

            if (account.getUsername() != null && !account.getUsername().isEmpty()) {
                updateAccount.setUsername(account.getUsername());
            }
            if (account.getEmail() != null && !account.getEmail().isEmpty()) {
                updateAccount.setEmail(account.getEmail());
            }
            if (account.getPhone() != null && !account.getPhone().isEmpty()) {
                updateAccount.setPhone(account.getPhone());
            }
            if (account.getAddress() != null && !account.getAddress().isEmpty()) {
                updateAccount.setAddress(account.getAddress());
            }
            if (nameImage != null) { updateAccount.setImage(nameImage);}

            return this.accountRepository.save(updateAccount).getId() > 0;

        }
        return false;
    }

    @Override
    public AccountDTO accountDetail(String token) {
        try {
            Integer idAccount =this.tokenService.validateTokenAndGetId(token);
            if(idAccount.equals(0)){ return null;};
            AccountDTO result = new AccountDTO(this.accountRepository.findById(idAccount).get());
            return result.getPermission() == true ? result : null;
        } catch (Exception e) {
            Integer idSore =this.tokenService.validateTokenAndGetId(token);
            if(idSore.equals(0)){ return null;};
            AccountDTO result = new AccountDTO(this.accountRepository.findByStoreId(idSore));
            return result.getPermission() == true ? result : null;
        }
    }

    private List<AccountDTO> convertToDTO(List<Account> listAccount){
        List<Integer> accountIds = listAccount.stream().map(Account::getId).collect(Collectors.toList());

        Map<Integer, Integer> totalOrderDeliveredMap = this.orderRepository.totalOrderDelivered(accountIds)
                .stream()
                .collect(Collectors.toMap(row -> (Integer) row[0], row -> row[1] != null ? ((Number) row[1]).intValue() : 0));

        Map<Integer, Integer> totalOrderBoughtMap = this.orderRepository.totalOrder(accountIds, ORDERBOUGHT)
                .stream()
                .collect(Collectors.toMap(row -> (Integer) row[0], row -> row[1] != null ? ((Number) row[1]).intValue() : 0));


        return listAccount.stream().map(account -> {
            AccountDTO accountDTO = new AccountDTO(account,account.getId());
            accountDTO.setTotalOrderDelivered(totalOrderDeliveredMap.getOrDefault(account.getId(), 0));
            accountDTO.setTotalOrderBought(totalOrderBoughtMap.getOrDefault(account.getId(), 0));
            return accountDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> listAccount(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPage = this.accountRepository.findAll(pageable);
        List<Account> listAccount = accountPage.getContent();
        return convertToDTO(listAccount);
    }

    @Override
    public List<AccountDTO> search(String information){
        return convertToDTO(this.accountRepository.searchAccounts(information));
    }

    @Override
    public boolean accessPermission(Integer idAccount) {
        Optional<Account> optionalAccount = this.accountRepository.findById(idAccount);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Boolean currentPermission = account.getPermission();
            Boolean newPermission = (currentPermission == UNLOCK_ACCOUNT) ? LOCK_ACCOUNT : UNLOCK_ACCOUNT;
            account.setPermission(newPermission);

            return this.accountRepository.save(account).getId() > 0;
        }
        return false;
    }

}
