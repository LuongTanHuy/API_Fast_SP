package com.app.api.dto;

import com.app.api.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String image;
    private String role;
    private Boolean permission;
    private Timestamp created_at;
    private StoreDTO storeDTO;
    private Integer totalOrderDelivered;
    private Integer totalOrderBought;

    public AccountDTO(Account account, Integer id) {
        this.id = id;
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.address = account.getAddress();
        this.image = account.getImage();
        this.role = account.getRole();
        this.permission = account.getPermission();
        this.created_at = account.getCreated_at();
    }

    public AccountDTO(Account account) {
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.address = account.getAddress();
        this.image = account.getImage();
        this.role = account.getRole();
        this.permission = account.getPermission();
        this.created_at = account.getCreated_at();
        this.storeDTO = new StoreDTO(account.getStoreModel());
    }

    public AccountDTO(String username, String email, String phone, String address) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public AccountDTO(String username, String email, String image) {
        this.username = username;
        this.email = email;
        this.image = image;
    }
}
