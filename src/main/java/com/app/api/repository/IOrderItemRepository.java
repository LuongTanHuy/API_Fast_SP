package com.app.api.repository;

import com.app.api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderItemRepository extends JpaRepository<OrderItem,Integer> {

    @Query("SELECT oi FROM OrderItem oi INNER JOIN oi.orderModel o WHERE o.accountModel.id = ?1 AND oi.orderModel.status = ?2")
    List<OrderItem> listOrderItem(int accountId, int status);

    @Query("SELECT oi FROM OrderItem oi INNER JOIN oi.orderModel o WHERE o.storeModel.id = ?1 AND oi.orderModel.status = ?2 ORDER BY oi.orderModel.created_at,oi.orderModel.receive_at DESC ")
    List<OrderItem> listOderItemOfStore(int storeId, int status);

    @Query("SELECT oi FROM OrderItem oi  WHERE  oi.orderModel.status = ?1 AND oi.orderModel.id_Shipper = 0 ")
    List<OrderItem> listOrderForShipper(int status);

    @Query("SELECT oi FROM OrderItem oi  WHERE  oi.orderModel.status = ?1 AND oi.orderModel.id_Shipper = ?2")
    List<OrderItem> listOrderOfShipper(int status, int idShipper);

    @Query("SELECT oi FROM OrderItem oi INNER JOIN oi.orderModel o WHERE o.storeModel.id = ?1 AND oi.orderModel.status = ?2 AND MONTH(oi.orderModel.receive_at) = ?3 AND YEAR(oi.orderModel.receive_at) = ?4")
    List<OrderItem> findAllByYear(int storeId, int status, int month, int year);

    @Query("SELECT oi FROM OrderItem oi  WHERE  oi.orderModel.id = ?1")
    Optional<OrderItem> findByIdOrder(int id);

    @Query("SELECT oi.productModel.id, SUM(oi.quantity) FROM OrderItem oi " +
            "WHERE oi.productModel.id IN (:idProduct) AND oi.orderModel.status = 3 " +
            "GROUP BY oi.productModel.id")
    List<Object[]> totalProductSold(@Param("idProduct") List<Integer> idProduct);

    @Query("SELECT oi.productModel.id, SUM(oi.price) FROM OrderItem oi " +
            "WHERE oi.productModel.id IN (:idProduct) AND oi.orderModel.status = 3 " +
            "GROUP BY oi.productModel.id")
    List<Object[]> totalRevenue(@Param("idProduct") List<Integer> idProduct);

}
