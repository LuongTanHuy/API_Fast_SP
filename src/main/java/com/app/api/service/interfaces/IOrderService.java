package com.app.api.service.interfaces;

import java.util.List;

public interface IOrderService {
    public int addOrder(int idAccount,int idStore);
    public boolean updateStatusListOrderWhenBuy(List<Integer> id_order, int status);
    public boolean updateStatus(int id_order, int status);
    public boolean updateStatusOrderWhenDeliveryComplete(int id_order, int status,int idShipper);
}
