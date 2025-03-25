package com.app.api.controller.api;

import com.app.api.dto.AccountDTO;
import com.app.api.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController("apiAccountController")
@RequestMapping("api/v2/app/")
public class AccountController {
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

    @PostMapping("account/update")
    public ResponseEntity<?> updateProfile(
                                         @RequestHeader("Authorization") String authorizationHeader,
                                         @RequestParam("userName") String userName,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("address") String address,
                                         @RequestParam("file") MultipartFile file) {

        return this.accountInterface.update(new AccountDTO(userName,email,phone,address),authorizationHeader,file) ? ResponseEntity.status(200).body("Success") : ResponseEntity.status(500).body("Error");
    }

    @PutMapping("account/accessPermission/{idAccount}")
    public ResponseEntity<?> lockAccount(@PathVariable("idAccount") int idAccount){
        return  this.accountInterface.accessPermission(idAccount) ? ResponseEntity.status(200).body("Success") : ResponseEntity.status(404).body("Not found account");
    }

}
