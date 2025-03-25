package com.app.api.repository;

import com.app.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment,Integer> {
    @Query("select cm from Comment cm where cm.productModel.id = :idProduct ORDER BY cm.created_at DESC ")
    List<Comment> findByProductModelId(@Param("idProduct")Integer idProduct);
}
