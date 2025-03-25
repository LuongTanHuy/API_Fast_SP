package com.app.api.dto;

import com.app.api.model.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO {

    private ProductDTO productDTO;
    private OrderDTO orderDTO;
    private Double price;
    private Integer quantity;

    public OrderItemDTO(OrderItem orderItem) {
        this.productDTO = new ProductDTO(orderItem.getProductModel());
        this.orderDTO = new OrderDTO(orderItem.getOrderModel());
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
