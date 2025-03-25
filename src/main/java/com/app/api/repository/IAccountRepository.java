package com.app.api.repository;

import com.app.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAccountRepository extends JpaRepository<Account,Integer> {
    @Query("SELECT a FROM Account a WHERE a.username LIKE %:keyword% OR a.email LIKE %:keyword% OR a.phone LIKE %:keyword%")
    List<Account> searchAccounts(@Param("keyword") String keyword);
    @Query("SELECT a FROM Account a WHERE  a.email =:keyword ")
    Account findByEmail(@Param("keyword") String keyword);
    @Query("SELECT a FROM Account a ORDER BY a.created_at DESC ")
    List<Account> listAccountByTime();

    @Query("SELECT a FROM Account a WHERE a.storeModel.status = 2 ORDER BY a.created_at DESC ")
    List<Account> getAllByStatusStore();

    @Query("SELECT a FROM Account a WHERE a.storeModel.id =:idStore")
    Account findByStoreId(@Param("idStore") Integer idStore);
}
