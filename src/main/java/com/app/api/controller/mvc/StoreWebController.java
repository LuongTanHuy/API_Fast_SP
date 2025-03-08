package com.app.api.controller.mvc;

import com.app.api.model.Store;
import com.app.api.service.implement.FileStorageServiceImpl;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import com.app.api.service.interfaces.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("webServerStoreController")
@RequestMapping("/FastFood/")
public class StoreWebController {
    @Autowired
    private IAccountService accountInterface;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    IStoreService storeInterface;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @GetMapping("store")
    public String PageStore(Model model,
                            @CookieValue("FastFood") String token,
                            @RequestParam(defaultValue = "0") int page){

        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        int size = 3;
        int totalPages = this.storeInterface.totalStore()/size;
        Pageable pageable = PageRequest.of(page, size);

        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Cửa Hàng");
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("listStore", this.storeInterface.listStore(pageable));
        model.addAttribute("listRequestOpenStore", this.accountInterface.getAllRequestOpenStore());
        model.addAttribute("file_html", "/components/storeBody");
        model.addAttribute("component", "storeBody");
        return "pages/index";
    }

    @GetMapping("searchStore")
    public String searchStore(Model model, @CookieValue("FastFood") String token, @RequestParam("search") String search){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Cửa Hàng");
        model.addAttribute("listStore", this.storeInterface.search(search));
        model.addAttribute("file_html", "/components/storeBody");
        model.addAttribute("component", "storeBody");
        return "pages/index";
    }

    @PostMapping("updateStore")
    public ResponseEntity<?> updateStore(@RequestParam("file")MultipartFile file,
                                         @RequestParam("name") String name,
                                         @RequestParam("address") String address,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("id") int id){
        Store storeModel = new Store();
        storeModel.setId(id);
        storeModel.setName(name);
        storeModel.setAddress(address);
        storeModel.setEmail(email);
        storeModel.setPhone(phone);
        if(!file.isEmpty()){
            String fileName = this.fileStorageService.storeFile(file);
            if (fileName != null) {
                storeModel.setImage(fileName);
            }
        }

        if(this.storeInterface.updateInfo(storeModel)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/product");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }


    @GetMapping("agreeOpenStore/{idAccount}")
    public ResponseEntity<?> agreeOpenStore(@PathVariable("idAccount") int idAccount){
        try{
            if(this.accountInterface.agreeOpenStore(idAccount)){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/FastFood/store");
                return ResponseEntity.status(302).headers(headers).build();
            }
        }catch(NullPointerException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/store");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/store");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @GetMapping("NotAgreeOpenStore/{idAccount}")
    public ResponseEntity<?> NotAgreeOpenStore(@PathVariable("idAccount") int idAccount){
        try{
            if(this.accountInterface.NotAgreeOpenStore(idAccount)){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/FastFood/store");
                return ResponseEntity.status(302).headers(headers).build();
            }
        }catch(NullPointerException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/store");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/store");
        return ResponseEntity.status(302).headers(headers).build();

    }
}
