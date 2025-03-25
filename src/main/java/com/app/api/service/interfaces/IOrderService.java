package com.app.api.service.interfaces;

import java.util.Map;

public interface IOrderService {
    public Integer add(String token,Integer idStore);
    public boolean updateStatus(Integer idOrder, Integer status);
    public boolean updateStatusUserAndShipper(Map<String,Object> requestData, String authorizationHeader);
}
