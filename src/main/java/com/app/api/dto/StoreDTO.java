package com.app.api.dto;

import com.app.api.model.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {

    private Integer id;
    private String name;
    private String image;
    private String address;
    private String email;
    private String phone;

    public StoreDTO() {
    }

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.image = store.getImage();
        this.address = store.getAddress();
        this.email = store.getEmail();
        this.phone = store.getPhone();
    }


}
