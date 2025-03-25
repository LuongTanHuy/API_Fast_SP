package com.app.api.repository;

import com.app.api.model.Customer;
import com.app.api.model.HistoryChat;
import com.app.api.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IChatRepository extends JpaRepository<Chat,Integer> {
    @Query("SELECT new com.app.api.model.Customer(am.image, am.username, cm.accountModel.id, cm.storeModel.id) " +
            "FROM Chat cm INNER JOIN cm.accountModel am WHERE cm.storeModel.id = ?1")
    List<Customer> listCustomer(int idStore);

    @Query("SELECT new com.app.api.model.HistoryChat(am.image, cm.storeModel.image,cm.message,cm.typeMessage,cm.created_at,cm.userSend,cm.accountModel.id,cm.storeModel.id)" +
            "FROM Chat cm INNER JOIN cm.accountModel am WHERE cm.accountModel.id = ?1 AND cm.storeModel.id = ?2")
    List<HistoryChat> historyChat(int idAccount,int idStore);
}
