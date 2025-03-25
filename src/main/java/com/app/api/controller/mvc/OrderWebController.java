package com.app.api.controller.mvc;

import com.app.api.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/web/")
public class OrderWebController {

    @Autowired
    private IOrderService orderInterface;

    @PostMapping("order/acceptOrder")
    public ResponseEntity<?> changeStatusOnOrderItem(@RequestParam("status") Integer status,
                                                     @RequestParam("idOrder") Integer idOrder) {

        boolean result = this.orderInterface.updateStatus(idOrder, status);
        return result == true ? ResponseEntity.ok("ok") : ResponseEntity.status(500).build();
    }

}
