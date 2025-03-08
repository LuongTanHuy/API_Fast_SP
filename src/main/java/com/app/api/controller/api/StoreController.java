package com.app.api.controller.api;

import com.app.api.model.Store;
import com.app.api.service.implement.FileStorageServiceImpl;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import com.app.api.service.interfaces.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/")
public class StoreController {

    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private IStoreService storeInterface;
    @Autowired
    private IAccountService accountInterface;


    @PostMapping("add-store")
    public ResponseEntity<?> addStore(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestParam("name") String name,
                                        @RequestParam("address") String address,
                                        @RequestParam("email") String email,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("file") MultipartFile file) {
        String token = authorizationHeader.replace("Bearer ", "");
       int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        Store newStoreModel = new Store();
        newStoreModel.setName(name);
        newStoreModel.setAddress(address);
        newStoreModel.setEmail(email);
        newStoreModel.setPhone(phone);
        newStoreModel.setImage(this.fileStorageService.storeFile(file));
        newStoreModel.setStatus(0);

        int idStore = this.storeInterface.addStore(newStoreModel);

        return this.accountInterface.updateIdStore(idStore,idAccount) ?
                ResponseEntity.status(200).body("ok") :
                ResponseEntity.status(500).body("Error");
    }

}
