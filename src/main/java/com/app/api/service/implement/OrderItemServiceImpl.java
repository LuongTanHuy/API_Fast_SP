package com.app.api.service.implement;

import com.app.api.dto.OrderItemDTO;
import com.app.api.model.Order;
import com.app.api.model.OrderItem;
import com.app.api.model.Product;
import com.app.api.repository.IOrderItemRepository;
import com.app.api.repository.IOrderRepository;
import com.app.api.repository.IProductRepository;
import com.app.api.service.interfaces.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {

    private final IOrderItemRepository orderItemRepository;
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final TokenServiceImpl tokenService;

    @Override
    public Integer add(Integer idOrder, Integer idProduct,Integer quantity) {
        Optional<Order> optionalOrderModel = this.orderRepository.findById(idOrder);
        Optional<Product> optionalProductModel = this.productRepository.findById(idProduct);

        if (!optionalOrderModel.isPresent()) {
            throw new IllegalArgumentException("Order with id " + idOrder + " does not exist.");
        }
        if (!optionalProductModel.isPresent()) {
            throw new IllegalArgumentException("Product with id " + idProduct + " does not exist.");
        }

        Order orderModel = optionalOrderModel.get();
        Product productModel = optionalProductModel.get();

        OrderItem orderItemModel = new OrderItem();
        orderItemModel.setOrderModel(orderModel);
        orderItemModel.setProductModel(productModel);
        orderItemModel.setQuantity(quantity);


        return this.orderItemRepository.save(orderItemModel).getId();
    }

    @Override
    public List<OrderItemDTO> listOrderItem(String token, Integer status){
        Integer idAccount = this.tokenService.validateTokenAndGetId(token);
        List<OrderItem> listItem = this.orderItemRepository.listOrderItem(idAccount,status);
        return this.convertToDTO(listItem);
    }

    @Override
    public List<OrderItemDTO> listOderToShipperChose(Integer statusOrderItem) {
        List<OrderItem> listItem = this.orderItemRepository.listOrderForShipper(statusOrderItem);
        return this.convertToDTO(listItem);
    }

    @Override
    public List<OrderItemDTO> listOderItemOfStore(String idStore, Integer status) {
        return this.convertToDTO(this.orderItemRepository.listOderItemOfStore(this.tokenService.validateTokenAndGetId(idStore),status));
    }

    @Override
    public List<OrderItemDTO> listOrderOfShipper(Integer status, String token) {
        Integer idShipper = this.tokenService.validateTokenAndGetId(token);
        List<OrderItem> listItem = this.orderItemRepository.listOrderOfShipper(status,idShipper);
        return this.convertToDTO(listItem);
    }
    @Override
    public boolean updatePrice(List<Integer> id_order) {
        for(int i= 0 ;i < id_order.size();i++){
            Optional<OrderItem> orderItemModel = this.orderItemRepository.findByIdOrder(id_order.get(i));
            if (orderItemModel.isPresent()) {
                OrderItem updateOrderIemModel = orderItemModel.get();
                double price = updateOrderIemModel.getProductModel().getPrice() - (updateOrderIemModel.getProductModel().getPrice()*updateOrderIemModel.getProductModel().getCategoryModel().getSale())/100;
                updateOrderIemModel.setPrice(price);
                this.orderItemRepository.save(updateOrderIemModel);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateQuantity(OrderItem orderItemModel) {
        Optional<OrderItem> itemOrder = this.orderItemRepository.findById(orderItemModel.getId());
        if (itemOrder.isPresent()) {
            OrderItem updateItem = itemOrder.get();
            if (orderItemModel.getQuantity() == 0) {
                this.orderItemRepository.deleteById(updateItem.getId());
            } else {
                updateItem.setQuantity(orderItemModel.getQuantity());
                this.orderItemRepository.save(updateItem);
            }
            return true;
        }
        return false;
    }

    private List<OrderItemDTO> convertToDTO(List<OrderItem> listItem){
      return listItem.stream().map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toList());
    }

    public double price( List<OrderItem> orderItemModel){
        double value = 0;
        if(!orderItemModel.isEmpty()){
            for(int i= 0;i < orderItemModel.size();i++){
                value += orderItemModel.get(i).getPrice();
            }
        }
        return value;
    }

    @Override
    public int[] getQuantityOrder(int idStore) {

        int[] quantityOrder = new int[4];
        quantityOrder[0] = this.orderItemRepository.listOderItemOfStore(idStore,1).size();
        quantityOrder[1] = this.orderItemRepository.listOderItemOfStore(idStore,2).size();
        quantityOrder[2] = this.orderItemRepository.listOderItemOfStore(idStore,3).size();
        quantityOrder[3] = this.orderItemRepository.listOderItemOfStore(idStore,4).size();
        return quantityOrder;

    }

    @Override
    public double[] getPriceOrder(int idStore, int year) {
        List<OrderItem> month1 = this.orderItemRepository.findAllByYear(idStore,3,1,year);
        List<OrderItem> month2 = this.orderItemRepository.findAllByYear(idStore,3,2,year);
        List<OrderItem> month3 = this.orderItemRepository.findAllByYear(idStore,3,3,year);
        List<OrderItem> month4 = this.orderItemRepository.findAllByYear(idStore,3,4,year);
        List<OrderItem> month5 = this.orderItemRepository.findAllByYear(idStore,3,5,year);
        List<OrderItem> month6 = this.orderItemRepository.findAllByYear(idStore,3,6,year);
        List<OrderItem> month7 = this.orderItemRepository.findAllByYear(idStore,3,7,year);
        List<OrderItem> month8 = this.orderItemRepository.findAllByYear(idStore,3,8,year);
        List<OrderItem> month9 = this.orderItemRepository.findAllByYear(idStore,3,9,year);
        List<OrderItem> month10 = this.orderItemRepository.findAllByYear(idStore,3,10,year);
        List<OrderItem> month11 = this.orderItemRepository.findAllByYear(idStore,3,11,year);
        List<OrderItem> month12 = this.orderItemRepository.findAllByYear(idStore,3,12,year);

        double[] priceOfYear = new double[12];
        priceOfYear[0] = this.price(month1);
        priceOfYear[1] = this.price(month2);
        priceOfYear[2] = this.price(month3);
        priceOfYear[3] = this.price(month4);
        priceOfYear[4] = this.price(month5);
        priceOfYear[5] = this.price(month6);
        priceOfYear[6] = this.price(month7);
        priceOfYear[7] = this.price(month8);
        priceOfYear[8] = this.price(month9);
        priceOfYear[9] = this.price(month10);
        priceOfYear[10] = this.price(month11);
        priceOfYear[11] = this.price(month12);


        return priceOfYear;
    }



}
