package com.app.api.dto;

import com.app.api.model.Order;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {

    private AccountDTO accountDTO;
    private Timestamp createdAt;
    private Timestamp receiverAt;

    public OrderDTO(Order order) {
        this.accountDTO = new AccountDTO(order.getAccountModel().getUsername(), order.getAccountModel().getEmail(), order.getAccountModel().getPhone(), order.getAccountModel().getAddress());
        this.createdAt = order.getCreated_at();
        this.receiverAt = order.getReceive_at();
    }
}
