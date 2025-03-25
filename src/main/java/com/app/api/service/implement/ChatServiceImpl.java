package com.app.api.service.implement;

import com.app.api.model.Customer;
import com.app.api.model.HistoryChat;
import com.app.api.model.Chat;
import com.app.api.repository.IChatRepository;
import com.app.api.service.interfaces.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private IChatRepository chatRepository;

    @Override
    public boolean addChat(Chat chatModel) {
        return this.chatRepository.save(chatModel).getId() > 0 ?true:false;
    }

    @Override
    public List<Customer> listCustomer(int id_store) {
        List<Customer> customers = this.chatRepository.listCustomer(id_store);
        Map<String, Customer> uniqueCustomers = new HashMap<>();

        for (Customer customer : customers) {
            uniqueCustomers.putIfAbsent(String.valueOf(customer.getIdAccount()), customer);
        }
        return new ArrayList<>(uniqueCustomers.values());
    }

    @Override
    public List<HistoryChat> historyChat(int idAccount, int idStore) {
        return this.chatRepository.historyChat(idAccount,idStore);
    }


}
