package com.app.api.controller.mvc;

import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import com.app.api.service.interfaces.IOrderItemService;
import com.app.api.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("webServerOrderItemController")
@RequestMapping("/FastFood/")
public class OrderItemWebController {
    @Autowired
    private IAccountService accountInterface;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private IOrderItemService orderItemInterface;
    @Autowired
    private IOrderService orderInterface;

    @GetMapping("orderItem")
    public String PageOderItemForStore(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Đơn Mới");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),1));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "pages/index";
    }

    @GetMapping("shippingOrderItem")
    public String ShippingOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", " Đang Giao");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),2));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "pages/index";
    }

    @GetMapping("finishOrderItem")
    public String finishOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Đã Giao");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),3));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "pages/index";
    }

    @GetMapping("cancelOrderItem")
    public String cancelOrderItem(Model model, @CookieValue("FastFood") String token){
        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Hủy Đơn");
        model.addAttribute("listOderItem", this.orderItemInterface.listOderItemOfStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(),4));
        model.addAttribute("file_html", "/components/orderItemBody");
        model.addAttribute("component", "orderItemBody");
        return "pages/index";
    }

    @PostMapping("changeStatus")
    public ResponseEntity<?> changeStatusOnOrderItem(@RequestParam("status") int status,@RequestParam("id_order") int id_order){
        this.orderInterface.updateStatus(id_order,status);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/orderItem");
        return ResponseEntity.status(302).headers(headers).build();
    }

}
