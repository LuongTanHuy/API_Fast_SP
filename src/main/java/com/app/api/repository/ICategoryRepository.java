package com.app.api.repository;

import com.app.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT a FROM Category a WHERE  a.storeModel.id =:id_store ")
    List<Category> getAllByIdStore(@Param("id_store") int id_store);


}
