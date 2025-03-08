package com.app.api.service.implement;

import com.app.api.model.Account;
import com.app.api.model.Order;
import com.app.api.model.Store;
import com.app.api.repository.IOrderRepository;
import com.app.api.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private EmailServiceImpl emailService;

    @Autowired
    public OrderServiceImpl(IOrderRepository orderRepository, EmailServiceImpl emailService) {
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }

    @Override
    public int addOrder(int idAccount, int idStore) {
            Order orderModel = new Order();

            Account accountModel = new Account();
            accountModel.setId(idAccount);

            Store storeModel = new Store();
            storeModel.setId(idStore);

            orderModel.setAccountModel(accountModel);
            orderModel.setStoreModel(storeModel);
            return this.orderRepository.save(orderModel).getId();
    }

    @Override
    public boolean updateStatusListOrderWhenBuy(List<Integer> id_order, int status) {
        for(int i= 0 ;i < id_order.size();i++){
            Optional<Order> orderModel = this.orderRepository.findById(id_order.get(i));
            if (orderModel.isPresent()) {
                Order updateOrderModel = orderModel.get();
                updateOrderModel.setStatus(status);
                this.orderRepository.save(updateOrderModel);
            }
        }
        return true;
    }

    @Override
    public boolean updateStatus(int id_order, int status) {
        Optional<Order> orderModel = this.orderRepository.findById(id_order);
        if (orderModel.isPresent()) {
            Order updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            this.orderRepository.save(updateOrderModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatusOrderWhenDeliveryComplete(int id_order, int status, int idShipper) {
        Optional<Order> orderModel = this.orderRepository.findById(id_order);
        if (orderModel.isPresent()) {

            Order updateOrderModel = orderModel.get();
            updateOrderModel.setStatus(status);
            updateOrderModel.setId_Shipper(idShipper);
            this.orderRepository.save(updateOrderModel);

            if(status == 3){
                emailService.sendNotificationWhenPayment(updateOrderModel);
            }

            return true;
        }
        return false;
    }

}
