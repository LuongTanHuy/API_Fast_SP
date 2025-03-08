package com.app.api.service.interfaces;

import com.app.api.model.OrderItem;

import java.util.List;

public interface IOrderItemService {

    public boolean updatePriceForOrderItem(List<Integer> id_order);
    public boolean updateQuantityForOrderItem(OrderItem orderItemModel);

    public List<OrderItem> listOrderItem(int id, int status);
    public List<OrderItem> listOrderForShipper(int statusOrderItem);
    public List<OrderItem> listOderItemOfStore(int id_store, int status);
    public List<OrderItem> listOrderOfShipper(int status, int idShipper);

    public int[] getQuantityOrder(int idStore);
    public int add(int id_order, int id_product,int quantity);
    public double[] getPriceOrder(int idStore,int year );

}
