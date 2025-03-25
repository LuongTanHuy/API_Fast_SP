package com.app.api.repository;

import com.app.api.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBannerRepository extends JpaRepository<Banner,Integer> {
    @Query("SELECT a FROM Banner a WHERE a.text LIKE %:keyword% ")
    List<Banner> findByKeyword(@Param("keyword") String keyword);
}
