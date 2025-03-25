package com.app.api.controller.mvc;


import com.app.api.dto.AccountDTO;
import com.app.api.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/web/")
public class AccountWebController {

    @Autowired
    private IAccountService accountInterface;

    @GetMapping("list-account")
    public ResponseEntity<List<AccountDTO>> listAccount(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size)
    {
        return ResponseEntity.status(200).body(this.accountInterface.listAccount(page, size));
    }

    @GetMapping("account/search")
    public ResponseEntity<List<AccountDTO>> search(@RequestParam("search") String search)
    {
        return ResponseEntity.status(200).body(this.accountInterface.search(search));
    }
}
