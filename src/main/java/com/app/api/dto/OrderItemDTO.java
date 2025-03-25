package com.app.api.dto;

import com.app.api.model.OrderItem;

public class OderItemDTO {

    private ProductDTO productDTO;
    private Order categoryDTO;
    private Double price;
    private Integer quantity;

    public OderItemDTO(OrderItem orderItem) {
        this.productDTO = new ProductDTO(orderItem.getProductModel());
        this.categoryDTO = categoryDTO;
        this.price = price;
        this.quantity = quantity;
    }
}
