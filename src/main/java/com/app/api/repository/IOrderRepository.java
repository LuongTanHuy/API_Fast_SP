package com.app.api.repository;

import com.app.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT COUNT(*) AS totalOrder FROM Order o WHERE o.id_Shipper = ?1 ")
    int totalOrderDelivered(int accountId);

    @Query("SELECT COUNT(*) AS totalOrderBought FROM Order o WHERE o.accountModel.id = ?1 AND o.status = ?2")
    int totalOrderBought(int accountId, int status);
}
