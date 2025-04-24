package com.app.api.repository;

import com.app.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Integer> {
//    @Query("SELECT a FROM Product a WHERE a.name LIKE %:keyword% AND a.categoryModel.storeModel.id =:idStore ORDER BY a.categoryModel.category desc")
//    List<Product> searchProductOfShop(@Param("idStore") int idStore, @Param("keyword") String keyword);
//
//    @Query("SELECT a FROM Product a WHERE a.categoryModel.storeModel.id = :idStore ORDER BY a.categoryModel.category desc")
//    List<Product> productOfShop(@Param("idStore") int idStore);
//
//    @Query("SELECT a FROM Product a WHERE a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc ")
//    List<Product> productOfTheSameType(@Param("idCategory") int idCategory);
//
//    @Query("SELECT a FROM Product a WHERE a.categoryModel.storeModel.id = :idStore AND a.categoryModel.id = :idCategory ORDER BY a.categoryModel.category desc")
//    List<Product> searchProductOfTheSameType(@Param("idStore") int idStore, @Param("idCategory") int idCategory);


    // Tìm kiếm theo từ khóa và cửa hàng
    List<Product> findByNameContainingAndCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(String keyword, Integer idStore);

    // Lấy sản phẩm theo cửa hàng (có phân trang)
    Page<Product> findByCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(int idStore, Pageable pageable);

    // Lấy sản phẩm theo loại
    List<Product> findByCategoryModelIdOrderByCategoryModelCategoryDesc(int idCategory,Pageable pageable);

    // Lấy sản phẩm theo cửa hàng và loại
    List<Product> findByCategoryModelStoreModelIdAndCategoryModelIdOrderByCategoryModelCategoryDesc(int idStore, int idCategory,Pageable pageable);

    // Lấy tất ca sản phẩm
    List<Product> findByCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(int idStore);

}
