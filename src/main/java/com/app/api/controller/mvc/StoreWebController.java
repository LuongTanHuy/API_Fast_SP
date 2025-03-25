package com.app.api.controller.mvc;

import com.app.api.dto.ListStoreDTO;
import com.app.api.dto.StoreDTO;
import com.app.api.service.interfaces.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v2/web/")
public class StoreWebController {
    @Autowired
    IStoreService storeInterface;

    @GetMapping("store/list")
    public ResponseEntity<List<ListStoreDTO>> PageStore(@RequestHeader("Authorization") String token,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "20") Integer size){

         return ResponseEntity.ok(this.storeInterface.listStore(token,page,size));
    }

    @GetMapping("store/search")
    public ResponseEntity<List<ListStoreDTO>>  search(@RequestHeader("Authorization") String token,
                                                     @RequestParam("search") String search){
        return ResponseEntity.ok(this.storeInterface.search(token,search));
    }

    @PostMapping("updateStore")
    public ResponseEntity<StoreDTO> updateStore(@RequestParam("file") MultipartFile file,
                                                @RequestParam("name") String name,
                                                @RequestParam("address") String address,
                                                @RequestParam("email") String email,
                                                @RequestParam("phone") String phone,
                                                @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(this.storeInterface.updateInfo(token, file, name, address, email, phone));

    }
}
