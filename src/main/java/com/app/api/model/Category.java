package com.app.api.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="category")
    private String category;
    @Column(name="sale")
    private int sale;
    @Column(name="status")
    private int status ;
    @OneToMany(mappedBy="categoryModel")
    private Set<Product> products;
    @ManyToOne
    @JoinColumn(name="id_store", nullable=false)
    private Store storeModel;

    public Category() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Store getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(Store storeModel) {
        this.storeModel = storeModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
