package com.app.api.dto;

import com.app.api.model.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListStoreDTO {

    private Integer id;
    private String name;
    private String image;
    private String address;
    private String email;
    private String phone;
    private Timestamp createdAt;
    private Integer totalOrdersSold;
    private Double revenue;
    public List<String> listCategory;

    public ListStoreDTO(Store store, Integer totalOrdersSold, Double revenue, List<String> listCategory) {
        this.id = store.getId();
        this.name = store.getName();
        this.image = store.getImage();
        this.address = store.getAddress();
        this.email = store.getEmail();
        this.phone = store.getPhone();
        this.createdAt = store.getCreated_at();
        this.totalOrdersSold = totalOrdersSold;
        this.revenue = revenue;
        this.listCategory = listCategory;
    }
}
