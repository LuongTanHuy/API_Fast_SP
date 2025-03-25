package com.app.api.repository;

import com.app.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT o.id_Shipper, COUNT(o) FROM Order o WHERE o.id_Shipper IN :accountIds GROUP BY o.id_Shipper")
    List<Object[]> totalOrderDelivered(@Param("accountIds") List<Integer> accountIds);

    @Query("SELECT o.accountModel.id, COUNT(o) FROM Order o WHERE o.accountModel.id IN :accountIds AND o.status = :status GROUP BY o.accountModel.id")
    List<Object[]> totalOrder(@Param("accountIds") List<Integer> accountIds, @Param("status") Integer status);

}
