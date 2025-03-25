package com.app.api.dto;

import com.app.api.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private int id;
    private String name;
    private String image;
    private double price;
    private int status;
    private CategoryDTO categoryModel;
    private Integer totalProductSold;
    private Double totalRevenue;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.status = product.getStatus();
        this.categoryModel = new CategoryDTO(product.getCategoryModel());
    }

    public void setTotalProductSold(int totalProductSold) {
        this.totalProductSold = totalProductSold;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
