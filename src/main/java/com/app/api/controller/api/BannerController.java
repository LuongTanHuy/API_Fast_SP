package com.app.api.controller.api;

import com.app.api.model.Banner;
import com.app.api.service.interfaces.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/app/")
public class BannerController {
    @Autowired
    private IBannerService bannerInterface;

    @GetMapping("banner")
    public ResponseEntity<List<Banner>> listBanner(){
        return ResponseEntity.status(200).body(this.bannerInterface.listBanner());
    }
}
