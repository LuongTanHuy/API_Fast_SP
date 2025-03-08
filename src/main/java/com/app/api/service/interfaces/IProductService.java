package com.app.api.service.interfaces;

import com.app.api.model.Category;
import com.app.api.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> listProducts();
    public List<Product> productOfTheSameType(int id_category);
    public List<Product> productOfShop(int id_store);
    public List<Category> categoryOfShop(int id_store);
    public List<Product> searchProductOfShop(int id_store, String search);
    public List<Product> searchProductOfTheSameType(int id_store, int id_category);

    public Product getProductDetail(int id);

    public boolean changeStatusProduct(int id);
    public boolean addProduct(Product productModel);
    public boolean updateProduct(Product productModel);
}
