package com.app.api.controller.mvc;

import com.app.api.model.Banner;
import com.app.api.service.implement.FileStorageServiceImpl;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import com.app.api.service.interfaces.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/FastFood/")
public class BannerWebController {
    @Autowired
    private IBannerService bannerInterface;
    @Autowired
    private IAccountService accountInterface;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;


    @GetMapping("banner")
    public String PageBanner(Model model, @CookieValue("FastFood") String token){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Banner");
        model.addAttribute("listBanner", this.bannerInterface.getAll());
        model.addAttribute("file_html", "/components/bannerBody");
        model.addAttribute("component", "bannerBody");
        return "pages/index";
    }

    @GetMapping("searchBanner")
    public String searchBanner(Model model, @CookieValue("FastFood") String token, @RequestParam("search") String search){
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title","Banner");
        model.addAttribute("listBanner", this.bannerInterface.search(search));
        model.addAttribute("file_html", "/components/bannerBody");
        model.addAttribute("component", "bannerBody");
        return "pages/index";
    }

    @GetMapping("deleteBanner/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable("id") int id){
        this.bannerInterface.deleteBanner(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("addBanner")
    public ResponseEntity<?> addBanner(@RequestParam("file") MultipartFile file,@RequestParam("text") String text){
        String fileName = this.fileStorageService.storeFile(file);
        Banner bannermodel = new Banner();
        bannermodel.setText(text);
        bannermodel.setImage(fileName);
        this.bannerInterface.addBanner(bannermodel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("updateBanner")
    public ResponseEntity<?> updateBanner(@RequestParam("file") MultipartFile file,@RequestParam("text") String text,@RequestParam("id") int id){
        Banner bannermodel = new Banner();
        bannermodel.setId(id);
        bannermodel.setText(text);
        if(!file.isEmpty()){
            String fileName = this.fileStorageService.storeFile(file);
            bannermodel.setImage(fileName);
        }
        if(this.bannerInterface.updateBanner(bannermodel)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/FastFood/banner");
            return ResponseEntity.status(302).headers(headers).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/banner");
        return ResponseEntity.status(302).headers(headers).build();
    }
}
