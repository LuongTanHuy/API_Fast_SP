package com.app.api.service.interfaces;

import com.app.api.model.Customer;
import com.app.api.model.HistoryChat;
import com.app.api.model.Chat;

import java.util.List;

public interface IChatService {
    public boolean addChat(Chat chatModel);
    public List<Customer> listCustomer(int id_store);
    List<HistoryChat> historyChat(int idAccount, int idStore);

}
