package com.app.api.controller.api;

import com.app.api.dto.OrderItemDTO;
import com.app.api.model.OrderItem;
import com.app.api.service.interfaces.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/app/")
public class OrderItemController {

    @Autowired
    private IOrderItemService orderItemInterface;

    @PostMapping("orderItem/add")
    public ResponseEntity<?> add(@RequestBody Map<String, Object> requestData) {
        try {
            Integer idOrder   = Integer.parseInt(requestData.get("idOrder").toString());
            Integer idProduct = Integer.parseInt(requestData.get("idProduct").toString());
            Integer quantity  = Integer.parseInt(requestData.get("quantity").toString());

            return this.orderItemInterface.add(idOrder, idProduct, quantity) > 0 ?
                    ResponseEntity.status(200).body("ok") :
                    ResponseEntity.status(500).body("Error");

        } catch (NumberFormatException e) {
            System.out.println("add-order-item: "+e.getMessage());
            return ResponseEntity.status(400).body("Invalid data format");
        }
    }


    @GetMapping("orderItem/list")
    public ResponseEntity<List<OrderItemDTO>> listOrderItem(@RequestHeader("Authorization") String authorizationHeader,
                                                            @RequestParam("statusOrderItem") Integer statusOrderItem) {
            return ResponseEntity.status(200).body(this.orderItemInterface.listOrderItem(authorizationHeader, statusOrderItem));
    }

    @GetMapping("orderItem/list-shipperChose")
    public ResponseEntity<List<OrderItemDTO>> listOderToShipperChose(@RequestParam("statusOrderItem") Integer statusOrderItem) {
        return ResponseEntity.status(200).body(this.orderItemInterface.listOderToShipperChose(statusOrderItem));
    }

    @GetMapping("orderItem/list-shipper")
    public ResponseEntity<List<OrderItemDTO>> listOrderOfShipper(@RequestHeader("Authorization") String authorizationHeader,
                                              @RequestParam("statusOrderItem") Integer statusOrderItem) {
        return ResponseEntity.status(200).body(this.orderItemInterface.listOrderOfShipper(statusOrderItem,authorizationHeader));
    }

    @PutMapping("orderItem/update/quantity")
    public ResponseEntity<?> updateQuantityForOrderItem(@RequestBody OrderItem orderItemModel){
        return  this.orderItemInterface.updateQuantity(orderItemModel)?
                ResponseEntity.status(200).body("ok"):
                ResponseEntity.status(500).body("Error");
    }

    @PutMapping("orderItem/update/price")
    public ResponseEntity<?> updatePriceForOrderItem(@RequestBody Map<String, Object> requestData) {
        List<Integer> idOrder = (List<Integer>)requestData.get("idOrder");
        return  this.orderItemInterface.updatePrice(idOrder)?
                ResponseEntity.status(200).body("Ok"):
                ResponseEntity.status(500).body("Error");
    }
}
