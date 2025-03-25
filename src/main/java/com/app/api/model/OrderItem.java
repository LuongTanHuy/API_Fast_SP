package com.app.api.model;

import jakarta.persistence.*;

@Entity
@Table(name="orderitem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product productModel;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_order",referencedColumnName = "id")
    private Order orderModel;

    public OrderItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProductModel() {
        return productModel;
    }

    public void setProductModel(Product productModel) {
        this.productModel = productModel;
    }

    public Order getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }
}
