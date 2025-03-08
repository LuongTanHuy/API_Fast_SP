package com.app.api.repository;

import com.app.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT a FROM Product a WHERE a.name LIKE %:keyword% AND a.categoryModel.storeModel.id =:idStore ORDER BY a.categoryModel.category desc")
    List<Product> searchProductOfShop(@Param("idStore") int idStore, @Param("keyword") String keyword);

    @Query("SELECT a FROM Product a WHERE a.categoryModel.storeModel.id = :idStore ORDER BY a.categoryModel.category desc")
    List<Product> productOfShop(@Param("idStore") int idStore);

    @Query("SELECT a FROM Product a WHERE a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc ")
    List<Product> productOfTheSaneType(@Param("idCategory") int idCategory);

    @Query("SELECT a FROM Product a WHERE a.categoryModel.storeModel.id = :idStore AND a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc")
    List<Product> searchProductOfTheSameType(@Param("idStore") int idStore, @Param("idCategory") int idCategory);

    @Query("SELECT a FROM Product a ORDER BY a.categoryModel.category desc")
    List<Product> getProductSale();
}
