package com.app.api.controller.api;

import com.app.api.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/app/")
public class OrderController {

    @Autowired
    private IOrderService orderInterface;

    @PostMapping("order/add")
    public ResponseEntity<?> add(@RequestBody Map<String, Integer> requestData,
                                 @RequestHeader("Authorization") String authorizationHeader) {

        Integer idStore = requestData.get("idStore");
        return ResponseEntity.status(200).body("idOrder:"+this.orderInterface.add(authorizationHeader, idStore));
    }

    @PutMapping("order/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String,Object> requestData,
                                          @RequestHeader("Authorization") String authorizationHeader) {

        boolean result = this.orderInterface.updateStatusUserAndShipper(requestData,authorizationHeader);
        return result == true ? ResponseEntity.ok("Finish"): ResponseEntity.status(500).body("Error");
    }

}
