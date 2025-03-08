package com.app.api.controller.api;

import com.app.api.model.Banner;
import com.app.api.service.interfaces.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiBannerController")
@RequestMapping("/api/v1/")
public class BannerController {
    @Autowired
    private IBannerService bannerInterface;

    @GetMapping("banner")
    public List<Banner> listBanner(){
        return this.bannerInterface.getAll();
    }
}
