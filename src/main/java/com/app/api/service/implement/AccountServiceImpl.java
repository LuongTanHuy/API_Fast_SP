package com.app.api.service.implement;

import com.app.api.model.Account;
import com.app.api.model.Store;
import com.app.api.repository.IAccountRepository;
import com.app.api.repository.IOrderRepository;
import com.app.api.repository.IStoreRepository;
import com.app.api.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {
    private final IAccountRepository accountRepository;
    private final IStoreRepository storeRepository;
    private final IOrderRepository orderRepository;
    public static final int LOCK_ACCOUNT = 1;
    public static final int UNLOCK_ACCOUNT = 0;
    public static final String OPEN_STORE = "1";
    public static final String DELETE_STORE = "2";
    public static final int ORDERBOUGHT = 3;

    @Autowired
    public AccountServiceImpl(IAccountRepository accountRepository, IStoreRepository storeRepository, IOrderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean updateOtp(Account _account) {
        Optional<Account> account = this.accountRepository.findById(_account.getId());

        if(account.isPresent()){
            Account update_account = account.get();
            update_account.setOtp(_account.getOtp());

            return this.accountRepository.save(update_account).getId() > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateIdStore(int idStore,int idAccount) {
        Optional<Account> account = accountRepository.findById(idAccount);
        if (account.isPresent()) {
            Account updateAccount = account.get();
            Store storeModel = new Store();
            storeModel.setId(idStore);
            updateAccount.setStoreModel(storeModel);

            return this.accountRepository.save(updateAccount).getId() > 0;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean updateProfile(Account _account) {
        Optional<Account> account = accountRepository.findById(_account.getId());
        if (account.isPresent()) {
            Account updateAccount = account.get();

            if (_account.getUsername() != null && !_account.getUsername().isEmpty()) {
                updateAccount.setUsername(_account.getUsername());
            }
            if (_account.getEmail() != null && !_account.getEmail().isEmpty()) {
                updateAccount.setEmail(_account.getEmail());
            }
            if (_account.getPhone() != null && !_account.getPhone().isEmpty()) {
                updateAccount.setPhone(_account.getPhone());
            }
            if (_account.getAddress() != null && !_account.getAddress().isEmpty()) {
                updateAccount.setAddress(_account.getAddress());
            }
            if (_account.getImage() != null && !_account.getImage().isEmpty()) {
                updateAccount.setImage(_account.getImage());
            }

            return this.accountRepository.save(updateAccount).getId() > 0;

        }
        return false;
    }

    @Override
    public boolean agreeOpenStore(int idAccount) {
        try {
            Optional<Account> account = this.accountRepository.findById(idAccount);
            if (account.isPresent()) {
                Account updateAccount = account.get();
                updateAccount.setPermission(OPEN_STORE);

                Store store = updateAccount.getStoreModel();
                if (store != null) {
                    Optional<Store> getStoreModel = this.storeRepository.findById(store.getId());
                    if (getStoreModel.isPresent()) {
                        Store updateStore = getStoreModel.get();
                        updateStore.setStatus(Integer.parseInt(OPEN_STORE));

                        this.storeRepository.save(updateStore);
                        this.accountRepository.save(updateAccount);
                        return true;
                    }
                }
            }
            return false;
        } catch (NullPointerException e) {
            System.err.println(STR."NullPointerException in agreeOpenStore: \{e.getMessage()}");
        } catch (Exception e) {
            System.err.println(STR."Exception in agreeOpenStore: \{e.getMessage()}");
        }
        return false;
    }

    @Override
    public boolean NotAgreeOpenStore(int idAccount) {
        try {
            Optional<Account> getAccount = this.accountRepository.findById(idAccount);
            if (getAccount.isPresent()) {
                Account updateAccount = getAccount.get();

                Store store = updateAccount.getStoreModel();
                if (store != null) {
                    Optional<Store> getStoreModel = this.storeRepository.findById(store.getId());
                    if (getStoreModel.isPresent()) {
                        Store updateStore = getStoreModel.get();
                        updateStore.setStatus(Integer.parseInt(DELETE_STORE));

                        this.storeRepository.save(updateStore);
                        return true;
                    }
                }
            }
            return false;
        } catch (NullPointerException e) {
            // Ghi lại lỗi nếu cần thiết
            System.err.println(STR."NullPointerException in NotAgreeOpenStore: \{e.getMessage()}");
        } catch (Exception e) {
            // Ghi lại lỗi nếu cần thiết
            System.err.println(STR."Exception in NotAgreeOpenStore: \{e.getMessage()}");
        }
        return false;
    }

    @Override
    public List<Account> getAllRequestOpenStore() {
        return this.accountRepository.getAllByStatusStore();
    }

    @Override
    public boolean deleteAccount(int idAccount) {
        this.accountRepository.deleteById(idAccount);
        return true;
    }

    @Override
    public Account accountProfile(int id, String WebOrApp) {
        Account accountProfile = new Account();

        accountProfile.setId(this.accountRepository.findById(id).get().getId());
        accountProfile.setUsername(this.accountRepository.findById(id).get().getUsername());
        accountProfile.setEmail(this.accountRepository.findById(id).get().getEmail());
        accountProfile.setAddress(this.accountRepository.findById(id).get().getAddress());
        accountProfile.setStatus(this.accountRepository.findById(id).get().getStatus());
        accountProfile.setImage(this.accountRepository.findById(id).get().getImage());
        accountProfile.setPhone(this.accountRepository.findById(id).get().getPhone());
        accountProfile.setPermission(this.accountRepository.findById(id).get().getPermission());
        accountProfile.setCreated_at(this.accountRepository.findById(id).get().getCreated_at());

        if (WebOrApp.equals("Web")) {accountProfile.setStoreModel(this.accountRepository.findById(id).get().getStoreModel());}

        return accountProfile;
    }

    @Override
    public Account getAccountDetailForWeb(int id) {
        Account accountModel = new Account();
        accountModel.setUsername(this.accountRepository.findById(id).get().getUsername());
        accountModel.setEmail(this.accountRepository.findById(id).get().getEmail());
        accountModel.setAddress(this.accountRepository.findById(id).get().getAddress());
        accountModel.setStatus(this.accountRepository.findById(id).get().getStatus());
        accountModel.setImage(this.accountRepository.findById(id).get().getImage());
        accountModel.setPhone(this.accountRepository.findById(id).get().getPhone());
        accountModel.setPermission(this.accountRepository.findById(id).get().getPermission());
        accountModel.setCreated_at(this.accountRepository.findById(id).get().getCreated_at());
        accountModel.setStoreModel(this.accountRepository.findById(id).get().getStoreModel());
        return accountModel;
    }

    @Override
    public List<Account> pageAccounts(Pageable pageable) {

        Page<Account> pageAccounts = this.accountRepository.findAll(pageable);
        List<Account> before_ListAccounts= pageAccounts.toList();
        List<Account> after_ListAccounts = new ArrayList<>();

        for(int i = 0;i< before_ListAccounts.size();i++){
             int totalOrderDelivered = this.orderRepository.totalOrderDelivered(before_ListAccounts.get(i).getId());
             int totalOrderBought = this.orderRepository.totalOrderBought(before_ListAccounts.get(i).getId(),ORDERBOUGHT);

            Account accountModel = new Account();
            accountModel.setUsername(before_ListAccounts.get(i).getUsername());
            accountModel.setEmail(before_ListAccounts.get(i).getEmail());
            accountModel.setAddress(before_ListAccounts.get(i).getAddress());
            accountModel.setStatus(before_ListAccounts.get(i).getStatus());
            accountModel.setImage(before_ListAccounts.get(i).getImage());
            accountModel.setPhone(before_ListAccounts.get(i).getPhone());
            accountModel.setPermission(before_ListAccounts.get(i).getPermission());
            accountModel.setCreated_at(before_ListAccounts.get(i).getCreated_at());
            accountModel.setTotalOrderDelivered(totalOrderDelivered > 0 ? totalOrderDelivered : 0);
            accountModel.setTotalOrderBought(totalOrderBought > 0 ? totalOrderBought : 0);

             after_ListAccounts.add(i,accountModel);
        }

        return after_ListAccounts;
    }

    @Override
    public int totalAccount(){
        return this.accountRepository.findAll().size();
    }

    @Override
    public List<Account> searchAccounts(String information){
        return this.accountRepository.searchAccounts(information);
    }
    @Override
    public boolean lockAccount(int idAccount) {
        Optional<Account> optionalAccount = this.accountRepository.findById(idAccount);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            int currentStatus = account.getStatus();
            int newStatus = (currentStatus == UNLOCK_ACCOUNT) ? LOCK_ACCOUNT : UNLOCK_ACCOUNT;
            account.setStatus(newStatus);

            return this.accountRepository.save(account).getId() > 0;
        }
        return false;
    }

    @Override
    public boolean checkEmail(String Email){
        return this.accountRepository.findByEmail(Email).size() > 0 ? true:false;
    }

    @Override
    public boolean checkOtp(int idAccount, String otp) {
        if (this.accountRepository.findById(idAccount).get().getId() > 0 && this.accountRepository.findById(idAccount).get().getOtp().equals(otp)) {
                Optional<Account> account = this.accountRepository.findById(idAccount);
                if (account.isPresent()) {
                    Account update_account = account.get();
                    update_account.setStatus(1);
                    this.accountRepository.save(update_account);
                    return true;
                }
        }else{
            this.accountRepository.deleteById(idAccount);
            return false;
        }
        return false;
    }

}
