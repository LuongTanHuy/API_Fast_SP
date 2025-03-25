package com.app.api.controller.mvc;


import com.app.api.dto.AccountDTO;
import com.app.api.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/web/")
public class AccountWebController {

    @Autowired
    private IAccountService accountInterface;

    @GetMapping("account/information")
    public ResponseEntity<AccountDTO> accountDetail(@RequestHeader("Authorization") String authorizationHeader){
        try {
            AccountDTO accountDTO = this.accountInterface.accountDetail(authorizationHeader);
            return accountDTO != null
                    ? ResponseEntity.status(200).body(accountDTO)
                    : ResponseEntity.status(404).body(null);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

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
