package com.app.api.service.implement;

import com.app.api.model.Account;
import com.app.api.model.Order;
import com.app.api.model.Store;
import com.app.api.repository.IOrderRepository;
import com.app.api.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final TokenServiceImpl tokenService;
    public static final Integer PRODUCT_IS_AWAITING_ACCEPT = 1;
    public static final Integer PRODUCT_IS_SHIPPING = 2;
    public static final Integer PRODUCT_IS_DELIVERED = 3;
    public static final Integer PRODUCT_CANCELED = 4;


    @Override
    public Integer add(String token, Integer idStore) {
            Order orderModel = new Order();

            Account accountModel = new Account();
            accountModel.setId(this.tokenService.validateTokenAndGetId(token));

            Store storeModel = new Store();
            storeModel.setId(idStore);

            orderModel.setAccountModel(accountModel);
            orderModel.setStoreModel(storeModel);
            return this.orderRepository.save(orderModel).getId();
    }

    //Web
    @Override
    public boolean updateStatus(Integer idOrder, Integer status) {
        Optional<Order> orderModel = this.orderRepository.findById(idOrder);
        if (orderModel.isPresent()) {
            Order updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            this.orderRepository.save(updateOrderModel);
            return true;
        }
        return false;
    }

    public boolean updateStatusListOrderWhenBuy_User(List<Integer> idOrder, Integer status) {
        for(int i= 0 ;i < idOrder.size();i++){
            Optional<Order> orderModel = this.orderRepository.findById(idOrder.get(i));
            if (orderModel.isPresent()) {
                Order updateOrderModel = orderModel.get();
                updateOrderModel.setStatus(status);
                this.orderRepository.save(updateOrderModel);
            }
        }
        return true;
    }

    public boolean updateStatusOrder_Shipper(Integer idOrder, Integer status, Integer idShipper) {
        Optional<Order> orderModel = this.orderRepository.findById(idOrder);
        if (orderModel.isPresent()) {
            Order updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            updateOrderModel.setId_Shipper(idShipper);
            this.orderRepository.save(updateOrderModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatusUserAndShipper(Map<String,Object> requestData, String authorizationHeader){
        Integer STATUS = (Integer) requestData.get("status");
        Integer idAccount = this.tokenService.validateTokenAndGetId(authorizationHeader);
        if (STATUS.equals(PRODUCT_IS_AWAITING_ACCEPT)) {
            List<Integer> listIdOrder = (List<Integer>) requestData.get("idOrder");
            return this.updateStatusListOrderWhenBuy_User(listIdOrder, PRODUCT_IS_AWAITING_ACCEPT);

        } else if (STATUS.equals(PRODUCT_IS_SHIPPING) || STATUS.equals(PRODUCT_IS_DELIVERED) || STATUS.equals(PRODUCT_CANCELED)) {
            Integer idOrder = (Integer) requestData.get("idOrder");
            return this.updateStatusOrder_Shipper(idOrder, STATUS, idAccount);
        }
        return false;
    }

}
