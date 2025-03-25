package com.app.api.controller.mvc;

import com.app.api.dto.OrderItemDTO;
import com.app.api.service.interfaces.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/web/")
public class OrderItemWebController {

    @Autowired
    private IOrderItemService orderItemInterface;

    @GetMapping("orderItem")
    public ResponseEntity<List<OrderItemDTO>> OderItemStore(@RequestHeader("Authorization") String token,
                                                            @RequestParam("statusOrder") Integer status){
     return ResponseEntity.ok(this.orderItemInterface.listOderItemOfStore(token,status));
    }

}
