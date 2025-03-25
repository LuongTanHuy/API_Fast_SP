package com.app.api.service.interfaces;

import com.app.api.dto.OrderItemDTO;
import com.app.api.model.OrderItem;

import java.util.List;

public interface IOrderItemService {

    public Integer add(Integer idOrder, Integer idProduct,Integer quantity);


    public boolean updatePrice(List<Integer> id_order);
    public boolean updateQuantity(OrderItem orderItemModel);

    public List<OrderItemDTO> listOrderItem(String token, Integer status);
    public List<OrderItemDTO> listOderToShipperChose(Integer statusOrderItem);
    public List<OrderItemDTO> listOderItemOfStore(String idStore, Integer status);
    public List<OrderItemDTO> listOrderOfShipper(Integer status, String idShipper);

    public int[] getQuantityOrder(int idStore);
    public double[] getPriceOrder(int idStore,int year );

}
